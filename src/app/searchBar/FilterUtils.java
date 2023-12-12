package app.searchBar;

import app.audio.LibraryEntry;

import java.util.ArrayList;
import java.util.List;

public final class FilterUtils {
    private FilterUtils() {
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa nume.
     * @param entries lista de LibraryEntry-uri
     * @param name numele dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByName(final List<LibraryEntry> entries,
                                                  final String name) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (entry.matchesName(name)) {
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa album.
     * @param entries lista de LibraryEntry-uri
     * @param album albumul dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByAlbum(final List<LibraryEntry> entries,
                                                   final String album) {
        return filter(entries, entry -> entry.matchesAlbum(album));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa taguri.
     * @param entries lista de LibraryEntry-uri
     * @param tags tagurile dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByTags(final List<LibraryEntry> entries,
                                                  final ArrayList<String> tags) {
        return filter(entries, entry -> entry.matchesTags(tags));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa versuri.
     * @param entries lista de LibraryEntry-uri
     * @param lyrics versurile dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByLyrics(final List<LibraryEntry> entries,
                                                    final String lyrics) {
        return filter(entries, entry -> entry.matchesLyrics(lyrics));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa genul muzical.
     * @param entries lista de LibraryEntry-uri
     * @param genre genul muzical dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByGenre(final List<LibraryEntry> entries,
                                                   final String genre) {
        return filter(entries, entry -> entry.matchesGenre(genre));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa artist.
     * @param entries lista de LibraryEntry-uri
     * @param artist artistul dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByArtist(final List<LibraryEntry> entries,
                                                    final String artist) {
        return filter(entries, entry -> entry.matchesArtist(artist));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa anul de lansare.
     * @param entries lista de LibraryEntry-uri
     * @param releaseYear anul de lansare dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByReleaseYear(final List<LibraryEntry> entries,
                                                         final String releaseYear) {
        return filter(entries, entry -> entry.matchesReleaseYear(releaseYear));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa owner.
     * @param entries lista de LibraryEntry-uri
     * @param user ownerul dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByOwner(final List<LibraryEntry> entries,
                                                   final String user) {
        return filter(entries, entry -> entry.matchesOwner(user));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa vizibilitatea playlistului.
     * @param entries lista de LibraryEntry-uri
     * @param user userul dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByPlaylistVisibility(final List<LibraryEntry> entries,
                                                                final String user) {
        return filter(entries, entry -> entry.isVisibleToUser(user));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa followers.
     * @param entries lista de LibraryEntry-uri
     * @param followers followers dupa care se filtreaza
     * @return lista filtrata
     */
    public static List<LibraryEntry> filterByFollowers(final List<LibraryEntry> entries,
                                                       final String followers) {
        return filter(entries, entry -> entry.matchesFollowers(followers));
    }

    /**
     * Filtreaza o lista de LibraryEntry-uri dupa un criteriu.
     * @param entries lista de LibraryEntry-uri
     * @param criteria criteriul dupa care se filtreaza
     * @return lista filtrata
     */
    private static List<LibraryEntry> filter(final List<LibraryEntry> entries,
                                             final FilterCriteria criteria) {
        List<LibraryEntry> result = new ArrayList<>();
        for (LibraryEntry entry : entries) {
            if (criteria.matches(entry)) {
                result.add(entry);
            }
        }
        return result;
    }

    @FunctionalInterface
    private interface FilterCriteria {
        boolean matches(LibraryEntry entry);
    }
}
