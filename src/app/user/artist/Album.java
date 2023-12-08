package app.user.artist;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.List;

public class Album extends AudioCollection {
    @Getter
    private String releaseYear;
    @Getter
    private String description;
    @Getter
    private List<Song> songs;

    public Album(String name, String owner, String releaseYear, String description, List<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
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

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        } else {
            return null;
        }
    }
    public int getTotalLikes() {
        return songs.stream().mapToInt(Song::getLikes).sum();
    }
}
