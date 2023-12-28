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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;

import Metier.PageWikidata;
import Metier.PageWikipedia;

public class Dictionnaire_In_Wikipedia {
	
	Connection connection;
	int compteur = 0;
	
	public Dictionnaire_In_Wikipedia() throws FileNotFoundException, IOException
	{
		
		try {
			//connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BONCOEURTEST", "postgres", "postgres");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BONCOEUR", "postgres", "postgres");
		} catch (SQLException e1) {
			System.out.println("Connection failure.");
			e1.printStackTrace();
		}
		
	}
	
	
	public void Run() throws FileNotFoundException, IOException, SQLException
	{
		//lecturefichier();
		lecturefichierUTF8();

	}
		       
	 
	 public void lecturefichier() throws FileNotFoundException, IOException, SQLException
	 {
			try(BufferedReader br = new BufferedReader(new FileReader("Dictionnaire/MOTS - data-1637590729013.csv"))) {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			        
			        try {
						requete(line);
					} catch (JSONException e) {
						e.printStackTrace();
					}
			    }
			    System.out.println("Fin de la lecture du fichier");
			}
	 }
	 
	 public void lecturefichierUTF8() throws SQLException 
	 {
			String filePath = "Dictionnaire/MOTS - data-1637590729013.csv";

			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"))) {

			 String cLine;
			 while ((cLine = br.readLine()) != null) {
			  sb.append(cLine).append("\n");
			  
			  
			  try {
				requete(cLine);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			  
			 }
			} catch (IOException e) {
			 e.printStackTrace();
			}
	 }
	 
	 
	 public void requete(String saisie) throws SQLException, IOException
	 {
		 
		 String saisie_MAJ = saisie.substring(0, 1).toUpperCase() + saisie.substring(1);	 
         Statement statement = connection.createStatement();
     
         
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT page_title FROM public.wikipedia WHERE page_title = ?");
         preparedStatement.setString(1, saisie_MAJ);
         ResultSet resultSet = preparedStatement.executeQuery();
         
         
         if (!resultSet.isBeforeFirst()) {    
        	 String message = "pas de resultat pour "+saisie_MAJ;
        	 export(message);
        	 System.out.println("Erreur "+saisie_MAJ+" N'EST PAS DANS WIKIPEDIA");
        	} 
         else if (resultSet.next()){
             export(resultSet.getString(1));
        	 System.out.println("Correct "+resultSet.getString(1)+" EST BIEN DANS WIKIPEDIA");
         }
         
         
	 }
	 
	 
		public void export(String saisie) throws IOException
		{

			try (
					FileWriter f = new FileWriter("Dictionnaire_In_Wikipedia/liste_MOTS_V2.csv", true);
	                BufferedWriter b = new BufferedWriter(f);
	                PrintWriter p = new PrintWriter(b);
				) 
		 	{
	            	p.println(saisie);
	            	compteur++;
	            	System.out.println("Fichier "+compteur+" bien crée !");
	        } catch (IOException i) {
	        	System.out.println("Erreur lors de la création du fichier");
	            i.printStackTrace();
	        }
			
		}
	 
}
