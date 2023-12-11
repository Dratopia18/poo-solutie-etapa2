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
            PodcastBookmark currentBookmark = new PodcastBookmark(source.getAudioCollection().getName(), source.getIndex(), source.getDuration());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }

    public static PlayerSource createSource(final String type, final LibraryEntry entry,
                                            final List<PodcastBookmark> bookmarks) {
        return switch (type) {
            case "song" -> new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
            case "playlist", "album" -> new PlayerSource(Enums.PlayerSourceType.PLAYLIST, (AudioCollection) entry);
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

    public void pause() {
        paused = !paused;
    }

    public void shuffle (final Integer seed) {
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

    public void simulatePlayer(int time) {
        if (!paused) {
            while (time >= source.getDuration()) {
                time -= source.getDuration();
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-time);
            }
        }
    }

    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }

    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    public void skipNext() {
        final int forwardNumber = -90;
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(forwardNumber);
        }
    }

    public void skipPrev() {
        final int backwardNumber = 90;
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(backwardNumber);
        }
    }

    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    public boolean getPaused() {
        return paused;
    }

    public boolean getShuffle() {
        return shuffle;
    }

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

    public LibraryEntry getCurrentSource() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }
}
