package app.user.artist;

import app.audio.Files.Song;
import app.user.User;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Artist extends User {
    private Set<Album> albums;
    public Artist(String username, int age, String city) {
        super(username, age, city);
        this.albums = new HashSet<>();
    }

    public String addAlbum(String name, String releaseYear, String description, List<Song> songs) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return getUsername() + " has another album with the same name.";
            }
        }

        Set<String> songNames = new HashSet<>();
        for (Song song : songs) {
            if (!songNames.add(song.getName())) {
                return getUsername() + " has the same song at least twice in this album.";
            }
        }

        Album newAlbum = new Album(name, releaseYear, description, songs);
        albums.add(newAlbum);

        return getUsername() + " has added new album successfully.";
    }
//    public String addEvent(String eventName, String eventDescription, String eventDate) {
//
//    }
    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
