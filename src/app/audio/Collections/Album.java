package app.audio.Collections;

import app.audio.Files.Song;

import java.util.List;

public class Album {
    private String albumName;
    private String releaseYear;
    private String description;
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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
