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
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Metier.PageWikidata;
import Metier.PageWikipedia;

public class TitleP279 {
	
	String entree,sortie;
	int compteur;
	
	public TitleP279 (String entree, String sortie)
	{
		this.entree = entree;
		this.sortie = sortie;
	}
	
	public void start() throws URISyntaxException
	{
		Lecture_Fichier_WikipediaFR_UTF8();
	}
	
	
	public void Lecture_Fichier_WikipediaFR_UTF8() throws URISyntaxException 
	{
		String filePath = entree;

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
		
		PageWikidata d = new PageWikidata();
		String P1269_txt ="";
		String P2579_txt ="";
		
		
		if (myResponse.getInt("success") == 1)
		{
			
		     JSONObject entities = myResponse.getJSONObject("entities");
		     JSONObject myitem = entities.getJSONObject(ligne);
		     JSONObject claims = myitem.getJSONObject("claims");
		     		     
		     // CAS P279(sous-classe)
		     if (claims.has("P279"))
		     {
		    	 JSONArray P279 = claims.getJSONArray("P279"); 
		    	 
		    	 for (int i = 0; i < P279.length(); i++) {
					
			    	 JSONObject element = P279.getJSONObject(i);
			    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
			    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
			    	 JSONObject value = datavalue.getJSONObject("value");
			    	 d.addP279(value.getString("id"));
		    	 }

		     }
		     else {
		    	 d.addP279(null);
		     }
		     
		     // CAS P1269(aspect de)  && P2579(discipline) 
		     if (claims.has("P1269"))
		     {
		    	 
		    	 JSONArray P1269 = claims.getJSONArray("P1269"); 
		    	 JSONObject element = P1269.getJSONObject(0);
		    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
		    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
		    	 JSONObject value = datavalue.getJSONObject("value");
		    	 P1269_txt = value.getString("id");

		     }
		     else {
		    	 P1269_txt = null;
		     }
		     
		     // CAS P2579(discipline) 
		     if (claims.has("P2579"))
		     {
		    	 
		    	 JSONArray P2579 = claims.getJSONArray("P2579"); 
		    	 JSONObject element = P2579.getJSONObject(0);
		    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
		    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
		    	 JSONObject value = datavalue.getJSONObject("value");
		    	 P2579_txt = value.getString("id");
		    	 
		     }
		     else {
		    	 P2579_txt = null;
		     }
		    
		     
		}
		else {
			String message_erreur = ("Success = 0 ");
		}

		System.out.println("Fin WikiDATA: "+ligne);
		FinalExport(ligne,d.getP279_String(),P1269_txt,P2579_txt);

	}
	
	public void FinalExport(String ligne, String P279, String P1269, String P2579) throws IOException
	{
		/**
		 * Sortie default
		 * "Final/Dictionnaire/"+nomfichier+".csv"
		 */
		try (
				FileWriter f = new FileWriter(sortie, true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);
			) 
	 	{
            	p.println(ligne+";"+P279+";"+P1269+";"+P2579);
            	compteur++;
            	System.out.println("Ligne "+compteur+" bien crée !");
        } catch (IOException i) {
        	System.out.println("Erreur lors de la création du fichier");
            i.printStackTrace();
        }
		

	}

}
