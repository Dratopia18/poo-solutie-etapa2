package app.audio.Files;

import app.audio.LibraryEntry;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class AudioFile extends LibraryEntry {
    private final Integer duration;

    public AudioFile(String name, Integer duration) {
        super(name);
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AudioFile audioFile = (AudioFile) obj;
        return Objects.equals(this.getName(), audioFile.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }

}
