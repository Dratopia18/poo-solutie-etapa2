package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Playlist extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private Integer followers;
    private final int timestamp;

    public Playlist(final String name, final String owner) {
        this(name, owner, 0);
    }

    public Playlist(final String name, final String owner, final int timestamp) {
        super(name, owner);
        this.songs = new ArrayList<>();
        this.visibility = Enums.Visibility.PUBLIC;
        this.followers = 0;
        this.timestamp = timestamp;
    }

    /**
     * Verifica daca playlist-ul contine o anumita melodie
     * @param song ia melodia data
     * @return true daca playlist-ul contine melodia, false altfel
     */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    /**
     * Adauga o melodie in playlist
     * @param song ia melodia data
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Sterge o melodie din playlist
     * @param song ia melodia data
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

    /**
     * Schimba vizibilitatea playlist-ului
     */
    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }

    /**
     * Ia numarul de like-uri al playlist-ului
     * @return
     */
    public Integer getTotalLikesCount() {
        int totalLikes = 0;
        for (Song song : songs) {
            totalLikes += song.getLikesCount();
        }
        return totalLikes;
    }

    /**
     * Creste numarul de followeri al playlist-ului
     */
    public void increaseFollowers() {
        followers++;
    }

    /**
     *
     */
    public void decreaseFollowers() {
        followers--;
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
        return songs.get(index);
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                || (this.getVisibility() == Enums.Visibility.PRIVATE
                && this.getOwner().equals(user));
    }

    /**
     *
     * @param followers1
     * @return
     */
    @Override
    public boolean matchesFollowers(final String followers1) {
        return filterByFollowersCount(this.getFollowers(), followers1);
    }

    /**
     *
     * @param count
     * @param query
     * @return
     */
    private static boolean filterByFollowersCount(final int count, final String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }
}
