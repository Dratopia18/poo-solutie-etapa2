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
     * Ia numarul de like-uri al playlist-ului,
     * adica suma like-urilor melodiilor din playlist
     * @return numarul de like-uri al playlist-ului
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
     * Scade numarul de followeri al playlist-ului
     */
    public void decreaseFollowers() {
        followers--;
    }

    /**
     * Ia numarul de melodii din playlist
     * @return numarul de melodii din playlist
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * Ia o melodie din playlist dupa index
     * @param index ia indexul melodiei
     * @return melodia de la indexul dat sau null daca indexul e invalid
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    /**
     * Verificam daca playlistul este vizibil pentru userul dat.
     * @param user userul cu care se compara
     * @return true daca playlistul este vizibil pentru userul dat, false in caz contrar
     */
    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                || (this.getVisibility() == Enums.Visibility.PRIVATE
                && this.getOwner().equals(user));
    }

    /**
     * Verificam daca followerii playlistului sunt egali cu followerii dati.
     * @param followers1 followerii cu care se compara
     * @return true daca followerii playlistului sunt egali cu followerii dati,
     * false in caz contrar
     */
    @Override
    public boolean matchesFollowers(final String followers1) {
        return filterByFollowersCount(this.getFollowers(), followers1);
    }

    /**
     * Filtrare dupa numarul de followeri.
     * @param count numarul de followeri
     * @param query query-ul dupa care se filtreaza
     * @return true daca numarul de followeri respecta query-ul, false in caz contrar
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
