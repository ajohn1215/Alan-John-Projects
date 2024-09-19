//Alan John 170236456
import java.util.*;
public class PLaylist {
    private String playlistTitle;
    private ArrayList<Song> songs;

    public PLaylist(String playlistTitle) {
        this.playlistTitle = playlistTitle;
        this.songs = new ArrayList<>();
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(String songTitle) {
        songs.removeIf(song -> song.getSongName().equals(songTitle));
    }

    public void swapSongs(Song song1, Song song2) {
        int index1 = songs.indexOf(song1);
        int index2 = songs.indexOf(song2);
        if (index1 != -1 && index2 != -1) {
            Collections.swap(songs, index1, index2);
        }
    }

    public String getTotalDuration() {
        int totalSeconds = songs.stream()
                .mapToInt(song -> {
                    String[] durationParts = song.getDuration().split(":");
                    return Integer.parseInt(durationParts[0]) * 60 + Integer.parseInt(durationParts[1]);
                })
                .sum();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return minutes + " min, " + seconds + " sec";
    }

    public void sortSongs() {
        Collections.sort(songs);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(playlistTitle).append(" - ").append(getTotalDuration()).append("\n");
        for (int i = 0; i < songs.size(); i++) {
            sb.append(i + 1).append(". ").append(songs.get(i)).append("\n");
        }
        return sb.toString();
    }
}