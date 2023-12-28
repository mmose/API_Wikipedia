package Metier;

import java.util.ArrayList;

public class PageWikidata {

	String wikibase_item;
	String site_officiel;
	String message_erreur;
	//ArrayList<String> P31,P361,P279,P856,P8225 = new ArrayList<String>();
	ArrayList<String> P31 = new ArrayList<String>();
	ArrayList<String> P361 = new ArrayList<String>();
	ArrayList<String> P279= new ArrayList<String>();
	ArrayList<String> P106 = new ArrayList<String>();
	ArrayList<String> P8225 = new ArrayList<String>();

	
	public PageWikidata()
	{
		
	}
	
	public void PageWikipediaNULL()
	{
		this.wikibase_item=null;
		this.site_officiel=null;
		this.P31.add(null);
		this.P361.add(null);
		this.P279.add(null);
		this.P106.add(null);
		this.P8225.add(null);
	}
	
	public String FormatCSV()
	{
		if (message_erreur != null)
		{
			return getP31_String()+";"+getP361_String()+";"+getP279_String()+";"+getP8225_String()+";"+getP106_String()+";"+site_officiel+";"+message_erreur;	
		}
		else 
		return getP31_String()+";"+getP361_String()+";"+getP279_String()+";"+getP8225_String()+";"+getP106_String()+";"+site_officiel+";"+"Pas de message d'erreur Wikidata ";
	}
	
	
	public String getMessage_erreur() {
		return message_erreur;
	}

	public void setMessage_erreur(String message_erreur) {
		
		this.message_erreur = getMessage_erreur() + "." + message_erreur ;
	}
	
	
	public String getWikibase_item() {
		return wikibase_item;
	}

	public void setWikibase_item(String wikibase_item) {
		this.wikibase_item = wikibase_item;
	}
	

	public String getSite_officiel() {
		return site_officiel;
	}

	public void setSite_officiel(String site_officiel) {
		this.site_officiel = site_officiel;
	}

	public String getP31_String() {
		String formatted = P31.toString();
		formatted = formatted.replace("[", "{").replace("]", "}");
		return formatted;
	}

	public String getP361_String() {
		String formatted = P361.toString();
		formatted = formatted.replace("[", "{").replace("]", "}");
		return formatted;
	}

	public String getP279_String() {
		String formatted = P279.toString();
		formatted = formatted.replace("[", "{").replace("]", "}");
		return formatted;
	}
	
	public String getP106_String() {
		String formatted = P106.toString();
		formatted = formatted.replace("[", "{").replace("]", "}");
		return formatted;
	}

	public String getP8225_String() {
		String formatted = P8225.toString();
		formatted = formatted.replace("[", "{").replace("]", "}");
		return formatted;
	}

	public void addP31(String p31) {
		P31.add(p31);
	}

	public void addP361(String p361) {
		P361.add(p361);
	}

	public void addP279(String p279) {
		P279.add(p279);
	}
	
	public void addP106(String p106) {
		P106.add(p106); 
	}

	public void addP8225(String p8225) {
		P8225.add(p8225); 
	}


	
	
}
