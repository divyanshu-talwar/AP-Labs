// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061

import java.util.Scanner;

public class RunMain {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		MediaSystem media = new MediaSystem();
		boolean flag=true;
//		Making this MENU-BASED
		while(flag){
			System.out.println("Press 1 to Serialize");
			System.out.println("Press 2 to view list of all songs/movies");
			System.out.println("Press 3 to view top k songs");
			System.out.println("Press 4 to search songs based on Genre");
			System.out.println("Press 5 to search movies based on Director");
			System.out.println("Press 6 to edit rating of a song");
			System.out.println("Press 7 to edit rating of a movie");
			System.out.println("Press 8 to display number of songs/movies");
			System.out.println("Press 9 to search all songs of a given movie");
			System.out.println("Press anything else to exit");
			int opt=in.nextInt();
			switch(opt){
			case 1: media.callSerialize(); break;
			case 2: media.listItems(); break;
			case 3: System.out.println("Sort: Enter the number of songs you want to see");
				int k=in.nextInt();
				if(k>media.getSNum())
				{
					System.out.println("Value of K exceeds the number of songs in the system.\nBut still I will give you all songs");
					media.callSort(media.getSNum());
				}
				media.callSort(k);
				break;
			case 4: System.out.println("Enter genre to search");
				String genre=in.nextLine();
				genre=in.nextLine();
				media.genreSearch(genre);
				break;
			case 5: System.out.println("Enter Director to search");
				String director=in.nextLine();
				director=in.nextLine();
				media.directorSearch(director);
				break;
			case 6: System.out.println("Enter Song whose rating you want to change");
				String name=in.nextLine();
				name=in.nextLine();
				System.out.println("Enter new rating");
				int rating = in.nextInt();
				media.changeSRating(name, rating);
				media.write();
				break;
			case 7: System.out.println("Enter Movie whose rating you want to change");
				name=in.nextLine();
				name=in.nextLine();
				System.out.println("Enter new rating");
				rating = in.nextInt();
				media.changeMRating(name, rating);
				media.write();
				break;
			case 8: System.out.println("Number of songs in the library: "+media.getSNum());
				System.out.println("Number of movies in the library: "+ media.getMNum());
				break;
			case 9: System.out.println("Enter Movie name");
				name=in.nextLine();
				name=in.nextLine();
				media.searchSong(name);
				break;
			default : flag=false;
			}
		}
	}

}
