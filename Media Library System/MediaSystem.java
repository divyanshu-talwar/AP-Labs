// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;

//class MySongComp implements Comparator<Song>{
//	MySongComp(){};
//	@Override
//	public int compare(Song m1, Song m2) {
//		Integer x=m1.getRating();
//		Integer y=m2.getRating();
//		return x.compareTo(y);
//	}
//}
//
//class MyMovieComp implements Comparable<Movie>{
//	MyMovieComp(){}
//	public int compare(Movie m1, Movie m2) {
//		Integer x=m1.getRating();
//		Integer y=m2.getRating();
//		return x.compareTo(y);
//	}
//	@Override
//	public int compareTo(Movie o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//}

public class MediaSystem {
	
	public static HashMap<String, Movie> MovieMap = new HashMap<String, Movie>();
	public static HashMap<String, Song> SongMap = new HashMap<String, Song>();
	private int numS;
	private int numM;
	private static String serM;
	private static String serS;
	
	MediaSystem(){
		this.setSerFile();
//		this.iread_db();
		this.read_db();
//		this.listItems();
		this.write();
		this.setNum();
	}
	
	public int getSNum(){
		return this.numS;
	}
	public int getMNum(){
		return this.numM;
	}
	
	public void setSerFile(){
		this.serM="movie.dat";
		this.serS="song.dat";
	}
	public void setNum(){
		numM=MovieMap.size();
		numS=SongMap.size();
	}
	
	public static Object deserialize(String fileName) throws IOException,ClassNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}
	
	public static void serialize(Object obj, String fileName)throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		fos.close();
	}
	
	public void changeSRating(String name,int rating){
		boolean flag=true;
		Set<?> set = SongMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Song song= (Song) mEntry.getValue();
			if(song.title.equals(name)){
				System.out.println("Rating of song updated from "+song.rating+" to "+rating+".");
				song.setRating(rating);
				flag=false;
			}
		}
		if(flag){
			System.out.println("Song "+name+" not found.");
		}
	}
	
	public void iread_db(){
		String file="song.txt";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] string;
			while ((str = in.readLine()) != null) {
				String a,b,c,e,h;
				int d,g;
				double f;
				string=str.split(",");
				a=string[0];
				b=string[1];
				c=string[2];
				d=Integer.parseInt(string[3]);
				e=string[4];
				f=Double.parseDouble(string[5]);
				g=Integer.parseInt(string[6]);
				h=string[7];
				Song s=new Song(a,b,c,d,e,f,g,h);
				SongMap.put(s.title, s);
			}
			in.close();
		}
		catch (IOException e) {
			System.out.println("File Read Error");
		}
		
		file="movie.txt";
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] string;
			while ((str = in.readLine()) != null) {
				String a,b,d,g,h,i;
				int c,f;
				double e;
				char j;
				string=str.split(",");
				a=string[0];
				b=string[1];
				c=Integer.parseInt(string[2]);
				d=string[3];
				e=Double.parseDouble(string[4]);
				f=Integer.parseInt(string[5]);
				g=string[6];
				h=string[7];
				i=string[8];
				j=string[9].charAt(0);
				Movie m=new Movie(a,b,c,d,e,f,g,h,i,j);
				MovieMap.put(m.title, m);
			}
			in.close();
		}
		catch (IOException e) {
			System.out.println("File Read Error");
		}
	}
	
	
	public void read_db(){
		String file="song.txt";
		FileInputStream fil;
		try {
			fil = new FileInputStream(file);
			MyDecrypt de = new MyDecrypt(fil);
			String song=de.new_read();
			String[] lines= song.split("\n");
			int i=0;
			for(i=0;i<lines.length;i++){
				String[] string = lines[i].split(",");
				String a,b,c,e,h;
				int d,g;
				double f;
				a=string[0];
				b=string[1];
				c=string[2];
				d=Integer.parseInt(string[3]);
				e=string[4];
				f=Double.parseDouble(string[5]);
				g=Integer.parseInt(string[6]);
				h=string[7];
				Song s=new Song(a,b,c,d,e,f,g,h);
				SongMap.put(s.title, s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file="movie.txt";
		try {
			fil = new FileInputStream(file);
			MyDecrypt de = new MyDecrypt(fil);
			String movie=de.new_read();
			String[] lines= movie.split("\n");
			int i=0;
			for(i=0;i<lines.length;i++){
				String[] string = lines[i].split(",");
				String a,b,d,g,h,k;
				int c,f;
				double e;
				char j;
				a=string[0];
				b=string[1];
				c=Integer.parseInt(string[2]);
				d=string[3];
				e=Double.parseDouble(string[4]);
				f=Integer.parseInt(string[5]);
				g=string[6];
				h=string[7];
				k=string[8];
				j=string[9].charAt(0);
				Movie m=new Movie(a,b,c,d,e,f,g,h,k,j);
				MovieMap.put(m.title, m);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void write(){
		try {
			String file="song.txt";
			FileOutputStream fil=new FileOutputStream(file);
			MyEncrypt ee= new MyEncrypt(fil);
			Set<?> set = SongMap.entrySet();
			Iterator<?> iterator = set.iterator();
			while(iterator.hasNext()) {
				Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
				Song mNew=(Song) mEntry.getValue();
				String a,b,c,d,e,f,g,h,i="\n";
				a=mNew.getTitle();
				b=mNew.getMovie();
				c=mNew.getArtist();
				d=Integer.toString(mNew.getYear());
				e=mNew.getGenre();
				f=Double.toString(mNew.getSize());
				g=Integer.toString(mNew.getRating());
				h=mNew.getDur();
				StringJoiner joiner = new StringJoiner(",");
				joiner.add(a).add(b).add(c).add(d).add(e).add(f).add(g).add(h).add(i);
				String x = joiner.toString();
				ee.new_write(x);
				
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			String file="movie.txt";
			FileOutputStream fil=new FileOutputStream(file);
			MyEncrypt ee= new MyEncrypt(fil);
			
			Set<?> set = MovieMap.entrySet();
			Iterator<?> iterator = set.iterator();
			while(iterator.hasNext()) {
				Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
				Movie mNew=(Movie) mEntry.getValue();
				String a,b,c,d,e,f,g,h,i,j,k="\n";
				a=mNew.getTitle();
				b=mNew.getArtist();
				c=Integer.toString(mNew.getYear());
				d=mNew.getGenre();
				e=Double.toString(mNew.getSize());
				f=Integer.toString(mNew.getRating());
				g=mNew.getDur();
				h=mNew.getDirector();
				i=mNew.getProducer();
				j=Character.toString(mNew.getCert());
				StringJoiner joiner = new StringJoiner(",");
				joiner.add(a).add(b).add(c).add(d).add(e).add(f).add(g).add(h).add(i).add(j).add(k);
				String x = joiner.toString();
				ee.new_write(x);
				
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void callSerialize(){
//		MOVIE
		Set<?> set = MovieMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Movie mNew=null;
			try {
				serialize(mEntry.getValue(), serM);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			try {
				mNew = (Movie) deserialize(serM);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			System.out.println("movie Object::"+mEntry.getValue());
			System.out.println("movieNew Object::"+mNew);
			System.out.println();
		}
		
//		SONG
		set = SongMap.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Song mNew=null;
			try {
				serialize(mEntry.getValue(), serS);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			try {
				mNew = (Song) deserialize(serS);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			System.out.println("song Object::"+mEntry.getValue());
			System.out.println("songNew Object::"+mNew);
			System.out.println();
		}
		
	}

	public void genreSearch(String genre){
		boolean flag=true;
		Set<?> set = SongMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Song mNew=(Song) mEntry.getValue();
			if(mNew.genre.equals(genre)){
//				mNew.genre="Mridul";
				mNew.minDisplay();
				flag=false;
			}
		}
		if(flag)
			System.out.println("No songs found for this genre");
	}
	
	
	public void listItems() {
		// TODO Auto-generated method stub
		Set<?> set = MovieMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Movie mNew=(Movie) mEntry.getValue();
			mNew.display();
		}
		set = SongMap.entrySet();
		iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Song mNew=(Song) mEntry.getValue();
			mNew.display();
		}
	}
	public void changeMRating(String name, int rating) {
		boolean flag=true;
		Set<?> set = MovieMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Movie movie= (Movie) mEntry.getValue();
			if(movie.title.equals(name)){
				System.out.println("Rating of Movie updated from "+movie.rating+" to "+rating+".");
				movie.setRating(rating);
				flag=false;
			}
		}
		if (flag){
			System.out.println("Movie "+name+" not found.");
		}
		
		
	}
	public void searchSong(String name) {
		boolean flag=true;
		// TODO Auto-generated method stub
		Set<Entry<String, Song>> set = SongMap.entrySet();
		Iterator<Entry<String, Song>> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Song mNew=(Song) mEntry.getValue();
			if(mNew.getMovie().equals(name)){
				mNew.display();
				flag=false;
			}
		}
		if(flag){
			System.out.println("No songs found for the movie "+name);
		}
	}
	public void directorSearch(String director) {
		// TODO Auto-generated method stub
		boolean flag=true;
		Set<?> set = MovieMap.entrySet();
		Iterator<?> iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mEntry = (Map.Entry<?, ?>)iterator.next();
			Movie mNew=(Movie) mEntry.getValue();
			if(mNew.getDirector().equals(director)){
//				mNew.genre="Mridul";
				mNew.minDisplay();
				flag=false;
			}
		}
		if(flag)
			System.out.println("No movies found for this director");
	}

	public void callSort(int k) {
//		Sort songs
		List<Song> peopleByAge = new ArrayList<Song>(SongMap.values());
		Collections.sort(peopleByAge, new Comparator<Song>() {

	        public int compare(Song o1, Song o2) {
	            return o2.getRating() - o1.getRating();
	        }
	    });
		int i=1;
		for (Song p : peopleByAge) {
			if(i>k)
				break;
	        System.out.println(p.getTitle() + "\t" + p.getRating());
	        i++;
	    }
		
	}

}

