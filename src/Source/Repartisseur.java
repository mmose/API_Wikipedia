package Source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repartisseur {
	
	int nombre = 0;
	
	public Repartisseur ()
	{
		
	}
	
	public void StartWikipedia() throws FileNotFoundException, IOException
	{
		try {
	        FileReader reader = new FileReader("");
	        BufferedReader bufferedReader = new BufferedReader(reader);

	        String line;
	        long currentLineNr = 0;
	        while ((line = bufferedReader.readLine()) != null) {

	            
	            currentLineNr++;
	            PackWikipedia(currentLineNr,nombre,line);
	            //System.out.println(line);
	            //System.out.println(currentLineNr);
	            
	            if (currentLineNr == 3386)
	            {
	            	currentLineNr = 0;
	            	nombre++;
	            }

	        }
	        reader.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	  }

	}
	
	public void StartDictionnaire() throws FileNotFoundException, IOException
	{
		try {
	        //FileReader reader = new FileReader("./Final/Dictionnaire/Dictionnaire_Mots_In_Wikipedia.csv");
			String filePath = "./Final/Dictionnaire/Dictionnaire_Mots_In_Wikipedia.csv";
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
			/**
			 * new BufferedReader(reader);
			 * VERSION UTF8 try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"))) 
			 */

	        String line;
	        long currentLineNr = 0;
	        while ((line = bufferedReader.readLine()) != null) {

	            
	            currentLineNr++;
	            PackDictionnaire(currentLineNr,nombre,line);
	            //System.out.println(line);
	            //System.out.println(currentLineNr);
	            
	            if (currentLineNr == 5689)
	            {
	            	currentLineNr = 0;
	            	nombre++;
	            }

	        }
	        bufferedReader.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	  }

	}
	
	public void PackWikipedia(long currentLineNr, int nombre,String line)
	{
		try (
				FileWriter f = new FileWriter("Repartisseur/Pack"+String.valueOf(nombre)+".csv", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);
			) 
	 	{
            	p.println(line);
            	System.out.println("Fichier "+currentLineNr+" bien crée ! "+line);
        } catch (IOException i) {
        	System.out.println("Erreur lors de la création du fichier");
            i.printStackTrace();
        }  
	}
	
	public void PackDictionnaire(long currentLineNr, int nombre,String line)
	{
		try (
				FileWriter f = new FileWriter("Final/Dictionnaire/Pack/Dico_In_Wiki_"+String.valueOf(nombre)+".csv", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);
			) 
	 	{
            	p.println(line);
            	System.out.println("Fichier "+currentLineNr+" bien crée ! "+line);
        } catch (IOException i) {
        	System.out.println("Erreur lors de la création du fichier");
            i.printStackTrace();
        }  
	}
	

}
	
	
