import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;


public class Factory 
{
	StandardAnalyzer englishAnalyzer = new StandardAnalyzer();
	Vector<String> tokens = new Vector<String>();
	Database db  = new Database();
	HashMap<String, Integer> lexion = new HashMap<String,Integer>();

	
	public Factory() {
		db.connect();
	}
	
	public boolean Tokenize (String page) {
		try {
			TokenStream tokenizer = englishAnalyzer.tokenStream(null, page);
			tokenizer.reset();
			//StringBuilder result = new StringBuilder();
			CharTermAttribute attr = tokenizer.addAttribute(CharTermAttribute.class);
			while (tokenizer.incrementToken()){
				//result.append(" "+attr+" ");
				tokens.addElement(attr.toString());
			}
			//System.out.print(result);
			tokenizer.end();
			tokenizer.close();
			return true;
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean Index(String url) {
		try {
			SiteHits site;
			for (String st : tokens){
				site = new SiteHits(st,st,url);
				while (tokens.contains(st)){
					site.addHit(tokens.indexOf(st), "body");
					tokens.remove(st);
				}
				if (lexion.containsKey(st)){
					db.updateDocument(site);
				}else {
					lexion.put(st, 0);
					db.createDocument(site);
				}
			}
			return true;
		} catch (Exception exp){
			System.out.println("Error while indexing a word ");
			return false;
		}
	}
	
	public void close () {
		englishAnalyzer.close();
	}
}












