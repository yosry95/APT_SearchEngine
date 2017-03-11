import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
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
	
	PrintWriter file ;
	int l =0;

	
	public Factory() {
		db.connect();
	}
	
	public boolean Tokenize (String page) {
		try {
			file = new PrintWriter("file"+l+".txt", "UTF-8");
			l++;
			file.println(page);
			
			TokenStream tokenizer = englishAnalyzer.tokenStream(null, page);
			tokenizer.reset();
			//StringBuilder result = new StringBuilder();
			CharTermAttribute attr = tokenizer.addAttribute(CharTermAttribute.class);
			while (tokenizer.incrementToken()){
				//result.append(" "+attr+" ");
				tokens.addElement(attr.toString());
				//System.out.print(attr.toString());
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
			//PrintStream file = new PrintStream(new FileOutputStream(url+".txt"));
			file.println(url);
			file.close();
			SiteHits site;
			System.out.println("indexing an new page");
			System.out.println("tokens size before start: "+tokens.size());
			System.out.println("");
			for (int i=0;i<tokens.size();i++){
				String st = tokens.elementAt(i);
				//System.out.print(st);
				site = new SiteHits(st,st,url);
				int pos;
				while (tokens.contains(st)){
					pos = tokens.indexOf(st);
					site.addHit(pos, "body");
					//System.out.println(pos);
					tokens.remove(st);
				}
				if (lexion.containsKey(st)){
					System.out.println("found word "+ st + " in the lexion");
					db.updateDocument(site);
				}else {
					lexion.put(st, 0);
					System.out.println("added word "+ st + " in the lexion");
					db.createDocument(site);
				}
			}
			return true;
		} catch (Exception exp){
			System.out.println("Error while indexing a word " + exp.getMessage());
			System.out.println("");
			return false;
		}
	}
	
	public void close () {
		englishAnalyzer.close();
	}
}












