package app.searchBar;


import app.Admin;
import app.audio.Collections.Podcast;
import app.user.artist.Album;
import app.audio.LibraryEntry;
import app.user.artist.Artist;
import app.user.host.Host;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static app.searchBar.FilterUtils.*;
import static app.searchBar.FilterUtils.filterByFollowers;

public class SearchBar {
    private List<LibraryEntry> results;
    private final String user;
    private static final Integer MAX_RESULTS = 5;
    @Getter
    private String lastSearchType;

    @Getter
    private LibraryEntry lastSelected;

    public SearchBar(String user) {
        this.results = new ArrayList<>();
        this.user = user;
    }

    public void clearSelection() {
        lastSelected = null;
        lastSearchType = null;
    }
    public List<LibraryEntry> search(Filters filters, String type) {
        List<LibraryEntry> entries;

        switch (type) {
            case "song":
                entries = new ArrayList<>(Admin.getSongs());
                for (Artist artist : Admin.getArtists()) {
                    for (Album album : artist.getAlbums()) {
                        entries.addAll(album.getSongs());
                    }
                }
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getAlbum() != null) {
                    entries = filterByAlbum(entries, filters.getAlbum());
                }

                if (filters.getTags() != null) {
                    entries = filterByTags(entries, filters.getTags());
                }

                if (filters.getLyrics() != null) {
                    entries = filterByLyrics(entries, filters.getLyrics());
                }

                if (filters.getGenre() != null) {
                    entries = filterByGenre(entries, filters.getGenre());
                }

                if (filters.getReleaseYear() != null) {
                    entries = filterByReleaseYear(entries, filters.getReleaseYear());
                }

                if (filters.getArtist() != null) {
                    entries = filterByArtist(entries, filters.getArtist());
                }

                break;
            case "playlist":
                entries = new ArrayList<>(Admin.getPlaylists());

                entries = filterByPlaylistVisibility(entries, user);

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                if (filters.getFollowers() != null) {
                    entries = filterByFollowers(entries, filters.getFollowers());
                }

                break;
            case "podcast":
                entries = new ArrayList<>(Admin.getPodcasts());
                for (Host host : Admin.getHosts()) {
                    entries.addAll(host.getPodcasts());
                }
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                break;
            case "album":
                entries = new ArrayList<>();
                for (Artist artist : Admin.getArtists()) {
                    for (Album album : artist.getAlbums()) {
                        if ((filters.getOwner() == null || album.getOwner().equalsIgnoreCase(filters.getOwner())) &&
                                (filters.getName() == null || album.getName().toLowerCase().contains(filters.getName().toLowerCase()))) {
                            entries.add(album);
                        }
                    }
                }
                break;
            case "artist":
                entries = new ArrayList<>();
                for(Artist artist : Admin.getArtists()) {
                    LibraryEntry artistEntry = new LibraryEntry(artist.getUsername()) {
                        @Override
                        public boolean matchesName(String name) {
                            return super.matchesName(name) || matchesArtist(name);
                        }
                        @Override
                        public boolean matchesArtist(String artistName) {
                            return artist.getUsername().toLowerCase().startsWith(artistName.toLowerCase());
                        }
                    };
                    entries.add(artistEntry);
                    if (filters.getName() != null) {
                        entries = filterByArtist(entries, filters.getName());
                    }
                }
                break;
            case "host":
                entries = new ArrayList<>();
                for (Host host : Admin.getHosts()) {
                    LibraryEntry hostEntry = new LibraryEntry(host.getUsername()) {
                        @Override
                        public boolean matchesName(String name) {
                            return super.matchesName(name) || matchesArtist(name);
                        }
                        @Override
                        public boolean matchesArtist(String hostName) {
                            return host.getUsername().toLowerCase().startsWith(hostName.toLowerCase());
                        }
                    };
                    entries.add(hostEntry);
                    if (filters.getName() != null) {
                        entries = filterByArtist(entries, filters.getName());
                    }
                }
                break;
            default:
                entries = new ArrayList<>();
        }

        while (entries.size() > MAX_RESULTS) {
            entries.remove(entries.size() - 1);
        }

        this.results = entries;
        this.lastSearchType = type;
        return this.results;
    }

    public LibraryEntry select(Integer itemNumber) {
        if (this.results.size() < itemNumber) {
            results.clear();

            return null;
        } else {
            lastSelected =  this.results.get(itemNumber - 1);
            results.clear();

            return lastSelected;
        }
    }
}
