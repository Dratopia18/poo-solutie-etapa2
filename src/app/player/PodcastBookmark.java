package app.player;

import lombok.Getter;

@Getter
public class PodcastBookmark {
    private final String name;
    private final int id;
    private final int timestamp;

    public PodcastBookmark(final String name, final int id, final int timestamp) {
        this.name = name;
        this.id = id;
        this.timestamp = timestamp;
    }

    /**
     * Se returneaza un string cu informatiile despre bookmark.
     * @return string cu informatiile despre bookmark
     */
    @Override
    public String toString() {
        return "PodcastBookmark{"
                + "name='" + name + '\''
                + ", id=" + id
                + ", timestamp=" + timestamp
                + '}';
    }
}
