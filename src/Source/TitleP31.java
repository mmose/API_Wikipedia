package Source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Metier.PageWikidata;
import Metier.PageWikipedia;

public class TitleP31 {
	
	String nomfichier;
	int compteur;
	
	public TitleP31 (String nomfichier)
	{
		this.nomfichier = nomfichier;
	}
	
	public void start() throws URISyntaxException
	{
		Lecture_Fichier_WikipediaFR_UTF8();
	}
	
	
	public void Lecture_Fichier_WikipediaFR_UTF8() throws URISyntaxException 
	{
		String filePath = "Final/Dictionnaire/"+nomfichier+".txt";

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"))) {

		 String cLine;
		 while ((cLine = br.readLine()) != null) {
		  sb.append(cLine).append("\n");
		  
		  try {
			  WikidataPageInfo(cLine);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 }
		} catch (IOException e) {
		 e.printStackTrace();
		}


	}
	
	public void WikidataPageInfo(String ligne) throws IOException, JSONException, URISyntaxException 
	{
		
			String url = "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="+ligne+"&languages=fr&format=json";
		     URL obj = new URL(url);
		     HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) obj.openConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		     con.setRequestMethod("GET");
		     con.setRequestProperty("User-Agent", "Mozilla/5.0");
		     con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		     BufferedReader in = new BufferedReader(
		             new InputStreamReader(con.getInputStream(), Charset.forName("utf-8")));
		     String inputLine;
		     StringBuffer response = new StringBuffer();
		     while ((inputLine = in.readLine()) != null) {
		     	response.append(inputLine);
		     }
		     in.close();

		     ExtractJSONWikidata(ligne,response.toString());	

			
	}
	
	public void ExtractJSONWikidata(String ligne, String response) throws JSONException, IOException
	{

		JSONObject myResponse = new JSONObject(response.toString());
		System.out.println("Début WikiDATA: "+ligne);
		
		String label = null;
		String description = null;
		
		if (myResponse.getInt("success") == 1)
		{
			
		     JSONObject entities = myResponse.getJSONObject("entities");
		     JSONObject myitem = entities.getJSONObject(ligne);
		     JSONObject labels = myitem.getJSONObject("labels");
		     JSONObject descriptions = myitem.getJSONObject("descriptions");
		     
		     
		     // CAS labels
		     if (labels.has("fr"))
		     {
		    	 JSONObject fr = labels.getJSONObject("fr"); 
		    	 label = fr.getString("value");
		     }
		     else {
		    	 label = "Pas de labels FR";
		     }

		     // CAS descriptions
		     if (descriptions.has("fr"))
		     {
		    	 JSONObject fr = descriptions.getJSONObject("fr"); 
		    	 description = fr.getString("value");
		     } else {
		    	 description = "Pas de descriptions";
		     }
		     

		     
		}
		else {
			System.out.println("Success = 0 ");
		}

		System.out.println("Fin WikiDATA: "+ligne);

		//System.out.println(ligne+";"+label+";"+description);
		FinalExport(ligne,label,description);

	}
	
	public void FinalExport(String ligne, String labels, String description) throws IOException
	{

		try (
				FileWriter f = new FileWriter("Final/Dictionnaire/"+nomfichier+".csv", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);
			) 
	 	{
            	p.println(ligne+";"+labels+";"+description);
            	compteur++;
            	System.out.println("Ligne "+compteur+" bien crée !");
        } catch (IOException i) {
        	System.out.println("Erreur lors de la création du fichier");
            i.printStackTrace();
        }
		

	}

}
