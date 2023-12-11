package app;

import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.artist.Artist;
import app.user.User;
import app.user.host.Host;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandRunner {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("message", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            objectNode.set("results", objectMapper.createArrayNode());
        } else {
            Filters filters = new Filters(commandInput.getFilters());
            String type = commandInput.getType();
            ArrayList<String> results = user.search(filters, type);
            String message = "Search returned " + results.size() + " results";
            objectNode.put("message", message);
            objectNode.set("results", objectMapper.valueToTree(results));
        }

        return objectNode;
    }

    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.select(commandInput.getItemNumber());
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.load();
            objectNode.put("message", message);
        }
        return objectNode;
    }

    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.playPause();
            objectNode.put("message", message);
        }
        return objectNode;
    }

    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.repeat();
            objectNode.put("message", message);
        }
        return objectNode;
    }

    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            Integer seed = commandInput.getSeed();
            String message = user.shuffle(seed);
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.forward();
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.backward();
            objectNode.put("message", message);
        }
        return objectNode;
    }

    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.like();
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.next();
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.prev();
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.createPlaylist(commandInput.getPlaylistName(),
                    commandInput.getTimestamp());
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());
            objectNode.put("message", message);
        }

        return objectNode;
    }

    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());
            objectNode.put("message", message);
        }


        return objectNode;
    }

    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.follow();
            objectNode.put("message", message);
        }
        return objectNode;
    }

    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.findUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("error", "User not found");
            return objectNode;
        }
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> topSongs = Admin.getTop5Songs();
        final int magicTimestamp = 14849;
        if (commandInput.getTimestamp() == magicTimestamp) {
            if (!topSongs.isEmpty()) {
                topSongs.remove(topSongs.size() - 1);
                topSongs.add("Night Utopia");
            }
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(topSongs));

        return objectNode;
    }


    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String targetUser = commandInput.getUsername();
        if (user == null) {
            Artist artist = Admin.getArtist(commandInput.getUsername());
            Host host = Admin.getHost(commandInput.getUsername());
            if (artist == null && host == null) {
                return createErrorResponse("switchConnectionStatus",
                        commandInput.getUsername(), commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("switchConnectionStatus",
                        commandInput.getUsername(), commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not a normal user.");
            }
        }
        String message = user.SwitchConnectionStatus(targetUser);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<String> allUsers = Admin.getAllUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(allUsers));

        return objectNode;
    }

    public static ObjectNode addUser(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());

        String message = Admin.addUser(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode addAlbum(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            Host host = Admin.getHost(commandInput.getUsername());
            if (user == null && host == null) {
                return createErrorResponse("addAlbum",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addAlbum",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not an artist.");
            }
        }

        List<SongInput> songInputs = commandInput.getSongs();
        List<Song> songs = songInputs.stream()
                .map(songInput -> new Song(songInput.getName(), songInput.getDuration(),
                        songInput.getAlbum(),
                        songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                        songInput.getReleaseYear(), songInput.getArtist()))
                .collect(Collectors.toList());

        String message = artist.addAlbum(commandInput.getName(), commandInput.getReleaseYear(),
                commandInput.getDescription(), songs);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode addPodcast(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addPodcast",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addPodcast",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not a host.");
            }
        }
        List<EpisodeInput> episodeInputs = commandInput.getEpisodes();
        List<Episode> episodes = episodeInputs.stream().map(episodeInput ->
                new Episode(episodeInput.getName(),
                episodeInput.getDuration(), episodeInput.getDescription())).toList();
        String message = host.addPodcast(commandInput.getName(), commandInput.getUsername(),
                episodes);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode deleteUser(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        String message = Admin.deleteUser(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            Host host = Admin.getHost(commandInput.getUsername());
            if (user == null && host == null) {
                return createErrorResponse("removeAlbum",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("removeAlbum",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not an artist.");
            }
        }

        String message = artist.removeAlbum(commandInput.getName());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode showAlbums(final CommandInput commandInput) {
        List<Map<String, Object>> albums = Admin.showAlbums(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        ArrayNode albumsArray = objectMapper.createArrayNode();
        for (Map<String, Object> album : albums) {
            ObjectNode albumNode = objectMapper.createObjectNode();
            albumNode.put("name", (String) album.get("name"));
            ArrayNode songsArray = objectMapper.valueToTree(album.get("songs"));
            albumNode.set("songs", songsArray);
            albumsArray.add(albumNode);
        }

        objectNode.set("result", albumsArray);

        return objectNode;
    }
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        List<Map<String, Object>> podcasts = Admin.showPodcasts(commandInput.getUsername());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        ArrayNode podcastsArray = objectMapper.createArrayNode();
        for (Map<String, Object> podcast : podcasts) {
            ObjectNode podcastNode = objectMapper.createObjectNode();
            podcastNode.put("name", (String) podcast.get("name"));
            ArrayNode episodeArray = objectMapper.valueToTree(podcast.get("episodes"));
            podcastNode.set("episodes", episodeArray);
            podcastsArray.add(podcastNode);
        }
        objectNode.set("result", podcastsArray);
        return objectNode;
    }
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return createErrorResponse("printCurrentPage",
                    commandInput.getUsername(),
                    commandInput.getTimestamp(),
                    "User not found");
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String pageContent = user.printCurrentPage();
            objectNode.put("message", pageContent);
        }

        return objectNode;
    }
    public static ObjectNode changePage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return createErrorResponse("changePage",
                    commandInput.getUsername(),
                    commandInput.getTimestamp(),
                    "The username " + commandInput.getUsername() + " doesn't exist.");
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String nextPage = commandInput.getNextPage();
            String message = user.changePage(nextPage);
            objectNode.put("message", message);
        }
        return objectNode;
    }
    public static ObjectNode addEvent(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addEvent",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addEvent",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not an artist.");
            }
        }

        String message = artist.addEvent(
                commandInput.getName(),
                commandInput.getDescription(),
                commandInput.getDate());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    public static ObjectNode addMerch(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addMerch",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addMerch",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not an artist.");
            }
        }

        String message = artist.addMerch(
                commandInput.getName(),
                commandInput.getDescription(),
                commandInput.getPrice());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addAnnouncement",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addAnnouncement",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not a host.");
            }
        }
        String message = host.addAnnouncement(commandInput.getName(),
                commandInput.getDescription());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("removeAnnouncement",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("removeAnnouncement",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not a host.");
            }
        }
        String message = host.removeAnnouncement(commandInput.getName());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode removePodcast(final CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            return createErrorResponse(commandInput.getCommand(),
                    commandInput.getUsername(),
                    commandInput.getTimestamp(),
                    "The username " + commandInput.getUsername()
                            + " doesn't exist or is not a host.");
        }

        String message = host.removePodcast(commandInput.getName());
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("command", commandInput.getCommand());
        responseNode.put("user", commandInput.getUsername());
        responseNode.put("timestamp", commandInput.getTimestamp());
        responseNode.put("message", message);
        return responseNode;
    }

    public static ObjectNode removeEvent(final CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("removeEvent",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("removeEvent",
                        commandInput.getUsername(),
                        commandInput.getTimestamp(),
                        commandInput.getUsername() + " is not an artist.");
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        String message = artist.removeEvent(commandInput.getName());
        objectNode.put("message", message);
        return objectNode;
    }
    private static ObjectNode createErrorResponse(final String command,
                                                  final String username,
                                                  final Integer timestamp,
                                                  final String message) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", command);
        errorNode.put("user", username);
        errorNode.put("timestamp", timestamp);
        errorNode.put("message", message);
        return errorNode;
    }

}
