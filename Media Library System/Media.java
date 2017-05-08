// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061

import java.io.Serializable;

abstract public class Media implements Comparable<Media>,Serializable{
	protected String title;
	protected String artist;
	protected int year;
	protected double size;
	protected Integer rating;
	protected int durHr;
	protected int durMin;
	protected String dur;
	protected String genre;
	
	@Override
	public int compareTo(Media m) {
		// TODO Auto-generated method stub
		return this.rating.compareTo(m.rating);
	}
	
//	GETTERS
	public String getTitle(){
		return this.title;
	}
	public String getArtist(){
		return this.artist;
	}
	public int getYear(){
		return this.year;
	}
	public double getSize(){
		return this.size;
	}
	public Integer getRating(){
		return this.rating;
	}
	
	public String getDur(){
		return this.dur;
	}
	
	public String getGenre(){
		return this.genre;
	}
	
	
//	SETTERS
	public void setRating(int r){
		this.rating=r;
	}

}

class Song extends Media{
	private String movie;
	Song(String a, String b, String c, int d, String e, double f, int g, String h){
		this.title=a;
		this.movie=b;
		this.artist=c;
		this.year=d;
		this.genre=e;
		this.size=f;
		this.rating=g;
		this.dur=h;
		String[] parts = h.split(":");
		this.durHr=Integer.parseInt(parts[0]);
		this.durMin=Integer.parseInt(parts[1]);
	}
	public String getMovie(){
		return this.movie;
	}
	public void display(){
		System.out.println("Song Title: "+this.title);
		System.out.println("Movie: "+this.movie);
		System.out.println("Artist: "+this.artist);
		System.out.println("Year of Release: "+this.year);
		System.out.println("Genre: "+this.genre);
		System.out.println("Size: "+this.size+" MB");
		System.out.println("Rating: "+this.rating);
		System.out.println("Duration (mins): "+this.dur);
		System.out.println();
	}
	public void minDisplay(){
		System.out.println("Song Title: "+this.title);
		System.out.println("Rating: "+this.rating);
		System.out.println();
	}
	public String toString(){
		return "Song{title="+title+",artist="+artist+"rating"+rating+"}";
	}
}

class Movie extends Media{
	private String director;
	private String producer;
	char certification;
	Movie(String a, String b, int c, String d, double e, int f, String g, String h, String i, char j){
		this.title=a;
		this.artist=b;
		this.year=c;
		this.genre=d;
		this.size=e;
		this.rating=f;
		this.dur=g;
		String[] parts = g.split(":");
		this.durHr=Integer.parseInt(parts[0]);
		this.durMin=Integer.parseInt(parts[1]);
		this.director=h;
		this.producer=i;
		this.certification=j;
	}
	
	public String getDirector(){
		return this.director;
	}
	
	public String getProducer(){
		return this.producer;
	}
	
	public char getCert(){
		return this.certification;
	}
	
	public void display(){
		System.out.println("Movie Name: "+this.title);
		System.out.println("Artist: "+this.artist);
		System.out.println("Year of Release: "+this.year);
		System.out.println("Genre: "+this.genre);
		System.out.println("Size: "+this.size+" GB");
		System.out.println("Rating: "+this.rating);
		System.out.println("Duration (hrs): "+this.dur);
		System.out.println("Director: "+this.director);
		System.out.println("Producer: "+this.producer);
		System.out.println("Certification: "+this.certification);
		System.out.println();
	}
	
	public void minDisplay(){
		System.out.println("Movie Title: "+this.title);
		System.out.println("Director: "+this.director);
		System.out.println();
	}
	
	public String toString(){
		return "Movie{title="+title+",artist="+artist+"rating"+rating+"}";
	}
}
