import java.io.IOException;
import java.util.Vector;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;


public class Factory 
{
	StandardAnalyzer englishAnalyzer = new StandardAnalyzer();
	Vector<String> tokens = new Vector<String>();
	
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
	
	public void close () {
		englishAnalyzer.close();
	}
}












