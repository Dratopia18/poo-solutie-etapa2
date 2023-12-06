package app;

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

public class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Host> hosts = new ArrayList<>();
    private static int timestamp = 0;
    private static Set<String> usernamesInCurrentTest = new HashSet<>();

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription()));
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
    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static Artist getArtist(String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }
    public static Host getHost(String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }
    public static void updateTimestamp(int newTimestamp) {
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
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
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
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }
    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for(User user: users) {
            if(user.getOnlineStatus()) {
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
        for (Artist artist : artists) {
            allUsers.add(artist.getUsername());
        }
        for (Host host : hosts) {
            allUsers.add(host.getUsername());
        }
        return allUsers;
    }

    public static List<User> getNormalUsers() {
        return new ArrayList<>(users);
    }
    public static List<Artist> getArtists() {
        return new ArrayList<>(artists);
    }
    public static List<Host> getHosts() {
        return new ArrayList<>(hosts);
    }
    public static String addUser(CommandInput commandInput) {
        String username = commandInput.getUsername();
        User existingUser = getUser(username);
        if(usernamesInCurrentTest.contains(username)) {
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
            case "artist" -> artists.add(new Artist(username, age, city));
            case "host" -> hosts.add(new Host(username, age, city));
        }
        usernamesInCurrentTest.add(username);
        return "The username " + username + " has been added successfully.";
    }
    public static String deleteUser(CommandInput commandInput) {
        String username = commandInput.getUsername();
        User userToDelete = getUser(username);
        Artist artistToDelete = getArtist(username);
        Host hostToDelete = getHost(username);
        if (userToDelete == null && artistToDelete == null && hostToDelete == null) {
            return "The username " + username + " doesn't exist.";
        }
        if (!hasOngoingProcess(userToDelete) && !hasOngoingProcess(artistToDelete)
                && !hasOngoingProcess(hostToDelete)) {
            return username + " can't be deleted.";
        }
        if (userToDelete != null) {
            removeUser(userToDelete);
        } else if (artistToDelete != null) {
            removeUser(artistToDelete);
        } else if (hostToDelete != null) {
            removeUser(hostToDelete);
        }
        return username + " was successfully deleted.";
    }
    public static boolean hasOngoingProcess(User user) {
        return false;
    }
    public static void removeUser(User user) {
        if (user instanceof Artist) {
            artists.remove(user);
        } else if (user instanceof Host) {
            hosts.remove(user);
        } else {
            users.remove(user);
        }
    }
    public static List<Map<String, Object>> showAlbums(String artistUsername) {
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
    public static List<Map<String, Object>> showPodcasts(String hostUsername) {
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

    public static void reset() {
        users.clear();
        songs.clear();
        podcasts.clear();
        artists.clear();
        hosts.clear();
        timestamp = 0;
        usernamesInCurrentTest.clear();
        for (Artist artist : artists) {
            artist.clearAlbums();
        }
    }
}
