//Alan John 170236456
public class Song implements Comparable<Song> {
    private String songName;
    private String artistName;
    private String duration;

    public Song(String songName, String artistName, String duration) {
        this.songName = songName;
        this.artistName = artistName;
        this.duration = duration;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getDuration() {
        return duration;
    }

    public int compareTo(Song other) {
        int result = songName.compareTo(other.songName);
        if (result != 0) 
        	return result;
        else
        	result = artistName.compareTo(other.artistName);
        return result;
    }


    public String toString() {
        return songName + " by " + artistName + " (" + duration + ")";
    }
}
