package TEST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Metier.PageWikidata;
import Metier.PageWikipedia;
import Source.MediaWikiAPI;
import Source.Repartisseur;
import Source.TitleP31;

public class TEST {

	public static void main(String[] args) throws ClassNotFoundException, IOException, JSONException, URISyntaxException {
	
		
		//PageWikipedia p = new PageWikipedia();
		//p.setWikibaseitem("Q17");

		
		//PageWikidata d = new PageWikidata();
		//d.addP31("Un article");
		//d.addP31("Un second article");
		//System.out.println(d.getP31_String());
		
		
		//PageWikipedia test = new PageWikipedia(null,null,null,null,null,0,null,null,null);
		//System.out.println(test.FormatCSV());
		
		//PageWikidata test = new PageWikidata();
		//test.PageWikipediaNULL();
		//System.out.println(test.FormatCSV());
		
		
		/** 06 12 2021  __ JAI DEJA PROGRAMME LE PROCHAIN DEPART **/
		for (int i = 271; i < 280; i++) {
			String nomfichier = "Pack"+i;
			System.out.println("--------------------------------------------------------------Début lecture fichier "+nomfichier);
			MediaWikiAPI d = new MediaWikiAPI(nomfichier);
			d.Start();
			System.out.println("--------------------------------------------------------------Fin lecture fichier "+nomfichier);
			
		}
		
		//TitleP31 tp31 = new TitleP31("P31_populaire");
		//tp31.start();
		
		
		//MediaWikiAPI d = new MediaWikiAPI("Pack7");
		//d.Start();
		
		//d.WikipediaPageInfo("Guillemet");
		//d.Start();
		//d.Lecture_Fichier_WikipediaFR_UTF8();
		//d.WikipediaPageInfo("Sergio_Ramos");
		
		//d.WikidataPageInfo(p);
		
		
		//Repartisseur r1 = new Repartisseur();
		//r1.StartDictionnaire();
		
	}
	

}


