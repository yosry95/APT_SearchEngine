import java.util.Vector;

public class SiteHits {
	
	private String word;
	private String stemmed;
	private String site;
	
	private Vector<Pair<Integer,String>> hitList = new Vector<>();
	
	int hitsCounter =0;
	
	public SiteHits (String word, String stemmed, String site){
		this.site = site;
		this.word = word;
		this.stemmed = stemmed;
	}
	
	public String getWord() {
		return word;
	}

	public String getStemmed() {
		return stemmed;
	}

	public String getSite() {
		return site;
	}
	
	public void addHit (int pos,String type){
		Pair<Integer,String> pr = new Pair<Integer,String>(pos,type);
		hitList.addElement(pr);
	}
	
	public boolean hasHit() {
		return hitsCounter < hitList.size();
	}
	
	public Pair<Integer,String> nextHit(){
		hitsCounter ++;
		return hitList.elementAt(hitsCounter - 1);
	}
	
	public void clearHits() {
		hitList.clear();
	}
}
