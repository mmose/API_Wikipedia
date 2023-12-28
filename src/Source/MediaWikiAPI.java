package Source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Metier.PageWikidata;
import Metier.PageWikipedia;


public class MediaWikiAPI {


	List<String> WikipediaFR_Log = new ArrayList<>();
	int compteur = 0;
	String nomfichier;
	
	public MediaWikiAPI (String nomfichier)
	{
		this.nomfichier = nomfichier;
	}
	
	public String EncodageURL(String saisie)
	{
		// SOURCE http://www.codeurjava.com/2015/05/encode-et-decode-un-url-string-java.html
	        try {
	          String urlencode = java.net.URLEncoder.encode(saisie, "UTF-8");
	          return urlencode;
	         // String urldecode = java.net.URLDecoder.decode(urlencode, "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return null;
	}
	
	public void Start() throws IOException, ClassNotFoundException, URISyntaxException
	{
		Lecture_Fichier_WikipediaFR_UTF8();
	}
	
	public void Lecture_Fichier_WikipediaFR_UTF8() throws URISyntaxException 
	{
		// SOURCE https://www.jackrutorial.com/2018/06/java-read-file-to-string-utf-8.html
		String filePath = "Repartisseur/Wikipedia/"+nomfichier+".txt";
		//String filePath = "Dictionnaire/Pack/"+nomfichier+".txt";
		
		StringBuilder sb = new StringBuilder();
		
		/**
		 * VERSION UTF8 try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"))) 
		 */
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) 
		
		{

		 String cLine;
		 while ((cLine = br.readLine()) != null) {
		  sb.append(cLine).append("\n");
		  
		  
		  try {
			WikipediaPageInfo(cLine);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		 }
		} catch (IOException e) {
		 e.printStackTrace();
		}

		//System.out.println("##### Use BufferedReader to read UTF-8 encoded data from a text file");
		//System.out.println(sb.toString());
	
	}
	

	
	public void WikipediaPageInfo(String ligne) throws IOException, JSONException, URISyntaxException 
	{

		String url = "https://fr.wikipedia.org/w/api.php?action=query&format=json&prop=pageprops%7Cpageviews%7Cpageimages%7Cextracts%7Cinfo&indexpageids=1&generator=search&redirects=1&utf8=1&formatversion=1&ppprop=wikibase_item&pvipdays=30&pvipcontinue=&piprop=thumbnail&pithumbsize=120&exchars=500&exintro=1&explaintext=1&exsectionformat=plain&inprop=url%7Cwatched&gsrsearch="+EncodageURL(ligne)+"&gsrlimit=1&gsrwhat=nearmatch&gsrinfo=totalhits";
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

	     ExtractJSONWikipedia(ligne, response.toString());

	}
	
	public void ExtractJSONWikipedia(String ligne, String response) throws JSONException, IOException, URISyntaxException
	{
		
		PageWikipedia p = new PageWikipedia();
		
		JSONObject myResponse = new JSONObject(response.toString());
		
		//System.out.println("statusCode- "+myResponse.getString("statusCode"));
		
		System.out.println("Début WikiPEDIA: "+ligne);
		
		if (myResponse.has("query"))
		{
			
	     JSONObject query = myResponse.getJSONObject("query");
	     
	     // CAS REDIRECTS
	     if (query.has("redirects"))
	     {
		     JSONArray redirects = query.getJSONArray("redirects");
		     JSONObject redirect = redirects.getJSONObject(0);
		     p.setRedirection_id(redirect.getInt("pageid"));
		     p.setRedirection_title(ligne);
		     //p.setRedirection_title(redirect.getString("from"));
		     if (redirects.length()>1)
		     {
		    	 p.setMessage_erreur("Attention il y a plusieurs redirects !");
		     }
	     }
	     else 
	    	 {
	    	 	p.setRedirection_id(0); 
	    	 	p.setRedirection_title(null);
	    	 }
	    
	   
	     // CAS PAGEIDS
	     if (query.has("pageids"))
	     {
		     JSONArray pageids = query.getJSONArray("pageids");
		     p.setId(pageids.getInt(0)); 
	     }
	     else  
	    	 {
	    	 p.setId(0);;
	    	 }

	     
	     // CAS PAGES
	     if (query.has("pages"))
	     {
		     JSONObject pages = query.getJSONObject("pages");
		     JSONObject page = pages.getJSONObject(String.valueOf(p.getId()));
		     p.setTitle(page.getString("title"));
		     p.setLien_wiki(page.getString("fullurl"));
		     
		     if (page.has("pageprops"))
		     {
			     JSONObject pageprops = page.getJSONObject("pageprops");
			     p.setWikibaseitem(pageprops.getString("wikibase_item"));
		     }
		     else {
		    	 System.out.println("Pas de query.pages."+p.getId()+"pageprops");
		     }

		     if (page.has("pageviews"))
		     {
			     JSONObject pageviews = page.getJSONObject("pageviews");
			     int total = 0;
			     
			     for (final Iterator<String> iter = pageviews.keys(); iter.hasNext();) {
			    	    final String key = iter.next();

			    	    try {
			    	    				    	    			    	  
		    	        final Object objectdates = pageviews.get(key);
		    	        
		    	        if (!objectdates.equals(null))
		    	        {
		    	        	final int dates = (int) objectdates;
			    	        total = total + dates;
		    	        }
		    	             	      
			    	    } catch (final JSONException e) {
			    	        // Something went wrong!
			    	    }
			    	    
			    	}
			     
			     p.setPageviews(total);
		     }
		     else {
		    	 p.setPageviews(0);
		     }
		     
		     
		     if (page.has("thumbnail"))
		     {
			     JSONObject thumbnail = page.getJSONObject("thumbnail");
			     p.setThumbnail(thumbnail.getString("source"));
			     
		     }
		     else {
		    	 p.setThumbnail(null);
		     }

		     if (page.has("extract"))
		     {
		    	 
		    	 //String extract = new String(page.getString("extract").getBytes(), "UTF-8");
		    	 String extract = page.getString("extract");
		    	 extract = extract.replace("\n", "<p>").replace("\r", "<p>").replace(";", "<p>");
		    	 p.setExtracts(""+extract+"");
		     }
		     else {
		    	 p.setExtracts(null);
		     }
		     
		     
	     }
	     //else System.out.println("Pas de query.pages");

		}
		else 
		{
			System.out.println("No Query !");
			p.PageWikipediaNULL();
			p.setMessage_erreur("No Query! Page Wikipedia NULL:"+ligne);
			
		}
		System.out.println("Fin WikiPEDIA: "+ligne);	

		
	     
	     //System.out.println(p.toString());
	     //System.out.println("Page id : "+p.getId() +"\n"+"Titre de la page :"+p.getTitle()+"\n"+"WikiData : "+p.getWikibaseitem()+"\n"+"Views:"+p.getPageviews()+"\n Image:"+p.getThumbnail()+"\n extract:"+p.getExtracts()+"\n redirection id:"+p.getRedirection_id()+"\n"+p.getRedirection_title());
	     
	    WikidataPageInfo(ligne,p);

	}
	
	public void WikidataPageInfo(String ligne,PageWikipedia p) throws IOException, JSONException, URISyntaxException 
	{
		
		if (p.getWikibaseitem() != null)
		{
			 String url = "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="+p.getWikibaseitem()+"&languages=fr&format=json";
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

		     ExtractJSONWikidata(ligne,p ,response.toString());	
		}
		else 
		{
			PageWikidata d = new PageWikidata();
			d.PageWikipediaNULL();
			d.setMessage_erreur("Page Wikidata NULL Wikibase_item non existant");
			FinalExport(p,d);
		}
			
		
	}
	
	public void ExtractJSONWikidata(String ligne, PageWikipedia p ,String response) throws JSONException, IOException
	{

		PageWikidata d = new PageWikidata();
		d.setWikibase_item(p.getWikibaseitem());
		
		JSONObject myResponse = new JSONObject(response.toString());
		
		System.out.println("Début WikiDATA: "+ligne);
		
		if (myResponse.getInt("success") == 1)
		{
			
		     JSONObject entities = myResponse.getJSONObject("entities");
		     JSONObject myitem = entities.getJSONObject(p.getWikibaseitem());
		     JSONObject claims = myitem.getJSONObject("claims");
		     
		     
		     // CAS P31
		     if (claims.has("P31"))
		     {
		    	 JSONArray P31 = claims.getJSONArray("P31"); 

		    	 for (int i = 0; i < P31.length(); i++) {
					
			    	 JSONObject element = P31.getJSONObject(i);
			    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
			    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
			    	 JSONObject value = datavalue.getJSONObject("value");
			    	 d.addP31(value.getString("id"));
		    	 }

		     }
		     else {
		    	 d.addP31(null);
		     }
		     
		     
		     // CAS P361
		     if (claims.has("P361"))
		     {
		    	 JSONArray P361 = claims.getJSONArray("P361"); 
		    	 
		    	 for (int i = 0; i < P361.length(); i++) {
					
			    	 JSONObject element = P361.getJSONObject(i);
			    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
			    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
			    	 JSONObject value = datavalue.getJSONObject("value");
			    	 d.addP361(value.getString("id"));
		    	 }

		     }
		     else {
		    	 d.addP361(null);
		     }
		     
		     
		     // CAS P279
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
		     
		     // CAS P8225
		     if (claims.has("P8225"))
		     {
		    	 JSONArray P8225 = claims.getJSONArray("P8225"); 
		    	 
		    	 for (int i = 0; i < P8225.length(); i++) {
					
			    	 JSONObject element = P8225.getJSONObject(i);
			    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
			    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
			    	 JSONObject value = datavalue.getJSONObject("value");
			    	 d.addP8225(value.getString("id"));
		    	 }

		     }
		     else {
		    	 d.addP8225(null);
		     }
		     
		     // CAS P106
		     if (claims.has("P106"))
		     {
		    	 JSONArray P106 = claims.getJSONArray("P106"); 
		    	 
		    	 for (int i = 0; i < P106.length(); i++) {
					
			    	 JSONObject element = P106.getJSONObject(i);
			    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
			    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
			    	 JSONObject value = datavalue.getJSONObject("value");
			    	 d.addP106(value.getString("id"));
		    	 }

		     }
		     else {
		    	 d.addP106(null);
		     }
		     
		     // CAS P856
		     if (claims.has("P856"))
		     {
		    	 JSONArray P856 = claims.getJSONArray("P856"); 
		    	 JSONObject element = P856.getJSONObject(0);
		    	 JSONObject mainsnak = element.getJSONObject("mainsnak");
		    	 JSONObject datavalue = mainsnak.getJSONObject("datavalue");
		    	 d.setSite_officiel(datavalue.getString("value"));
		     }
		     else {
		    	 d.setSite_officiel(null);
		     }
		     
		}
		else {
			d.setMessage_erreur("Success = 0 ");
		}

		System.out.println("Fin WikiDATA: "+ligne);

	    //System.out.println(p.FormatCSV()+"\n"+d.FormatCSV());
		FinalExport(p,d);

	}
	
	public void FinalExport(PageWikipedia pw, PageWikidata pd) throws IOException
	{

		try (
				//FileWriter f = new FileWriter("Final/wikipediaFR-"+nomfichier+".csv", true);
				FileWriter f = new FileWriter("Final/Wikipedia/wikipediaFR-"+nomfichier+".csv", true);
				//FileWriter f = new FileWriter("Final/Dictionnaire/Dictionnaire_Mots_In_Wikipedia.csv", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);
			) 
	 	{
            	p.println(pw.FormatCSV()+";"+pd.FormatCSV());
            	compteur++;
            	System.out.println("Fichier "+compteur+" bien crée !");
        } catch (IOException i) {
        	System.out.println("Erreur lors de la création du fichier");
            i.printStackTrace();
        }
		
		/*if (compteur == 3385)
		{
			System.out.println("Fin de la lecture du fichier");
		}*/
		
		
		/***
		 * PARTIE LOG
		 */
		//WikipediaFR_Modifie.add(line);
	}
	
	
}
