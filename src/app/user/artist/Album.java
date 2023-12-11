package app.user.artist;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Album extends AudioCollection {
    private String releaseYear;
    private String description;
    private List<Song> songs;

    public Album(final String name, final String owner, final String releaseYear,
                 final String description, final List<Song> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs = songs;
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public int getTotalLikes() {
        return songs.stream().mapToInt(Song::getLikes).sum();
    }
}
