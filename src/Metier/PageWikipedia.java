package Metier;

public class PageWikipedia {
	
	int id,redirection_id,pageviews;
	String title,redirection_title,wikibaseitem,extracts,thumbnail,lien_wiki,message_erreur;

	

	public PageWikipedia()
	{
		
	}
	
	public PageWikipedia(int id,String title,int redirection_id, String redirection_title, String wikibaseitem, int pageviews, String date_pageviews, String extracts, String thumbnail)
	{
		this.id=id;
		this.title=title;
		this.redirection_id=redirection_id;
		this.redirection_title=redirection_title;
		this.wikibaseitem=wikibaseitem;
		this.pageviews=pageviews;
		this.extracts=extracts;
		this.thumbnail=thumbnail;
	}
	
	public void PageWikipediaNULL()
	{
		this.id=0;
		this.title=null;
		this.redirection_id=0;
		this.redirection_title=null;
		this.wikibaseitem=null;
		this.pageviews=0;
		this.extracts=null;
		this.thumbnail=null;
		
	}
	
	public String FormatCSV()
	{
		if (message_erreur != null)
		{
			return id+";"+title+";"+redirection_id+";"+redirection_title+";"+wikibaseitem+";"+pageviews+";"+extracts+";"+thumbnail+";"+lien_wiki+";"+message_erreur;	
		}
		else 
		return id+";"+title+";"+redirection_id+";"+redirection_title+";"+wikibaseitem+";"+pageviews+";"+extracts+";"+thumbnail+";"+lien_wiki+";"+"Pas de message d'erreur Wikipedia";
	}
	

	@Override
	public String toString() {
		return "PageWikipedia [id=" + id + ", title=" + title + ", redirection_id=" + redirection_id
				+ ", redirection_title=" + redirection_title + ", wikibaseitem=" + wikibaseitem + ", pageviews="
				+ pageviews + ", extracts=" + extracts + ", thumbnail=" + thumbnail + ", lien_wiki=" + lien_wiki
				+ ", message_erreur=" + message_erreur + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRedirection_id() {
		return redirection_id;
	}

	public void setRedirection_id(int redirection_id) {
		this.redirection_id = redirection_id;
	}

	public String getRedirection_title() {
		return redirection_title;
	}

	public void setRedirection_title(String redirection_title) {
		this.redirection_title = redirection_title;
	}

	public String getWikibaseitem() {
		return wikibaseitem;
	}

	public void setWikibaseitem(String wikibaseitem) {
		this.wikibaseitem = wikibaseitem;
	}

	public int getPageviews() {
		return pageviews;
	}

	public void setPageviews(int pageviews) {
		this.pageviews = pageviews;
	}


	public String getExtracts() {
		return extracts;
	}

	public void setExtracts(String extracts) {
		this.extracts = extracts;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	

	public String getLien_wiki() {
		return lien_wiki;
	}

	public void setLien_wiki(String lien_wiki) {
		this.lien_wiki = lien_wiki;
	}

	public String getMessage_erreur() {
		return message_erreur;
	}

	public void setMessage_erreur(String message_erreur) {
		
		this.message_erreur = getMessage_erreur() + "." + message_erreur ;
	}
	
	
	
	
}
