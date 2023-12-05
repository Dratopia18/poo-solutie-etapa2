package app;

import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.artist.Artist;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;
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
            objectNode.put("results", objectMapper.createArrayNode());
        } else {
            Filters filters = new Filters(commandInput.getFilters());
            String type = commandInput.getType();
            ArrayList<String> results = user.search(filters, type);
            String message = "Search returned " + results.size() + " results";
            objectNode.put("message", message);
            objectNode.put("results", objectMapper.valueToTree(results));
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
        objectNode.put("result", objectMapper.valueToTree(playlists));

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
        User user = Admin.getUser(commandInput.getUsername());
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
        objectNode.put("stats", objectMapper.valueToTree(stats));

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
        objectNode.put("result", objectMapper.valueToTree(songs));

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
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    public static ObjectNode getTop5Songs(CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    public static ObjectNode getTop5Playlists(CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

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
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

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
    private static ObjectNode createErrorResponse(String command, String username, Integer timestamp, String message) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", command);
        errorNode.put("user", username);
        errorNode.put("timestamp", timestamp);
        errorNode.put("message", message);
        return errorNode;
    }

}
