
public class Souce {

	public static void main(String[] args){
		System.out.println("Hello World");
		Database db  = new Database();
		db.connect();
		
		SiteHits t = new SiteHits("forcing", "force", "www.linkedin.com");
		t.addHit(3, "title");
		t.addHit(56, "body");
		t.addHit(789, "body");
		
		db.findDocument();
		
		db.updateDocument();
	}
}
