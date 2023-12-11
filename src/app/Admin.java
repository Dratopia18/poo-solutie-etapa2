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
import fileio.input.*;

import java.util.*;
import java.util.stream.Collectors;

public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static final List<Artist> Artists = new ArrayList<>();
    private static final List<Host> Hosts = new ArrayList<>();
    private static int timestamp = 0;
    private static final Set<String> usernamesInCurrentTest = new HashSet<>();
    private static final int maxNumber = 5;

    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

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

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static Artist getArtist(final String username) {
        for (Artist artist : Artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }
    public static Host getHost(final String username) {
        for (Host host : Hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }
    public static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        for (Artist artist : Artists) {
            albums.addAll(artist.getAlbums());
        }
        return albums;
    }
    public static User findUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        for (Artist artist : Artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }

        for (Host host : Hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }

        return null;
    }

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
            if (count >= maxNumber) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= maxNumber) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::getTotalLikes).reversed()
                .thenComparing(Album::getName));
        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= maxNumber) break;
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }
    public static List<String> getTop5Artists() {
        List<Artist> sortedArtists = new ArrayList<>(getArtists());
        sortedArtists.sort(Comparator.comparingInt(Artist::getTotalLikes).reversed());
        List<String> topArtists = new ArrayList<>();
        int count = 0;
        for (Artist artist : sortedArtists) {
            if (count >= maxNumber) break;
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user: users) {
            if (user.getOnlineStatus()) {
                onlineUsers.add(user.getUsername());
            }
        }
        return onlineUsers;
    }
    public static List<String> getAllUsers() {
        List<String> allUsers = new ArrayList<>();
        for (User user : users) {
            allUsers.add(user.getUsername());
        }
        for (Artist artist : Artists) {
            allUsers.add(artist.getUsername());
        }
        for (Host host : Hosts) {
            allUsers.add(host.getUsername());
        }
        return allUsers;
    }

    public static List<User> getNormalUsers() {
        return new ArrayList<>(users);
    }
    public static List<Artist> getArtists() {
        return new ArrayList<>(Artists);
    }
    public static List<Host> getHosts() {
        return new ArrayList<>(Hosts);
    }
    public static String addUser(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        User existingUser = getUser(username);
        if (usernamesInCurrentTest.contains(username)) {
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
            case "artist" -> Artists.add(new Artist(username, age, city));
            case "host" -> Hosts.add(new Host(username, age, city));
            default -> {
                return "Type doesn't exist";
            }
        }
        usernamesInCurrentTest.add(username);
        return "The username " + username + " has been added successfully.";
    }

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

    public static boolean isAnyUserInteractingWith(final List<LibraryEntry> entries) {
        for (String username : getAllUsers()) {
            User user = findUser(username);
            if (user != null && user.isInteractingWith(entries)) {
                return true;
            }
        }
        return false;
    }
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
    public static void removeUser(final User user) {
        if (user instanceof Artist artist) {
            artist.clearAlbums();
            for (Album album : artist.getAlbums()) {
                removeAlbum(album);
            }
            Artists.remove(artist);

            removeArtistSongsFromLikedSongs(artist);
        } else if (user instanceof Host host) {
            host.clearPodcasts();
            for (Podcast podcast : host.getPodcasts()) {
                removePodcast(podcast);
            }
            Hosts.remove(host);
        } else {
            for (Playlist playlist : user.getFollowedPlaylists()) {
                playlist.decreaseFollowers();
            }
            removeUserPlaylists(user);
            users.remove(user);
        }
        usernamesInCurrentTest.remove(user.getUsername());
    }

    private static void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    private static void removeArtistSongsFromLikedSongs(final Artist artist) {
        for (User user : users) {
            user.getLikedSongs()
                    .removeIf(song -> song.getArtist().equals(artist.getUsername()));
        }
    }

    private static void removeUserPlaylists(final User user) {
        for (User u : users) {
            u.getFollowedPlaylists().
                    removeIf(playlist -> playlist.getOwner().equals(user.getUsername()));
        }
    }

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

    public static boolean isAlbumInUse(final Album album) {
        for (User user : users) {
            if (user.isUsingAlbum(album) || user.hasAlbumSongInPlaylist(album)) {
                return true;
            }
        }
        return false;
    }
    public static void removeAlbum(final Album album) {
        songs.removeAll(album.getSongs());
    }

    public static void reset() {
        users.clear();
        songs.clear();
        podcasts.clear();
        Artists.clear();
        Hosts.clear();
        timestamp = 0;
        usernamesInCurrentTest.clear();
        for (Artist artist : Artists) {
            artist.clearAlbums();
        }
        for (Host host : Hosts) {
            host.clearPodcasts();
        }
    }
}
