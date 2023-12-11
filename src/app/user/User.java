package app.user;

import app.Admin;
import app.Page;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.user.artist.Album;
import app.user.artist.Artist;
import app.user.host.Host;
import app.utils.Enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    private boolean onlineStatus;
    @Getter @Setter
    private String currentPage;
    @Setter
    private Artist selectedArtist;
    @Setter
    private Host selectedHost;
    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        onlineStatus = true;
        currentPage = "Home";
    }

    /**
     *
     * @param filters
     * @param type
     * @return
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    /**
     *
     * @param itemNumber
     * @return
     */
    public String select(final int itemNumber) {
        List<Artist> artists = Admin.getArtists();
        List<Host> hosts = Admin.getHosts();
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }

        for (Artist artist : artists) {
            if (artist.getUsername().equals(selected.getName())) {
                setCurrentPage("ArtistPage");
                setSelectedArtist(artist);
                return "Successfully selected %s's page.".formatted(selected.getName());
            }
        }
        for (Host host : hosts) {
            if (host.getUsername().equals(selected.getName())) {
                setCurrentPage("HostPage");
                setSelectedHost(host);
                return "Successfully selected %s's page.".formatted(selected.getName());
            }
        }
        return "Successfully selected %s.".formatted(selected.getName());
    }

    /**
     *
     * @return
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (searchBar.getLastSelected() instanceof AudioCollection
                && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();
        player.pause();
        return "Playback loaded successfully.";
    }

    /**
     *
     * @return
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }

    }

    /**
     *
     * @return
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT:
                repeatStatus = "no repeat";
                break;
            case REPEAT_ONCE:
                repeatStatus = "repeat once";
                break;
            case REPEAT_ALL:
                repeatStatus = "repeat all";
                break;
            case REPEAT_INFINITE:
                repeatStatus = "repeat infinite";
                break;
            case REPEAT_CURRENT_SONG:
                repeatStatus = "repeat current song";
                break;
            default:
                break;
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     *
     * @param seed
     * @return
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }


        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     *
     * @return
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     *
     * @return
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     *
     * @return
     */
    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }


        if (!player.getType().equals("song") && !player.getType().equals("playlist")
        && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     *
     * @return
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     *
     * @return
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     *
     * @param name
     * @param timestamp
     * @return
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }


        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     *
     * @param id
     * @return
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     *
     * @param playlistId
     * @return
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     *
     * @return
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     *
     * @return
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     *
     * @return
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     *
     * @return
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     *
     * @return
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     *
     * @param targetUsername
     * @return
     */
    public String switchConnectionStatus(final String targetUsername) {
        User targetUser = Admin.getUser(targetUsername);
        List<User> normalUsers = Admin.getNormalUsers();
        if (!normalUsers.contains(targetUser)) {
            return targetUsername + " is not a normal user.";
        }
        assert targetUser != null;
        targetUser.switchOnlineStatus();
        return targetUsername + " has changed status successfully.";
    }

    /**
     *
     * @param nextPage
     * @return
     */
    public String changePage(final String nextPage) {
        if (Objects.equals(nextPage, "Home") || Objects.equals(nextPage, "LikedContent")) {
            setCurrentPage(nextPage);
            return getUsername() + " accessed " + nextPage + " successfully.";
        } else {
            return getUsername() + " is trying to access a non-existent page.";
        }
    }

    /**
     *
     */
    public void switchOnlineStatus() {
        onlineStatus = !onlineStatus;
    }

    /**
     *
     * @return
     */
    public boolean getOnlineStatus() {
        return onlineStatus;
    }

    /**
     *
     * @return
     */
    public String printCurrentPage() {
        return switch (currentPage) {
            case "Home" -> Page.generateHomePage(this);
            case "LikedContent" -> Page.generateLikedContentPage(this);
            case "ArtistPage" -> {
                if (selectedArtist != null) {
                    yield Page.generateArtistPage(selectedArtist);
                } else {
                    yield "No artist selected.";
                }
            }
            case "HostPage" -> {
                if (selectedHost != null) {
                    yield Page.generateHostPage(selectedHost);
                } else {
                    yield "No host selected.";
                }
            }
            default -> "Invalid page.";
        };
    }

    /**
     *
     * @param time
     */
    public void simulateTime(final int time) {
        if (onlineStatus) {
            player.simulatePlayer(time);
        } else if (player.getCurrentAudioFile() != null && !player.getPaused()) {
            int remainingTime = player.getStats().getRemainedTime() - time;
            player.getStats().setRemainedTime(Math.max(0, remainingTime));
        }
    }

    /**
     *
     * @param entries
     * @return
     */
    public boolean isInteractingWith(final List<LibraryEntry> entries) {
        PlayerStats stats = this.getPlayerStats();
        if (stats != null && stats.getRemainedTime() > 0) {
            for (LibraryEntry entry : entries) {
                if (entry.getName().equals(stats.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica daca utilizatorul este pe pagina unui artist sau a unui host
     * @param artistOrHostUsername artistul sau hostul dat
     * @return true daca este pe pagina artistului sau hostului, false altfel
     */
    public boolean isOnArtistOrHostPage(final String artistOrHostUsername) {
        if (Objects.equals(currentPage, "ArtistPage") && selectedArtist != null
                && selectedArtist.getUsername().equals(artistOrHostUsername)) {
            return true;
        }
        return Objects.equals(currentPage, "HostPage") && selectedHost != null
                && selectedHost.getUsername().equals(artistOrHostUsername);
    }

    /**
     * Verifica daca un album este folosit in momentul de fata
     * @param album ia albumul respectiv
     * @return true daca este folosit, false altfel
     */
    public boolean isUsingAlbum(final Album album) {
        if (this.player.getCurrentAudioFile() == null) {
            return false;
        }

        AudioFile currentTrack = this.player.getCurrentAudioFile();
        return album.getSongs().contains(currentTrack);
    }

    /**
     * verifica daca un album are vreo melodie dintr-un playlist
     * @param album ia albumul respectiv
     * @return true daca are vreo melodie dintr-un playlist, false altfel
     */
    public boolean hasAlbumSongInPlaylist(final Album album) {
        for (Playlist playlist : playlists) {
            for (Song song : album.getSongs()) {
                if (playlist.containsSong(song)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param entry
     * @return
     */
    public boolean isCurrentlyPlaying(final LibraryEntry entry) {
        LibraryEntry currentSource = player.getCurrentSource();
        if (currentSource != null && entry instanceof Podcast podcast) {
            return podcast.getEpisodes().contains(currentSource);
        } else if (currentSource instanceof Album && entry instanceof Album) {
            return currentSource.getName().equals(entry.getName());
        }
        return false;
    }
}
