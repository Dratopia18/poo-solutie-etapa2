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
     * Cauta o melodie, un podcast, un album, un playlist, un artist sau un host
     * @param filters filtrele dupa care se face cautarea
     * @param type tipul de obiect cautat
     * @return lista de rezultate, dupa filtrare si tipul de obiect cautat
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
     * Selecteaza un obiect din lista de rezultate
     * @param itemNumber numarul obiectului selectat
     * @return 5 cazuri:
     * 1. Daca nu s-a facut nicio cautare, se returneaza un mesaj corespunzator
     * 2. Daca numarul obiectului selectat este prea mare, se returneaza un mesaj corespunzator
     * 3. Daca obiectul selectat este un artist, se seteaza pagina curenta pe ArtistPage
     * 4. Daca obiectul selectat este un host, se seteaza pagina curenta pe HostPage
     * 5. Daca obiectul selectat nu este nici artist, nici host,
     * se returneaza un mesaj corespunzator
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
     * Incarca un obiect selectat
     * @return 3 cazuri:
     * 1. Daca nu s-a facut nicio selectare de obiect, se returneaza un mesaj corespunzator
     * 2. Daca obiectul selectat este o colectie de melodii si nu are melodii,
     * se returneaza un mesaj corespunzator
     * 3. Daca obiectul selectat este o colectie de melodii si are melodii,
     * se incarca si se returneaza un mesaj corespunzator
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
     * Porneste sau opreste redarea unui obiect incarcat
     * @return 3 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca obiectul ruleaza, se opreste si se returneaza un mesaj corespunzator
     * 3. Daca obiectul nu ruleaza, se porneste si se returneaza un mesaj corespunzator
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
     * Seteaza modul de repetare
     * @return 6 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca modul de repetare este NO_REPEAT, se seteaza modul de repetare la no repeat
     * si se returneaza un mesaj corespunzator
     * 3. Daca modul de repetare este REPEAT_ONCE, se seteaza modul de repetare la repeat once
     * si se returneaza un mesaj corespunzator
     * 4. Daca modul de repetare este REPEAT_ALL, se seteaza modul de repetare la repeat all
     * si se returneaza un mesaj corespunzator
     * 5. Daca modul de repetare este REPEAT_INFINITE,
     * se seteaza modul de repetare la repeat infinite, si se returneaza un mesaj corespunzator
     * 6. Daca modul de repetare este REPEAT_CURRENT_SONG,
     * se seteaza modul de repetare la repeat current song, si se returneaza un mesaj corespunzator
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
     * Seteaza modul de shuffle
     * @param seed seed-ul pentru shuffle
     * @return 4 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca obiectul incarcat nu este o colectie de melodii (playlist sau album),
     * se returneaza un mesaj corespunzator
     * 3. Daca obiectul incarcat este o colectie de melodii (playlist sau album)
     * si nu este shuffle, se seteaza modul de shuffle la shuffle
     * si se returneaza un mesaj corespunzator
     * 4. Daca obiectul incarcat este o colectie de melodii (playlist sau album)
     * si este shuffle, se seteaza modul de shuffle la no shuffle
     * si se returneaza un mesaj corespunzator
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
     * Sare cu 90 de secunde inainte
     * @return 3 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca obiectul incarcat nu este un podcast, se returneaza un mesaj corespunzator
     * 3. Daca obiectul incarcat este un podcast, se sare cu 90 de secunde inainte
     * si se returneaza un mesaj corespunzator
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
     * Sare cu 90 de secunde inapoi
     * @return 3 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca obiectul incarcat nu este un podcast, se returneaza un mesaj corespunzator
     * 3. Daca obiectul incarcat este un podcast, se sare cu 90 de secunde inapoi
     * si se returneaza un mesaj corespunzator
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
     * Adauga sau sterge o melodie din lista de melodii liked
     * @return 4 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca obiectul incarcat nu este o melodie, se returneaza un mesaj corespunzator
     * 3. Daca obiectul incarcat este o melodie si este liked, se sterge din lista de melodii liked
     * si se returneaza un mesaj corespunzator
     * 4. Daca obiectul incarcat este o melodie si nu este liked,
     * se adauga in lista de melodii liked si se returneaza un mesaj corespunzator
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
     * Sare la urmatoarul obiect din player
     * @return 3 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca nu exista urmatorul obiect, se returneaza un mesaj corespunzator
     * 3. Daca exista urmatorul obiect, se sare la urmatorul obiect
     * si se returneaza un mesaj corespunzator
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
     * Sare la obiectul anterior din player
     * @return 3 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca nu exista obiectul anterior, se returneaza un mesaj corespunzator
     * 3. Daca exista obiectul anterior, se sare la obiectul anterior
     * si se returneaza un mesaj corespunzator
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
     * Adauga un nou playlist
     * @param name numele playlistului
     * @param timestamp timestamp-ul la care a fost creat playlistul
     * @return 2 cazuri:
     * 1. Daca exista deja un playlist cu acelasi nume, se returneaza un mesaj corespunzator
     * 2. Daca nu exista deja un playlist cu acelasi nume, se adauga playlistul
     * si se returneaza un mesaj corespunzator
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }


        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Adaugi / stergi o melodie dintr-un playlist
     * @param id id-ul playlistului
     * @return 5 cazuri:
     * 1. Daca nu s-a incarcat niciun obiect, se returneaza un mesaj corespunzator
     * 2. Daca obiectul incarcat nu este o melodie, se returneaza un mesaj corespunzator
     * 3. Daca playlistul nu exista, se returneaza un mesaj corespunzator
     * 4. Daca melodia este deja in playlist, se sterge din playlist
     * si se returneaza un mesaj corespunzator
     * 5. Daca melodia nu este in playlist, se adauga in playlist
     * si se returneaza un mesaj corespunzator
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
     * Seteaza vizibilitatea unui playlist
     * @param playlistId id-ul playlistului
     * @return 3 cazuri:
     * 1. Daca playlistul nu exista, se returneaza un mesaj corespunzator
     * 2. Daca playlistul exista si este public, se seteaza vizibilitatea la private
     * si se returneaza un mesaj corespunzator
     * 3. Daca playlistul exista si este private, se seteaza vizibilitatea la public
     * si se returneaza un mesaj corespunzator
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
     * Se afiseaza toate playlisturile, cu melodiile lor
     * vizibilitatea si numarul de followers al fiecarui playlist
     * @return lista de playlisturi
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Adauga sau sterge un playlist din lista de playlisturi urmarite
     * @return 5 cazuri:
     * 1. Daca nu s-a selectat niciun playlist, se returneaza un mesaj corespunzator
     * 2. Daca playlistul selectat nu e playlist, se returneaza un mesaj corespunzator
     * 3. Daca playlistul selectat este al userului, se returneaza un mesaj corespunzator
     * 4. Daca playlistul selectat este deja urmarit, se sterge din lista de playlisturi urmarite
     * si se returneaza un mesaj corespunzator
     * 5. Daca playlistul selectat nu este urmarit, se adauga in lista de playlisturi urmarite
     * si se returneaza un mesaj corespunzator
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
     * Afiseaza statistici despre player
     * @return statistici despre player
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Afiseaza toate melodiile preferate
     * @return lista de melodii preferate
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Se afiseaza toate genurile muzicale preferate
     * @return lista de genuri muzicale preferate
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
     * Se schimba statusul de online / offline al unui user
     * @param targetUsername username-ul userului
     * @return 2 cazuri:
     * 1. Daca userul nu este un user normal, se returneaza un mesaj corespunzator
     * 2. Daca userul este un user normal, se schimba statusul de online / offline
     * si se returneaza un mesaj corespunzator
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
     * Se schimba pagina curenta
     * @param nextPage pagina urmatoare
     * @return 2 cazuri:
     * 1. Daca pagina urmatoare este Home sau LikedContent, se schimba pagina curenta
     * si se returneaza un mesaj corespunzator
     * 2. Daca pagina urmatoare nu este Home sau LikedContent, se returneaza un mesaj corespunzator
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
     * Se schimba statusul de online / offline
     */
    public void switchOnlineStatus() {
        onlineStatus = !onlineStatus;
    }

    /**
     * Se afiseaza statusul de online / offline
     * @return statusul de online / offline
     */
    public boolean getOnlineStatus() {
        return onlineStatus;
    }

    /**
     * Se afiseaza pagina curenta
     * @return 5 cazuri:
     * 1. Daca pagina curenta este Home, se afiseaza pagina Home
     * 2. Daca pagina curenta este LikedContent, se afiseaza pagina LikedContent
     * 3. Daca pagina curenta este ArtistPage si artistul selectat nu este null,
     * se afiseaza pagina ArtistPage, iar daca artistul selectat este null,
     * se afiseaza un mesaj corespunzator
     * 4. Daca pagina curenta este HostPage si hostul selectat nu este null,
     * se afiseaza pagina HostPage, iar daca hostul selectat este null,
     * se afiseaza un mesaj corespunzator
     * 5. Daca pagina curenta nu este nici Home, nici LikedContent, nici ArtistPage, nici HostPage,
     * se afiseaza un mesaj corespunzator
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
     * Simuleaza trecerea timpului
     * @param time timpul care trece
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
     * Verifica daca din chestiile create de un user
     * (melodiile din playlist, melodiile din album, episoadele din podcast)
     * sunt folosite in momentul de fata de un user
     * @param entries lista de chestii create de un user
     *                (melodiile din playlist, melodiile din album, episoadele din podcast)
     * @return true daca sunt folosite, false in caz contrar
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
     * Verifica daca un album sau un podcast este folosit in momentul de fata
     * @param entry ia albumul sau podcastul respectiv
     * @return true daca este folosit, false in caz contrar
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
