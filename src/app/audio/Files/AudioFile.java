package app.audio.Files;

import app.audio.LibraryEntry;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class AudioFile extends LibraryEntry {
    private final Integer duration;

    public AudioFile(final String name, final Integer duration) {
        super(name);
        this.duration = duration;
    }

    /**
     * Se supra-scrie metoda equals pentru a compara doua obiecte de tip AudioFile.
     * @param obj obiectul cu care se compara
     * @return true daca obiectele sunt egale, false in caz contrar
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AudioFile audioFile = (AudioFile) obj;
        return Objects.equals(this.getName(), audioFile.getName());
    }

    /**
     * Se supra-scrie metoda hashCode pentru a calcula hash-ul unui obiect de tip AudioFile.
     * @return hash-ul obiectului
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }

}
