package app.audio.Files;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Song extends AudioFile {
    private final String album;
    private final ArrayList<String> tags;
    private final String lyrics;
    private final String genre;
    private final Integer releaseYear;
    private final String artist;
    private Integer likes;

    public Song(final String name, final Integer duration, final String album,
                final ArrayList<String> tags, final String lyrics,
                final String genre, final Integer releaseYear, final String artist) {
        super(name, duration);
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
        this.likes = 0;
    }

    @Override
    public boolean matchesAlbum(final String album1) {
        return this.getAlbum().equalsIgnoreCase(album1);
    }

    @Override
    public boolean matchesTags(final ArrayList<String> tags1) {
        List<String> songTags = new ArrayList<>();
        for (String tag : this.getTags()) {
            songTags.add(tag.toLowerCase());
        }

        for (String tag : tags1) {
            if (!songTags.contains(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean matchesLyrics(final String lyrics1) {
        return this.getLyrics().toLowerCase().contains(lyrics1.toLowerCase());
    }

    @Override
    public boolean matchesGenre(final String genre1) {
        return this.getGenre().equalsIgnoreCase(genre1);
    }

    @Override
    public boolean matchesArtist(final String artist1) {
        return this.getArtist().equalsIgnoreCase(artist1);
    }

    @Override
    public boolean matchesReleaseYear(final String releaseYear1) {
        return filterByYear(this.getReleaseYear(), releaseYear1);
    }

    private static boolean filterByYear(final int year, final String query) {
        if (query.startsWith("<")) {
            return year < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return year > Integer.parseInt(query.substring(1));
        } else {
            return year == Integer.parseInt(query);
        }
    }

    public Integer getLikesCount() {
        return likes;
    }

    /**
     * adauga un like de la o melodie
     */
    public void like() {
        likes++;
    }

    /**
     * sterge un like de la o melodie
     */
    public void dislike() {
        likes--;
    }
}
