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

public class Dictionnaire_From_Wikipedia {
	
	Connection connection;
	int compteur = 0;
	
	public Dictionnaire_From_Wikipedia() throws FileNotFoundException, IOException
	{
		
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BONCOEUR", "postgres", "postgres");
		} catch (SQLException e1) {
			System.out.println("Connection failure.");
			e1.printStackTrace();
		}
		
	}
	
	
	public void Run() throws FileNotFoundException, IOException, SQLException
	{
		lecturefichierUTF8();

	}
		       
	 
	 public void lecturefichierUTF8() throws SQLException 
	 {
			String filePath = "Dictionnaire/Dictionnaire_Mots_In_Wikipedia.txt";

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
		 
		 //String saisie_MAJ = saisie.substring(0, 1).toUpperCase() + saisie.substring(1);	 

         PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,title,P31[1] FROM public.dictionnaire WHERE title = ?");
         preparedStatement.setString(1, saisie);
         ResultSet resultSet = preparedStatement.executeQuery();
         
         
         if (!resultSet.isBeforeFirst()) {    
        	 String export_message = "No title;"+saisie;
        	 export(export_message);
        	 System.out.println("Erreur "+saisie+" REDIRECT_TITLE");
        	} 
         else if (resultSet.next()){
             
             int id = resultSet.getInt(1);
             String title = resultSet.getString(2);
             String p31 = resultSet.getString(3);

             
             String export_message = String.valueOf(id)+";"+title+";"+p31;
             export(export_message);
        	 System.out.println("Correct "+export_message);
         }
         
         
	 }
	 
	 
		public void export(String export) throws IOException
		{

			try (
					FileWriter f = new FileWriter("Final/Dictionnaire/dictionnaire_fr.csv", true);
	                BufferedWriter b = new BufferedWriter(f);
	                PrintWriter p = new PrintWriter(b);
				) 
		 	{
	            	p.println(export);
	            	compteur++;
	            	System.out.println("Fichier "+compteur+" bien crée !");
	        } catch (IOException i) {
	        	System.out.println("Erreur lors de la création du fichier");
	            i.printStackTrace();
	        }
			
		}
	 
}
