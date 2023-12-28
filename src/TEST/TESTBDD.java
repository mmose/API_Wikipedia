package TEST;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import Source.Dictionnaire_From_Wikipedia;
import Source.Dictionnaire_In_Wikipedia;
import Source.MediaWikiAPI;
import Source.Repartisseur;
import Source.TitleP279;
import Source.TitleP31;

public class TESTBDD {

	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException, URISyntaxException {
		
		//Dictionnaire_In_Wikipedia bdd = new Dictionnaire_In_Wikipedia();
		//bdd.Run();

		//Dictionnaire_From_Wikipedia b1 = new Dictionnaire_From_Wikipedia();
		//b1.Run();
		
		/*
		String nomfichier = "Dico_In_Wiki_4";
		System.out.println("--------------------------------------------------------------Début lecture fichier "+nomfichier);
		MediaWikiAPI d = new MediaWikiAPI(nomfichier);
		d.Start();
		System.out.println("--------------------------------------------------------------Fin lecture fichier "+nomfichier);*/
		 
		
		//Repartisseur p1 = new Repartisseur();
		//p1.StartDictionnaire();
	
		
		//TitleP31 p = new TitleP31("Dictionnaire_P31_Direct");
		//p.start();
	
		
		TitleP279 p = new TitleP279("Refactoring/class/p31.txt","Refactoring/class/p31.csv");
		p.start();
		
	}

}
