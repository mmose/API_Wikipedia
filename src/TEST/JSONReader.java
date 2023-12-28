package TEST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONReader {
	
	public JSONReader()
	{
		
	}

	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }

		  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }

		  public static void main(String[] args) throws IOException, JSONException {
		    JSONObject json = readJsonFromUrl("https://fr.wikipedia.org/w/api.php?action=query&format=json&prop=pageprops%7Cpageviews%7Cpageimages%7Cextracts&indexpageids=1&generator=search&redirects=1&utf8=1&formatversion=1&ppprop=wikibase_item&pvipdays=1&pvipcontinue=&piprop=thumbnail&pithumbsize=120&exchars=700&explaintext=1&exsectionformat=plain&gsrsearch=Somaya_Bousa%C3%AFd&gsrlimit=1&gsrwhat=nearmatch&gsrinfo=totalhits");
		    System.out.println("My JSON"+json.toString());
		    //System.out.println(json.get("id"));
		  }

}