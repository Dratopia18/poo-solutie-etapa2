package app;

import app.audio.LibraryEntry;
import app.user.artist.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.artist.Artist;
import app.user.host.Host;
import app.user.User;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static final List<Artist> ARTISTS = new ArrayList<>();
    private static final List<Host> HOSTS = new ArrayList<>();
    private static int timestamp = 0;
    private static final Set<String> USERNAMES_IN_CURRENT_TEST = new HashSet<>();
    private static final int LIMIT = 5;
    private Admin() {
    }

    /**
     * Seteaza utilizatorii
     * @param userInputList lista de utilizatori
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Seteaza melodiile
     * @param songInputList lista de melodii
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * Seteaza podcasturile
     * @param podcastInputList lista de podcasturi
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                        episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Face rost de toate playlisturile facute
     * de utilizatori
     * @return playlisturile facute de utilizatori
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Cauta un utilizator dupa username
     * @param username numele de utilizator
     * @return utilizatorul cu username-ul dat sau null daca nu exista
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Cauta un artist dupa username
     * @param username numele de utilizator
     * @return artistul cu username-ul dat sau null daca nu exista
     */
    public static Artist getArtist(final String username) {
        for (Artist artist : ARTISTS) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * Caute un host dupa username
     * @param username numele de utilizator
     * @return hostul cu username-ul dat sau null daca nu exista
     */
    public static Host getHost(final String username) {
        for (Host host : HOSTS) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }

    /**
     * Ia toate albumele facute de artisti
     * @return albumele facute de artisti
     */
    public static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        for (Artist artist : ARTISTS) {
            albums.addAll(artist.getAlbums());
        }
        return albums;
    }

    /**
     * Cauta un utilizator dupa username
     * @param username numele de utilizator
     * @return userul cu username-ul dat, artistul cu username-ul dat,
     * hostul cu username-ul dat sau null daca nu exista
     */
    public static User findUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        for (Artist artist : ARTISTS) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }

        for (Host host : HOSTS) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }

        return null;
    }

    /**
     * Se actualizeaza timestampul
     * @param newTimestamp noul timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Ia cele mai apreciate 5 melodii dupa numarul de like-uri,
     * si apoi dupa ordinea in care au fost adaugate
     * @return cele mai apreciate 5 melodii
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        for (Artist artist : getArtists()) {
            for (Album album : artist.getAlbums()) {
                sortedSongs.addAll(album.getSongs());
            }
        }

        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed()
                .thenComparing(sortedSongs::indexOf));

        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Ia cele mai urmarite 5 playlisturi dupa numarul de follow-uri,
     * si apoi dupa ordinea in care au fost adaugate
     * @return cele mai urmarite 5 playlisturi
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Ia cele mai apreciate 5 albume dupa numarul de like-uri,
     * si apoi dupa numele lor
     * @return cele mai apreciate 5 albume
     */
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::getTotalLikes).reversed()
                .thenComparing(Album::getName));
        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= LIMIT) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

    /**
     * Ia cei mai apreciati 5 artisti dupa numarul de like-uri primite
     * din melodiile lor.
     * @return cei mai apreciati 5 artisti
     */
    public static List<String> getTop5Artists() {
        List<Artist> sortedArtists = new ArrayList<>(getArtists());
        sortedArtists.sort(Comparator.comparingInt(Artist::getTotalLikes).reversed());
        List<String> topArtists = new ArrayList<>();
        int count = 0;
        for (Artist artist : sortedArtists) {
            if (count >= LIMIT) {
                break;
            }
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }

    /**
     * Face rost de toti utilizatorii online
     * @return toti utilizatorii online
     */
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user: users) {
            if (user.getOnlineStatus()) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }

    /**
     * face rost de toti utilizatorii, fie ei artisti, hosti
     * sau utilizatori normali
     * @return toti utilizatorii
     */
    public static List<String> getAllUsers() {
        List<String> allUsers = new ArrayList<>();
        for (User user : users) {
            allUsers.add(user.getUsername());
        }
        for (Artist artist : ARTISTS) {
            allUsers.add(artist.getUsername());
        }
        for (Host host : HOSTS) {
            allUsers.add(host.getUsername());
        }
        return allUsers;
    }

    public static List<User> getNormalUsers() {
        return new ArrayList<>(users);
    }
    public static List<Artist> getArtists() {
        return new ArrayList<>(ARTISTS);
    }
    public static List<Host> getHosts() {
        return new ArrayList<>(HOSTS);
    }

    /**
     * Adauga un utilizator, artist sau host
     * @param commandInput comanda de adaugare
     * @return mesajul de succes sau de eroare
     */
    public static String addUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        User existingUser = getUser(username);
        if (USERNAMES_IN_CURRENT_TEST.contains(username)) {
            return "The username " + username + " is already taken.";
        }
        if (existingUser != null) {
            return "The username " + username + " is already taken.";
        }
        String type = commandInput.getType();
        int age = commandInput.getAge();
        String city = commandInput.getCity();
        switch (type) {
            case "user" -> users.add(new User(username, age, city));
            case "artist" -> ARTISTS.add(new Artist(username, age, city));
            case "host" -> HOSTS.add(new Host(username, age, city));
            default -> {
                return "Type doesn't exist";
            }
        }
        USERNAMES_IN_CURRENT_TEST.add(username);
        return "The username " + username + " has been added successfully.";
    }

    /**
     * Sterge un utilizator, artist sau host
     * @param commandInput comanda de stergere
     * @return mesajul de succes sau de eroare
     */
    public static String deleteUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        User userToDelete = findUser(username);
        if (userToDelete == null) {
            return "The username " + username + " doesn't exist.";
        }
        if (isAnyUserInteractingWithPages(userToDelete)) {
            return username + " can't be deleted.";
        }
        List<LibraryEntry> associatedEntries = getAssociatedEntries(userToDelete);
        if (isAnyUserInteractingWith(associatedEntries)) {
            return username + " can't be deleted.";
        }

        removeUser(userToDelete);
        return username + " was successfully deleted.";
    }

    /**
     * Ia toate lucrurile facute de un utilizator,
     * artist sau host
     * @param user utilizatorul, artistul sau hostul
     * @return toate lucrurile facute de utilizatorul
     * respectiv (utilizator, artist sau host)
     */
    public static List<LibraryEntry> getAssociatedEntries(final User user) {
        List<LibraryEntry> associatedEntries = new ArrayList<>();
        if (user instanceof Artist artist) {
            for (Album album : artist.getAlbums()) {
                associatedEntries.add(album);
                associatedEntries.addAll(album.getSongs());
            }
        } else if (user instanceof Host host) {
            for (Podcast podcast : host.getPodcasts()) {
                associatedEntries.add(podcast);
                associatedEntries.addAll(podcast.getEpisodes());
            }
        } else {
            for (Playlist playlist : user.getPlaylists()) {
                associatedEntries.add(playlist);
                associatedEntries.addAll(playlist.getSongs());
            }
        }
        return associatedEntries;
    }

    /**
     * Verifica daca exista vreun utilizator care
     * foloseste unul dintre lucrurile facute de un
     * utilizator, artist sau host
     * @param entries lucrurile facute de utilizatorul
     * respectiv (utilizator, artist sau host)
     * @return true daca exista vreun utilizator care
     * foloseste unul dintre lucrurile facute de un
     * utilizator, artist sau host, false in caz contrar
     */
    public static boolean isAnyUserInteractingWith(final List<LibraryEntry> entries) {
        for (String username : getAllUsers()) {
            User user = findUser(username);
            if (user != null && user.isInteractingWith(entries)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica daca exista vreun utilizator care
     * foloseste pagina unui artist sau host
     * @param user utilizatorul
     * @return true daca exista vreun utilizator care
     * foloseste pagina unui artist sau host, false in caz contrar
     */
    public static boolean isAnyUserInteractingWithPages(final User user) {
        for (String username : getAllUsers()) {
            User interactingUser = findUser(username);
            if (interactingUser != null
                    && interactingUser.isOnArtistOrHostPage(user.getUsername())) {
                    return true;
            }
        }
        return false;
    }

    /**
     * Sterge un utilizator, artist sau host si tot
     * ce a facut acesta (playlisturi, albume, podcasturi)
     * @param user utilizatorul, artistul sau hostul
     */
    public static void removeUser(final User user) {
        if (user instanceof Artist artist) {
            artist.clearAlbums();
            for (Album album : artist.getAlbums()) {
                removeAlbum(album);
            }
            ARTISTS.remove(artist);

            removeArtistSongsFromLikedSongs(artist);
        } else if (user instanceof Host host) {
            host.clearPodcasts();
            for (Podcast podcast : host.getPodcasts()) {
                removePodcast(podcast);
            }
            HOSTS.remove(host);
        } else {
            for (Playlist playlist : user.getFollowedPlaylists()) {
                playlist.decreaseFollowers();
            }
            removeUserPlaylists(user);
            users.remove(user);
        }
        USERNAMES_IN_CURRENT_TEST.remove(user.getUsername());
    }

    /**
     * Sterge un podcast
     * @param podcast podcastul de sters
     */
    private static void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * Sterge toate melodiile unui artist care va fi sters
     * din lista de melodii like-uite de utilizator
     * @param artist artistul care va fi sters
     */
    private static void removeArtistSongsFromLikedSongs(final Artist artist) {
        for (User user : users) {
            user.getLikedSongs()
                    .removeIf(song -> song.getArtist().equals(artist.getUsername()));
        }
    }

    /**
     * Sterge toate playlisturile unui utilizator care va fi sters
     * @param user utilizatorul care va fi sters
     */
    private static void removeUserPlaylists(final User user) {
        for (User u : users) {
            u.getFollowedPlaylists().
                    removeIf(playlist -> playlist.getOwner().equals(user.getUsername()));
        }
    }

    /**
     * Arata toate albumele facute de un artist
     * plus melodiile din fiecare album
     * @param artistUsername username-ul artistului
     * @return albumele facute de artistul respectiv
     * plus melodiile din fiecare album
     */
    public static List<Map<String, Object>> showAlbums(final String artistUsername) {
        Artist artist = getArtist(artistUsername);
        if (artist == null) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> albumsInfo = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            Map<String, Object> albumInfo = new HashMap<>();
            albumInfo.put("name", album.getName());
            List<String> songNames = album.getSongs().stream()
                    .map(Song::getName)
                    .collect(Collectors.toList());
            albumInfo.put("songs", songNames);
            albumsInfo.add(albumInfo);
        }

        return albumsInfo;
    }

    /**
     * Arata toate podcasturile facute de un host
     * plus episoadele din fiecare podcast
     * @param hostUsername username-ul hostului
     * @return podcasturile facute de hostul respectiv
     * plus episoadele din fiecare podcast
     */
    public static List<Map<String, Object>> showPodcasts(final String hostUsername) {
        Host host = getHost(hostUsername);
        if (host == null) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> podcastsInfo = new ArrayList<>();
        for (Podcast podcast : host.getPodcasts()) {
            Map<String, Object> podcastInfo = new HashMap<>();
            podcastInfo.put("name", podcast.getName());
            List<String> episodeNames = podcast.getEpisodes().stream()
                    .map(Episode::getName).toList();
            podcastInfo.put("episodes", episodeNames);
            podcastsInfo.add(podcastInfo);
        }
        return podcastsInfo;
    }

    /**
     * Verifica daca un album este folosit de vreun utilizator
     * @param album albumul de verificat
     * @return true daca albumul este folosit de vreun utilizator,
     * false in caz contrar
     */
    public static boolean isAlbumInUse(final Album album) {
        for (User user : users) {
            if (user.isUsingAlbum(album) || user.hasAlbumSongInPlaylist(album)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sterge un album si melodiile din el
     * @param album albumul de sters
     */
    public static void removeAlbum(final Album album) {
        songs.removeAll(album.getSongs());
    }

    /**
     * Reseteaza totul pentru urmatorul test
     */
    public static void reset() {
        users.clear();
        songs.clear();
        podcasts.clear();
        ARTISTS.clear();
        HOSTS.clear();
        timestamp = 0;
        USERNAMES_IN_CURRENT_TEST.clear();
        for (Artist artist : ARTISTS) {
            artist.clearAlbums();
        }
        for (Host host : HOSTS) {
            host.clearPodcasts();
        }
    }
}
