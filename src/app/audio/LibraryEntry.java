package app.audio;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class LibraryEntry {
    private final String name;

    public LibraryEntry(final String name) {
        this.name = name;
    }

    /**
     * Verifica daca numele melodiei incepe cu name1.
     * @param name1 numele cu care se compara
     * @return true daca numele melodiei incepe cu name1, false in caz contrar
     */
    public boolean matchesName(final String name1) {
        return getName().toLowerCase().startsWith(name1.toLowerCase());
    }

    /**
     * Verifica daca albumul melodiei este egal cu albumul dat.
     * @param album albumul cu care se compara
     * @return true daca albumul melodiei este egal cu albumul dat, false in caz contrar
     */
    public boolean matchesAlbum(final String album) {
        return false;
    }

    /**
     * Verifica daca tagurile melodiei este egal cu tagurile date.
     * @param tags tagurile cu care se compara
     * @return true daca tagurile melodiei este egal cu tagurile date, false in caz contrar
     */
    public boolean matchesTags(final ArrayList<String> tags) {
        return false;
    }

    /**
     * Verificam daca versul melodiei este egal cu versul dat.
     * @param lyrics versul cu care se compara
     * @return true daca versul melodiei este egal cu versul dat, false in caz contrar
     */
    public boolean matchesLyrics(final String lyrics) {
        return false;
    }

    /**
     * Verificam daca genul melodiei este egal cu genul dat.
     * @param genre genul cu care se compara
     * @return true daca genul melodiei este egal cu genul dat, false in caz contrar
     */
    public boolean matchesGenre(final String genre) {
        return false;
    }

    /**
     * Verificam daca artistul melodiei este egal cu artistul dat.
     * @param artist artistul cu care se compara
     * @return true daca artistul melodiei este egal cu artistul dat, false in caz contrar
     */
    public boolean matchesArtist(final String artist) {
        return false;
    }

    /**
     * Verificam daca anul de lansare al melodiei este egal cu anul dat.
     * @param releaseYear anul de lansare cu care se compara
     * @return true daca anul de lansare al melodiei este egal cu anul dat, false in caz contrar
     */
    public boolean matchesReleaseYear(final String releaseYear) {
        return false;
    }

    /**
     * Verificam daca propietarul podcastului este egal cu userul dat.
     * @param user userul cu care se compara
     * @return true daca propietarul podcastului este egal cu userul dat, false in caz contrar
     */
    public boolean matchesOwner(final String user) {
        return false;
    }

    /**
     * Verificam daca playlistul este vizibil pentru userul dat.
     * @param user userul cu care se compara
     * @return true daca playlistul este vizibil pentru userul dat, false in caz contrar
     */
    public boolean isVisibleToUser(final String user) {
        return false;
    }

    /**
     * Verificam daca followerii playlistului sunt egali cu followerii dati.
     * @param followers followerii cu care se compara
     * @return true daca followerii playlistului sunt egali cu followerii dati,
     * false in caz contrar
     */
    public boolean matchesFollowers(final String followers) {
        return false;
    }
}
