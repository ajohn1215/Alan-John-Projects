//Alan John 170236456
import java.util.*;
public class User {
    private String username;
    private ArrayList<PLaylist> playlists;

    public User(String username) {
        this.username = username;
        this.playlists = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void addPlaylist(PLaylist playlist) {
        playlists.add(playlist);
    }

    public void removePlaylist(String playlistTitle) {
        playlists.removeIf(playlist -> playlist.getPlaylistTitle().equals(playlistTitle));
    }

    public ArrayList<PLaylist> getPlaylists() {
        return playlists;
    }

    public PLaylist getPlaylist(String playlistTitle) {
        for (PLaylist playlist : playlists) {
            if (playlist.getPlaylistTitle().equals(playlistTitle)) {
                return playlist;
            }
        }
        return null;
    }

    public void makeCollaborativePlaylist(String playlistTitle, User friend) {
        PLaylist playlist = getPlaylist(playlistTitle);
        if (playlist != null) {
            friend.addPlaylist(playlist);
        }
    }


    public String toString() {
        return username;
    }
}
