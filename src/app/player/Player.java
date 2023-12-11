package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Enums.RepeatMode repeatMode;
    private boolean shuffle;
    private boolean paused;
    private PlayerSource source;
    @Getter
    private String type;

    private final ArrayList<PodcastBookmark> bookmarks = new ArrayList<>();


    public Player() {
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.paused = true;
    }

    /**
     *
     */
    public void stop() {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        repeatMode = Enums.RepeatMode.NO_REPEAT;
        paused = true;
        source = null;
        shuffle = false;
    }

    private void bookmarkPodcast() {
        if (source != null && source.getAudioFile() != null) {
            PodcastBookmark currentBookmark =
                    new PodcastBookmark(source.getAudioCollection().getName(),
                            source.getIndex(), source.getDuration());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }

    /**
     *
     * @param type
     * @param entry
     * @param bookmarks
     * @return
     */
    public static PlayerSource createSource(final String type, final LibraryEntry entry,
                                            final List<PodcastBookmark> bookmarks) {
        return switch (type) {
            case "song" -> new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
            case "playlist", "album" -> new PlayerSource(Enums.PlayerSourceType.PLAYLIST,
                    (AudioCollection) entry);
            case "podcast" -> createPodcastSource((AudioCollection) entry, bookmarks);
            default -> null;
        };
    }

    private static PlayerSource createPodcastSource(final AudioCollection collection,
                                                    final List<PodcastBookmark> bookmarks) {
        for (PodcastBookmark bookmark : bookmarks) {
            if (bookmark.getName().equals(collection.getName())) {
                return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection, bookmark);
            }
        }
        return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection);
    }

    /**
     *
     * @param entry
     * @param type1
     */
    public void setSource(final LibraryEntry entry, final String type1) {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        this.type = type1;
        this.source = createSource(type1, entry, bookmarks);
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }

    /**
     *
     */
    public void pause() {
        paused = !paused;
    }

    /**
     *
     * @param seed
     */
    public void shuffle(final Integer seed) {
        if (seed != null) {
            source.generateShuffleOrder(seed);
        }

        if (source.getType() == Enums.PlayerSourceType.PLAYLIST
                || source.getType() == Enums.PlayerSourceType.ALBUM) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
    }

    /**
     *
     * @return
     */
    public Enums.RepeatMode repeat() {
        if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                repeatMode = Enums.RepeatMode.REPEAT_ONCE;
            } else {
                repeatMode = Enums.RepeatMode.REPEAT_ALL;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
                repeatMode = Enums.RepeatMode.REPEAT_INFINITE;
            } else {
                if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                    repeatMode = Enums.RepeatMode.REPEAT_CURRENT_SONG;
                } else {
                    repeatMode = Enums.RepeatMode.NO_REPEAT;
                }
            }
        }

        return repeatMode;
    }

    /**
     *
     * @param time
     */
    public void simulatePlayer(final int time) {
        int elapsedTime = time;
        if (!paused) {
            while (elapsedTime >= source.getDuration()) {
                elapsedTime -= source.getDuration();
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-elapsedTime);
            }
        }
    }

    /**
     *
     */
    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }

    /**
     *
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    /**
     *
     * @param duration
     */
    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    /**
     *
     */
    public void skipNext() {
        final int forwardNumber = -90;
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(forwardNumber);
        }
    }

    /**
     *
     */
    public void skipPrev() {
        final int backwardNumber = 90;
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(backwardNumber);
        }
    }

    /**
     *
     * @return
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    /**
     *
     * @return
     */
    public boolean getPaused() {
        return paused;
    }

    /**
     *
     * @return
     */
    public boolean getShuffle() {
        return shuffle;
    }

    /**
     *
     * @return
     */
    public PlayerStats getStats() {
        String filename = "";
        int duration = 0;
        if (source != null && source.getAudioFile() != null) {
            filename = source.getAudioFile().getName();
            duration = source.getDuration();
        } else {
            stop();
        }

        return new PlayerStats(filename, duration, repeatMode, shuffle, paused);
    }

    /**
     *
     * @return
     */
    public LibraryEntry getCurrentSource() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }
}
