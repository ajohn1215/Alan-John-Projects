//Alan John 170236456
import java.util.*;

public class MusicService {
    private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static ArrayList<PLaylist> getUserPlaylists(User user) {
        return user.getPlaylists();
    }
    public static void main(String[] args) {
    	PLaylist p0 = new PLaylist("Study Songs");
    	Song s1 = new Song("Studying1", "ABC", "3:00");
    	Song s2 = new Song("Studying2", "XYZ", "4:50");
    	p0.addSong(s1);
    	p0.addSong(new Song("Studying3", "CDE", "2:50"));
    	p0.addSong(new Song("Studying4", "EFG", "3:25"));
    	PLaylist p1 = new PLaylist("Workout Songs");
    	p1.addSong(new Song("Exercising1", "JKL", "3:00"));
    	p1.addSong(new Song("Exercising2", "OPQRS", "2:50"));
    	p1.addSong(new Song("Exercising3", "Wxyz", "4:35"));
    	p1.addSong(new Song("Exercising3", "Stu", "3:25"));
    	User u0 = new User("Paul");
    	User u1 = new User("Mary");
    	MusicService.addUser(u0);
    	MusicService.addUser(u1);
    	u0.addPlaylist(p0);
    	u1.addPlaylist(p1);
    	System.out.println("ORIGINAL STUDY SONGS PLAYLIST BY "+u0.getUsername());
    	for(PLaylist p : MusicService.getUserPlaylists(u0)) {
    	  System.out.println(p);
    	}
    	System.out.println("ORIGINAL STUDY SONGS PLAYLIST BY "+u1.getUsername());
    	for(PLaylist p : MusicService.getUserPlaylists(u1)) {
    	  System.out.println(p);
    	}
    	u0.makeCollaborativePlaylist("Study Songs", u1);
    	u0.getPlaylist("Study Songs").setPlaylistTitle("Study Songs with "+u1.getUsername());
    	u1.getPlaylist("Study Songs with "+u1.getUsername()).removeSong("Studying4");
    	u1.getPlaylist("Study Songs with "+u1.getUsername()).addSong(s2);
    	u1.getPlaylist("Study Songs with "+u1.getUsername()).swapSongs(s1, s2);
    	u1.getPlaylist("Study Songs with "+u1.getUsername()).swapSongs(new Song("Does Not Exist", "In Playlist", "5:00"), s2);
    	System.out.println("UPDATED PLAYLISTS BY "+u0.getUsername());
    	for(PLaylist p : MusicService.getUserPlaylists(u1)) {
    	  System.out.println(p);
    	}
    	u0.getPlaylist("Study Songs with "+u1.getUsername()).sortSongs();
    	u1.getPlaylist("Workout Songs").sortSongs();
    	System.out.println("AFTER SORTING BOTH PLAYLISTS:");
    	for(PLaylist p : MusicService.getUserPlaylists(u1)) {
    	  System.out.println(p);
    	}
    }
}
