package app.user.artist;

import app.Admin;
import app.audio.Files.Song;
import app.user.User;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
public class Artist extends User{
    @Getter
    @Setter
    private Set<Album> albums;
    @Setter
    @Getter
    private Set<Event> events;
    private final Set<Merch> merchandise;

    public Artist(String username, int age, String city) {
        super(username, age, city);
        this.albums = new LinkedHashSet<>();
        this.events = new LinkedHashSet<>();
        this.merchandise = new LinkedHashSet<>();
    }

    public String addAlbum(String name, String releaseYear, String description, List<Song> songs) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return getUsername() + " has another album with the same name.";
            }
        }

        Set<String> songNames = new HashSet<>();
        for (Song song : songs) {
            if (!songNames.add(song.getName())) {
                return getUsername() + " has the same song at least twice in this album.";
            }
        }

        Album newAlbum = new Album(name, getUsername(), releaseYear, description, songs);
        albums.add(newAlbum);

        return getUsername() + " has added new album successfully.";
    }

    public String removeAlbum(String albumName) {
        Album albumToRemove = null;
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                albumToRemove = album;
                break;
            }
        }

        if (albumToRemove == null) {
            return getUsername() + " doesn't have an album with the given name.";
        }

        if (Admin.isAlbumInUse(albumToRemove)) {
            return getUsername() + " can't delete this album.";
        }

        albums.remove(albumToRemove);
        Admin.removeAlbum(albumToRemove);
        return getUsername() + " deleted the album successfully.";
    }

    public boolean hasEvent(String eventName) {
        return events.stream().anyMatch(event -> event.getName().equals(eventName));
    }

    public String addEvent(String name, String description, String date) {
        if (!isValidDate(date)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }
        if (hasEvent(name)) {
            return getUsername() + " has another event with the same name.";
        }
        events.add(new Event(name, description, date));
        return getUsername() + " has added new event successfully.";
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            Date parsedDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            if (year < 1900 || year > 2023) {
                return false;
            }
            if (month > 12) {
                return false;
            }
            if (month == 2 && day > 28) {
                return false;
            }

            return (month != 4 && month != 6 && month != 9 && month != 11) || day <= 30;

        } catch (ParseException e) {
            return false;
        }
    }
    public String removeEvent(String eventName) {
        if (!hasEvent(eventName)) {
            return getUsername() + " doesn't have an event with the given name.";
        }
        Event eventToRemove = null;
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                eventToRemove = event;
                break;
            }
        }
        events.remove(eventToRemove);
        return getUsername() + " deleted the event successfully.";
    }
    public String addMerch(String name, String description, int price) {
        for (Merch item : merchandise) {
            if (item.getName().equals(name)) {
                return getUsername() + " has merchandise with the same name.";
            }
        }
        if (price < 0) {
            return "Price for merchandise can not be negative.";
        }
        Merch newMerch = new Merch(name, price, description);
        merchandise.add(newMerch);
        return getUsername() + " has added new merchandise successfully.";
    }

    public void clearAlbums() {
        albums.clear();
    }
    public int getTotalLikes() {
        return albums.stream().flatMap(album -> album.getSongs().stream())
                .mapToInt(Song::getLikes).sum();
    }

}
