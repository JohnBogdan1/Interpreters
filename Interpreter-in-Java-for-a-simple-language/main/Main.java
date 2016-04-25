package main;

import java.util.ArrayList;

import analiza_semantica.AnalizaSemantica;
import arbore_de_parsare.AfisareArbore;
import arbore_de_parsare.ArboreDeParsare;
import evaluare_expresie.EvaluareExpresie;
import postfix.InfixToPostfix;
import read_write.ReadInput;
import read_write.WriteOutput;

/**
 * Clasa principala care executa programul.
 * 
 * @author Johnny
 *
 */
public class Main {

	public static void main(String[] args) {

		/*
		 * args[0] este numele fisierului de input dat ca argument in linia de
		 * comanda.
		 */

		/*
		 * Clasa ReadInput prea expresiile din fisierul de intrare si le pune
		 * intr-un ArrayList<String>.
		 */
		ReadInput read = new ReadInput(args[0]);

		/*
		 * Clasa WriteOutput va scrie output-ul pentru arborele de parsare in
		 * fisierul de iesire corespunzator.
		 */
		WriteOutput outputArboreDeParsare = null;
		if (args[0].contains(".")) {
			String nume = args[0].substring(0, args[0].indexOf("."));
			String extensie = args[0].substring(args[0].indexOf("."), args[0].length());
			outputArboreDeParsare = new WriteOutput(nume + "_pt" + extensie);
		} else {
			outputArboreDeParsare = new WriteOutput(args[0] + "_pt");
		}

		// Pentru fiecare linie(expresie) din fisierul de input.
		for (String line : read.getLines()) {
			/*
			 * Convertesc expresia la forma postfixata pentru a o putea adauga
			 * in arbore.
			 */
			ArrayList<String> postfix = InfixToPostfix.ToPostfix(line);

			// Creez un arbore pentru expresia postfixata.
			ArboreDeParsare et = new ArboreDeParsare();
			et.creeazaArbore(postfix);

			/*
			 * Scriu toate nivelurile arborelui in fisier in forma
			 * corespunzatoare.
			 */
			AfisareArbore.printLevelOrder(et.getRoot(), outputArboreDeParsare);

			/*
			 * Pun o linie goala in fisier dupa fiecare output al arborelui de
			 * parsare corespunzator unei expresii.
			 */
			outputArboreDeParsare.write("\n");
		}

		// Inchid fisierul de output pentru arborele de parsare.
		outputArboreDeParsare.close();

		/*
		 * Clasa WriteOutput va scrie output-ul pentru analiza semantica in
		 * fisierul de iesire corespunzator.
		 */
		WriteOutput outputAnalizaSemantica = null;
		if (args[0].contains(".")) {
			String nume = args[0].substring(0, args[0].indexOf("."));
			String extensie = args[0].substring(args[0].indexOf("."), args[0].length());
			outputAnalizaSemantica = new WriteOutput(nume + "_sa" + extensie);
		} else {
			outputAnalizaSemantica = new WriteOutput(args[0] + "_sa");
		}

		// Clasa va analiza fiecare expresie din fisier.
		AnalizaSemantica analizaSemantica = new AnalizaSemantica();
		analizaSemantica.Analizeaza(read.getLines());

		// Scriu in fisier output-urile corespunzatoare expresiilor analizate.
		outputAnalizaSemantica.writeLines(analizaSemantica.getOutput());

		// Inchid fisierul de output pentru analiza semantica.
		outputAnalizaSemantica.close();

		/*
		 * Clasa WriteOutput va scrie output-ul pentru evaluarea expresiilor in
		 * fisierul de iesire corespunzator.
		 */
		WriteOutput outputEvaluareExpresie = null;
		if (args[0].contains(".")) {
			String nume = args[0].substring(0, args[0].indexOf("."));
			String extensie = args[0].substring(args[0].indexOf("."), args[0].length());
			outputEvaluareExpresie = new WriteOutput(nume + "_ee" + extensie);
		} else {
			outputEvaluareExpresie = new WriteOutput(args[0] + "_ee");
		}

		// Clasa va evalua fiecare expresie din fisier.
		EvaluareExpresie evaluareExpresie = new EvaluareExpresie();
		evaluareExpresie.Evalueaza(read.getLines());

		// Scriu in fisier output-urile corespunzatoare expresiilor evaluate.
		outputEvaluareExpresie.writeLines(evaluareExpresie.getOutput());

		// Inchid fisierul de output pentru evaluarea expresiei.
		outputEvaluareExpresie.close();
	}
}