package app.user.artist;

import app.audio.Files.Song;
import lombok.Getter;

import java.util.List;

public class Album {
    private String albumName;
    @Getter
    private String releaseYear;
    @Getter
    private String description;
    @Getter
    private List<Song> songs;

    public Album(String albumName, String releaseYear, String description, List<Song> songs) {
        this.albumName = albumName;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }

    public String getName() {
        return this.albumName;
    }

    public void setName(String albumName) {
        this.albumName = albumName;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
