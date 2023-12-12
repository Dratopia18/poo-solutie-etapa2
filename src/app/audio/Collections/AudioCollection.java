package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioCollection extends LibraryEntry {
    private final String owner;

    public AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * Returneaza numarul de melodii din colectie.
     * @return numarul de melodii din colectie
     */
    public abstract int getNumberOfTracks();

    /**
     * Returneaza o melodie din colectie dupa index.
     * @param index indexul melodiei
     * @return melodia de la indexul dat sau null daca indexul e invalid
     */
    public abstract AudioFile getTrackByIndex(int index);

    /**
     * Verifica daca numele proprietarului este egal cu userul dat.
     * @param user userul cu care se compara
     * @return true daca numele proprietarului este egal cu userul dat, false in caz contrar
     */
    @Override
    public boolean matchesOwner(final String user) {
        return this.getOwner().equals(user);
    }
}
