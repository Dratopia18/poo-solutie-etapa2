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
     * Opreste player-ul.
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
     * Creeaza o noua sursa pentru player.
     * @param type tipul sursei
     * @param entry melodia/playlistul/podcastul
     * @param bookmarks lista de bookmark-uri
     * @return 5 cazuri:
     * 1. Daca tipul sursei este o melodie, se creeaza o sursa de tip LIBRARY
     * 2. Daca tipul sursei este un playlist sau un album, se creeaza o sursa de tip PLAYLIST
     * 3. Daca tipul sursei este un podcast si exista un bookmark pentru acel podcast,
     * se creeaza o sursa de tip PODCAST cu bookmark-ul (createPodcastSource)
     * 4. Daca tipul sursei este un podcast si nu exista un bookmark pentru acel podcast,
     * se creeaza o sursa de tip PODCAST fara bookmark (createPodcastSource)
     * 5. Daca tipul sursei nu este unul dintre cele 4, se returneaza null
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
     * Seteaza sursa pentru player.
     * @param entry melodia/playlistul/podcastul
     * @param type1 tipul sursei
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
     * Da pause sau resume la player.
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * Amesteca melodiile din playlist sau album.
     * @param seed seed-ul pentru amestecare
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
     * Seteaza modul de repetare.
     * @return modul de repetare
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
     * Simuleaza player-ul pentru un anumit timp.
     * @param time timpul
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
     * Sare la urmatoarea sursa.
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
     * Sare la sursa anterioara.
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    /**
     * Sare la un anumit moment din melodie.
     * @param duration durata cu care se sare
     */
    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    /**
     * Sare cu 90 de secunde inainte.
     */
    public void skipNext() {
        final int forwardNumber = -90;
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(forwardNumber);
        }
    }

    /**
     * Sare cu 90 de secunde inapoi.
     */
    public void skipPrev() {
        final int backwardNumber = 90;
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(backwardNumber);
        }
    }

    /**
     * preluam audiofile-ul curent
     * @return audiofile-ul curent sau null daca nu exista
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    /**
     * Luam statutyl lui paused
     * @return statutul lui paused
     */
    public boolean getPaused() {
        return paused;
    }

    /**
     * Luam statutul lui shuffle
     * @return statutul lui shuffle
     */
    public boolean getShuffle() {
        return shuffle;
    }

    /**
     * Luam statisticile player-ului
     * @return statisticile player-ului:
     * Numele fisierului, durata, modul de repetare, modul de shuffle, modul de paused
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
     * Luam sursa curenta
     * @return sursa curenta sau null daca nu exista
     */
    public LibraryEntry getCurrentSource() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }
}
