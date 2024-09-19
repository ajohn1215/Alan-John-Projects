import java.util.*;

/**
 * SongRecord class with title, artist, and duration in minutes and seconds constructors.
 */
class SongRecord {
    private String title;
    private String artist;
    private int songMinutes;  
    private int songSeconds;

    /**
     * Default constructor for SongRecord.
     */
    public SongRecord() {
        this.title = "";
        this.artist = "";
        this.songMinutes = 0;
        this.songSeconds = 0;
    }

    /**
     * 
     * @return the song title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title for the song.
     * 
     * @param title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return the artist of the song
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets song artist.
     * 
     * @param artist to set
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**    
     * 
     * @return the length of the song in minutes
     */
    public int getSongMinutes() {
        return songMinutes;
    }

    /**
     * Sets the minutes of the song.
     * 
     * @param songMinutes the minutes to set
     * @throws IllegalArgumentException if the minutes are negative
     */
    public void setSongMinutes(int songMinutes) throws IllegalArgumentException {
        if (songMinutes >= 0)
            this.songMinutes = songMinutes;
        else
            throw new IllegalArgumentException("The amount of minutes cannot be negative");
    }

    /**
     * 
     * @return the length of the song in seconds
     */
    public int getSongSeconds() {
        return songSeconds;
    }

    /**
     * Sets the seconds of the song.
     * 
     * @param songSeconds the seconds to set
     * @throws IllegalArgumentException if the seconds are not between 0 and 59
     */
    public void setSongSeconds(int songSeconds) throws IllegalArgumentException {
        if (songSeconds >= 0 && songSeconds <= 59)
            this.songSeconds = songSeconds;
        else
            throw new IllegalArgumentException("The amount of seconds needs to be between 0 and 59");
    }
    /**
     * Constructor for SongRecord
     * 
     * @param title the title of the song
     * @param artist the artist of the song
     * @param songMinutes the length of the song in minutes
     * @param songSeconds the length of the song in seconds
     */
    public SongRecord(String title, String artist, int songMinutes, int songSeconds) {
        this.title = title;
        this.artist = artist;
        setSongMinutes(songMinutes);
        setSongSeconds(songSeconds);
    }
    /**
     * 
     * @return returns the song as a usable string. 
     */
    public String songRecordToString() {
        return String.format("%-15s%-15s%-10s", title, artist, String.format("%d:%02d", songMinutes, songSeconds));
    }

    /**
     * 
     * @return the title of the song to use later.
     */
    public String getSongToString() {
        return title;
    }
}

/**
 * A class of Playlist of SongRecord objects.
 */
class Playlist implements Cloneable {
    public static final int MAX_SONGS = 50;
    public SongRecord[] songList; 
    public int size;

    /**
     * Constructor to create an empty Playlist.
     */
    public Playlist() {
        songList = new SongRecord[MAX_SONGS];
        size = 0;
    }

    /**
     * @return a deep clone of this Playlist
     * @throws CloneNotSupportedException if cloning is not supported
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            Playlist newSongList = (Playlist)(super.clone());
            newSongList.songList = songList.clone();
            for (int i = 0; i < size; i++) {
                newSongList.songList[i] = new SongRecord(songList[i].getTitle(), songList[i].getArtist(), songList[i].getSongMinutes(), songList[i].getSongSeconds());
            }
            return newSongList;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    /**
     * Checks if this Playlist is equal to another object.
     * 
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return !(obj == null || !(obj instanceof Playlist));
    }

    /**
     * 
     * @return the number of songs in the Playlist
     */
    public int size() {
        return size;
    }

    /**
     * Adds songs to given positions.
     * 
     * @param song to add
     * @param position to add the song at
     * @throws FullPlaylistException if the Playlist is full
     * @throws IllegalArgumentException if the position is invalid
     */
    public void addSong(SongRecord song, int position) throws FullPlaylistException {
        if (position < 1 || position > size + 1) {
            throw new IllegalArgumentException("The position is not possible");
        }
        if (size >= MAX_SONGS) {
            throw new FullPlaylistException("The playlist is full");
        }
        for (int i = size; i >= position; i--) {
            songList[i] = songList[i - 1];
        }
        songList[position - 1] = song;
        size++;
    }

    /**
     * Removes songs from the Playlist at given positions.
     * 
     * @param position the position to remove the song from
     * @throws IllegalArgumentException if the position is invalid
     */
    public void removeSong(int position) {
        if (position < 1 || position > size) {
            throw new IllegalArgumentException("The position is not possible");
        }
        for (int i = position - 1; i < size - 1; i++) {
            songList[i] = songList[i + 1];
        }
        songList[size - 1] = null;
        size--;
    }

    /**
     * Gets songs from the Playlist at given positions.
     * 
     * @param position to get the song from
     * @return the song at the position
     * @throws IllegalArgumentException if the position is invalid
     */
    public SongRecord getSong(int position) {
        if (position < 1 || position > size) {
            throw new IllegalArgumentException("The position is not possible");
        }
        return songList[position - 1];
    }

    /**
     * Prints all songs in the Playlist.
     */
    public void printAllSongs() {
        System.out.println(String.format("%-10s%-15s%-15s%-10s", "Song#", "Title", "Artist", "Length"));
        for (int i = 0; i < size; i++) {
            SongRecord song = songList[i];
            System.out.println(String.format("%-10d%-15s", i + 1, song.songRecordToString()));
        }
    }

    /**
     * returns all songs by a specified artist.
     * 
     * @param originalList the original Playlist to search
     * @param artist to search for
     * @return a Playlist of songs by the artist
     * @throws FullPlaylistException if the new Playlist is full
     */
    public static Playlist getSongsByArtist(Playlist originalList, String artist) throws FullPlaylistException {
        if (originalList == null || artist == null) {
            return null;
        }

        Playlist artistPlaylist = new Playlist();
        for (int i = 0; i < originalList.size; i++) {
            SongRecord song = originalList.songList[i];
            if (song.getArtist().equals(artist)) {
                artistPlaylist.addSong(song, artistPlaylist.size + 1);
            }
        }
        return artistPlaylist;
    }
}

/**
 * Exception thrown if the Playlist is full.
 */
class FullPlaylistException extends Exception {
    public FullPlaylistException(String message) {
        super(message);
    }
}

/**
 * Provides operations for managing a Playlist.
 */
public class PlaylistOperation {
    /**
     * Main method to use the Playlist.
     * 
     * @throws FullPlaylistException if an error occurs during input/output
     */
    public static void main(String[] args) throws FullPlaylistException {
        Scanner input = new Scanner(System.in);
        Playlist newPlaylist = new Playlist();
        boolean quit = false; 
        while (!quit) {
            System.out.println("Menu:");
            System.out.println("A) - Add Song");
            System.out.println("B) - Print Songs by Artist");
            System.out.println("G) - Get Song");
            System.out.println("R) - Remove Song");
            System.out.println("P) - Print All Songs");
            System.out.println("S) - Size");
            System.out.println("Q - Quit");
            System.out.println("Select a menu option:");
            String option = input.nextLine().toUpperCase();
            switch (option) {
                case "A" -> {
                    System.out.println("Enter song title: ");
                    String title = input.nextLine();
                    System.out.println("Enter song artist: ");
                    String artist = input.nextLine();
                    System.out.println("Enter song length (minutes): ");
                    int minutes = input.nextInt();
                    input.nextLine(); 
                    System.out.println("Enter song length (seconds): ");
                    int seconds = input.nextInt();
                    input.nextLine(); 
                    System.out.println("Enter position to add song: ");
                    int position = input.nextInt();
                    input.nextLine(); 
                    try {
                        SongRecord newSong = new SongRecord(title, artist, minutes, seconds);
                        newPlaylist.addSong(newSong, position);
                        System.out.println("The song was added successfully.");
                    } catch (Exception e) {
                    	System.out.println(e.getMessage());
                    }
                }
                case "B" -> {
                    System.out.println("Enter the artist: ");
                    String artist = input.nextLine();
                    Playlist artistPlaylist = Playlist.getSongsByArtist(newPlaylist, artist);
                   try { 
                        artistPlaylist.printAllSongs();
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "G" -> {
                    System.out.println("Enter the position: ");
                    int position = input.nextInt();
                    input.nextLine();
                    try {
                        SongRecord choice = newPlaylist.getSong(position);
                        System.out.println("The song at position " + position + " is " + choice.getSongToString());
                    } catch (Exception e) {
                    	System.out.println(e.getMessage());
                    }
                }
                case "R" -> {
                    System.out.println("Enter the position: ");
                    int position = input.nextInt();
                    input.nextLine();
                    try {
                        newPlaylist.removeSong(position);
                        System.out.println("Song removed at position " + position + ".");
                    } catch (Exception e) {
                    	System.out.println(e.getMessage());
                    }
                }
                case "S" -> {
                    System.out.println("The size is " + newPlaylist.size());
                }
                case "P" -> {
                    newPlaylist.printAllSongs();
                }
                case "Q" -> {
                    System.out.println("Thank you for listening");
                    quit = true;
                }
                default ->{
                	System.out.println("Option not available, try again.");
                }
            }
        }
    }
}
