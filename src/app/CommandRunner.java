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
    static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode search(CommandInput commandInput) {
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

    public static ObjectNode select(CommandInput commandInput) {
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

    public static ObjectNode load(CommandInput commandInput) {
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

    public static ObjectNode playPause(CommandInput commandInput) {
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

    public static ObjectNode repeat(CommandInput commandInput) {
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

    public static ObjectNode shuffle(CommandInput commandInput) {
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

    public static ObjectNode forward(CommandInput commandInput) {
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

    public static ObjectNode backward(CommandInput commandInput) {
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

    public static ObjectNode like(CommandInput commandInput) {
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

    public static ObjectNode next(CommandInput commandInput) {
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

    public static ObjectNode prev(CommandInput commandInput) {
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

    public static ObjectNode createPlaylist(CommandInput commandInput) {
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

    public static ObjectNode addRemoveInPlaylist(CommandInput commandInput) {
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

    public static ObjectNode switchVisibility(CommandInput commandInput) {
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

    public static ObjectNode showPlaylists(CommandInput commandInput) {
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

    public static ObjectNode follow(CommandInput commandInput) {
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

    public static ObjectNode status(CommandInput commandInput) {
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

    public static ObjectNode showLikedSongs(CommandInput commandInput) {
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

    public static ObjectNode getPreferredGenre(CommandInput commandInput) {
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

    public static ObjectNode getTop5Songs(CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getTop5Albums(CommandInput commandInput) {
        List<String> albums = Admin.getTop5Albums();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(albums));

        return objectNode;
    }

    public static ObjectNode getTop5Playlists(CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }
    public static ObjectNode getTop5Artists(CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }
    public static ObjectNode switchConnectionStatus(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String targetUser = commandInput.getUsername();
        if (user == null) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", "The username " + targetUser + " doesn't exist.");
            return objectNode;
        }
        String message = user.SwitchConnectionStatus(targetUser);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode getOnlineUsers(CommandInput commandInput) {
        List<String> onlineUsers = Admin.getOnlineUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    public static ObjectNode getAllUsers(CommandInput commandInput) {
        List<String> allUsers = Admin.getAllUsers();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.set("result", objectMapper.valueToTree(allUsers));

        return objectNode;
    }

    public static ObjectNode addUser(CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());

        String message = Admin.addUser(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode addAlbum(CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addAlbum", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addAlbum", commandInput.getUsername(), commandInput.getTimestamp(), commandInput.getUsername() + " is not an artist.");
            }
        }

        List<SongInput> songInputs = commandInput.getSongs();
        List<Song> songs = songInputs.stream()
                .map(songInput -> new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
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

    public static ObjectNode addPodcast(CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addPodcast", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addPodcast", commandInput.getUsername(), commandInput.getTimestamp(), commandInput.getUsername() + " is not a host.");
            }
        }
        List<EpisodeInput> episodeInputs = commandInput.getEpisodes();
        List<Episode> episodes = episodeInputs.stream().map(episodeInput -> new Episode(episodeInput.getName(),
                episodeInput.getDuration(), episodeInput.getDescription())).toList();
        String message = host.addPodcast(commandInput.getName(), commandInput.getUsername(), episodes);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode deleteUser(CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        String message = Admin.deleteUser(commandInput);
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode removeAlbum(CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            return createErrorResponse(
                    commandInput.getCommand(),
                    commandInput.getUsername(),
                    commandInput.getTimestamp(),
                    "The username " + commandInput.getUsername() + " doesn't exist."
            );
        }

        String message = artist.removeAlbum(commandInput.getName());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode showAlbums(CommandInput commandInput) {
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
    public static ObjectNode showPodcasts(CommandInput commandInput) {
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
    public static ObjectNode printCurrentPage(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return createErrorResponse("printCurrentPage", commandInput.getUsername(), commandInput.getTimestamp(), "User not found");
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.getOnlineStatus()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String pageContent = user.printCurrentPage();
            objectNode.put("message", pageContent);
        }

        return objectNode;
    }
    public static ObjectNode changePage(CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            return createErrorResponse("changePage", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
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
    public static ObjectNode addEvent(CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addEvent", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addEvent", commandInput.getUsername(), commandInput.getTimestamp(), commandInput.getUsername() + " is not an artist.");
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
    public static ObjectNode addMerch(CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addMerch", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addMerch", commandInput.getUsername(), commandInput.getTimestamp(), commandInput.getUsername() + " is not an artist.");
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
    public static ObjectNode addAnnouncement(CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("addAnnouncement", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("addAnnouncement", commandInput.getUsername(), commandInput.getTimestamp(), commandInput.getUsername() + " is not a host.");
            }
        }
        String message = host.addAnnouncement(commandInput.getName(), commandInput.getDescription());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    public static ObjectNode removeAnnouncement(CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("removeAnnouncement", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("removeAnnouncement", commandInput.getUsername(), commandInput.getTimestamp(), commandInput.getUsername() + " is not a host.");
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

    public static ObjectNode removePodcast(CommandInput commandInput) {
        Host host = Admin.getHost(commandInput.getUsername());
        if (host == null) {
            return createErrorResponse(commandInput.getCommand(), commandInput.getUsername(),
                    commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist or is not a host.");
        }

        String message = host.removePodcast(commandInput.getName());
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("command", commandInput.getCommand());
        responseNode.put("user", commandInput.getUsername());
        responseNode.put("timestamp", commandInput.getTimestamp());
        responseNode.put("message", message);
        return responseNode;
    }

    public static ObjectNode removeEvent(CommandInput commandInput) {
        Artist artist = Admin.getArtist(commandInput.getUsername());
        if (artist == null) {
            User user = Admin.getUser(commandInput.getUsername());
            if (user == null) {
                return createErrorResponse("removeEvent", commandInput.getUsername(), commandInput.getTimestamp(), "The username " + commandInput.getUsername() + " doesn't exist.");
            } else {
                return createErrorResponse("removeEvent", commandInput.getUsername(), commandInput.getTimestamp(), commandInput.getUsername() + " is not an artist.");
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
    private static ObjectNode createErrorResponse(String command, String username, Integer timestamp, String message) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", command);
        errorNode.put("user", username);
        errorNode.put("timestamp", timestamp);
        errorNode.put("message", message);
        return errorNode;
    }

}
