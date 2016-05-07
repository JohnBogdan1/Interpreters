module Interpreter
  (
    -- * Types
    Prog,

    -- * Functions
    evalRaw,
    evalAdt,
  ) where

-- import Map pentru a-l folosi in stocarea valorilor variabilelor
import qualified Data.Map as M

-- folosesc monade pentru tratarea erorilor
import Control.Monad

-------------------------------------------------------------------------------
--------------------------------- The Expr ADT  -------------------------------
-------------------------------------------------------------------------------
data Expr = Add Expr Expr
          | Sub Expr Expr
          | Mult Expr Expr
          | Equal Expr Expr
          | Smaller Expr Expr
          | Symbol String
          | Value Int deriving (Show, Read)

-- definesc un dictionar care contine perechi (String, Int)
type SymTab = M.Map String Int

-- creez o monada definita de un constructor de tip bazat pe Either a b
-- prima variabila este fixata -> String
-- de asemenea folosesc constructorul Ev pe Either
newtype Evaluator a = Ev (Either String a) deriving Show

-- declaratia monadei
-- definesc operatorul bind care primeste un pattern Ev ev si o "continuare" k
-- bind returneaza o noua monada
-- return incapsuleaza o valoare intr-o monada
-- fail incapsuleaza un mesaj(String) intr-o monada
instance Monad Evaluator where
    (Ev ev) >>= k = 
        case ev of
          Left msg -> Ev (Left msg)
          Right v -> k v
    return v = Ev (Right v)
    fail msg = Ev (Left msg)

-- programul principal
-- map-ul este initial empty
main :: Prog -> Either String Int
main program = do
   evaluare program (M.fromList [])

-- functia evaluare primeste un program si un map
-- ea intoarce rezultatul evaluarii programului, precum si dictionarul rezultat
evaluare :: Prog -> M.Map String Int -> Either String Int
evaluare program symTab = do
  let
    Ev ev = evalp program symTab
    in
      case ev of
      -- mesaj in caz de eroare
      Left msg -> Left msg
      Right (v, symTab') -> do
        let x = M.member "reachedReturn" symTab'
        -- se intoarce valoarea rezultata in urma evaluarii
        -- sau eroare daca nu exista return
        if x == True then Right v else Left "Missing return"

eval :: Expr -> SymTab -> Evaluator (Int, SymTab)

-- cazurile de baza
-- evaluarea unui simbol presupune cautarea valorii corespunzatoare in map
-- evaluarea unei valori reprezinta chiar valoarea
eval (Symbol a) d = lookupMap a d
eval (Value a) d = return (a, d)

-- operatiile aritmetice
-- folosesc operatorul bind pe care aplic evaluarea expresiei si o continuare
-- ceea ce rezulta, ca fiind continuarea, este valoarea si noul dictionar
-- la final returnez rezultatul operatiei intre cele doua valori si map-ul
-- return incapsuleaza o valoare intr-o monada
-- procedez analog pentru toate operatiile
eval (Add a b) d = do 
  (eval a d) >>= \(v1, d') ->
    (eval b d') >>= \(v2, d'') ->
      return (v1 + v2, d'')

eval (Sub a b) d = do 
  (eval a d) >>= \(v1, d') ->
    (eval b d') >>= \(v2, d'') ->
      return (v1 - v2, d'')

eval (Mult a b) d = do 
  (eval a d) >>= \(v1, d') ->
    (eval b d') >>= \(v2, d'') ->
      return (v1 * v2, d'')

-- operatii de comparare(bool) cu rezultat intreg
eval (Equal a b) d = do 
  (eval a d) >>= \(v1, d') ->
    (eval b d') >>= \(v2, d'') ->
      if (v1 == v2) then return (1, d'') else return (0, d'')

eval (Smaller a b) d = do 
  (eval a d) >>= \(v1, d') ->
    (eval b d') >>= \(v2, d'') ->
      if (v1 < v2) then return (1, d'') else return (0, d'')

-------------------------------------------------------------------------------
---------------------------------- The Prog ADT -------------------------------
-------------------------------------------------------------------------------
data Prog = Eq String Expr
          | Seq Prog Prog
          | If Expr Prog Prog
          | While Expr Prog
          | Return Expr deriving (Show, Read)

-- functia de cautare a unei variabile in dictionar
lookupMap :: String -> SymTab -> Evaluator (Int, SymTab)

-- in cazul in care se gaseste, returneaza o monada (valoare, dictionar)
-- altfel returneaza eroare
lookupMap var map =
    case M.lookup var map of
      Just value -> return (value, map)
      Nothing -> fail $ "Uninitialized variable"

-- functia de adaugare in dictionar
addSymbol :: String -> Int -> SymTab -> Evaluator ((), SymTab)

-- introduce o pereche (String, Int) in dictionar daca nu exista
-- altfel, daca exista, suprascrie valoarea asociata cheii
-- returneaza un nou dictionar
addSymbol str val symTab = 
    let symTab' = M.insert str val symTab
    in return ((), symTab')

-- functia de evaluare a programului
evalp :: Prog -> SymTab -> Evaluator (Int, SymTab)

-- evaluez expresia si intorc valoarea si un nou map
-- map-ul rezultat in urma evaluarii unei expresii va fi acelasi
-- adaug variabila si valoarea in map si intorc un nou map
-- returnez noul map rezultat, impreuna cu valoarea(optional)
-- d este dictionarul
evalp (Eq var expr) d = do 
  (eval expr d) >>= \(value, d') ->
    addSymbol var value d' >>= \(_, d'') ->
      return (value, d'')

-- evaluez primul program si intorc un nou map
-- evaluez si al doilea program folosind map-ul anterior rezultat
evalp (Seq prog1 prog2) d = do 
  evalp prog1 d >>= \(_, d') ->
    evalp prog2 d'

-- evaluez expresia si intorc valoarea + noul map
-- daca valoarea este diferita de 0 evaluez primul program, altfel celalalt
-- limbajul fiind interpretat, erorile pe celelalte ramuri vor fi ignorate
-- in cazul in care exista erori
evalp (If expr prog1 prog2) d = do 
  (eval expr d) >>= \(value, d') ->
    if value /= 0 then evalp prog1 d' else evalp prog2 d'

-- evaluez expresia si intorc valoarea + noul map
-- daca valoarea este diferita de 0 evaluez secventa formata din programul 
-- curent si evaluarea urmatoare a programului while
-- aceasta evaluare se executa pana cand valoarea rezultata din evaluarea 
-- expresiei este egala cu 0
evalp (While expr prog) d = do 
  (eval expr d) >>= \(value, d') ->
    if (value /= 0) then (evalp (Seq prog (While expr prog)) d') else return (value, d')

-- cand gasesc return, evaluez expresia si adaug in map o variabila pe care
-- o voi gasi la finalul executiei in map. Aceasta imi spune daca executia a
-- ajuns la return sau nu. Returnez valoarea data de evaluarea expresiei si
-- noul map
evalp (Return expr) d = do 
  (eval expr d) >>= \(value, d') ->
    addSymbol "reachedReturn" value d' >>= \(_, d'') ->
    return (value, d'')

-------------------------------------------------------------------------------
-------------------------------- The Interpreter ------------------------------
-------------------------------------------------------------------------------

-- functia de evaluare primeste un program si intoarce un Either rezultat
-- apelez functia main pentru evaluarea programului respectiv
evalAdt :: Prog -> Either String Int
evalAdt = \program -> main program

-- functia de parsare este neimplementata
parse :: String -> Maybe Prog
parse = undefined

evalRaw :: String -> Either String Int
evalRaw rawProg = case parse rawProg of
                    Just prog -> evalAdt prog
                    Nothing   -> Left "Syntax error"