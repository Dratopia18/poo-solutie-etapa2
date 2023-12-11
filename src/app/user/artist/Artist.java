package app.user.artist;

import app.Admin;
import app.audio.Files.Song;
import app.user.User;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Date;
import java.util.Calendar;

@Getter
public class Artist extends User {
    @Getter
    @Setter
    private Set<Album> albums;
    @Setter
    @Getter
    private Set<Event> events;
    private final Set<Merch> merchandise;

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        this.albums = new LinkedHashSet<>();
        this.events = new LinkedHashSet<>();
        this.merchandise = new LinkedHashSet<>();
    }

    /**
     *
     * @param name
     * @param releaseYear
     * @param description
     * @param songs
     * @return
     */
    public String addAlbum(final String name, final String releaseYear,
                           final String description, final List<Song> songs) {
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

    /**
     *
     * @param albumName
     * @return
     */
    public String removeAlbum(final String albumName) {
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

    /**
     *
     * @param eventName
     * @return
     */
    public boolean hasEvent(final String eventName) {
        return events.stream().anyMatch(event -> event.getName().equals(eventName));
    }

    /**
     *
     * @param name
     * @param description
     * @param date
     * @return
     */
    public String addEvent(final String name, final String description, final String date) {
        if (!isValidDate(date)) {
            return "Event for " + getUsername() + " does not have a valid date.";
        }
        if (hasEvent(name)) {
            return getUsername() + " has another event with the same name.";
        }
        events.add(new Event(name, description, date));
        return getUsername() + " has added new event successfully.";
    }

    /**
     * Se verifica daca data respectiva este valida
     * @param date ia data ce va fi verificata
     * @return true daca data este valida, false altfel
     */
    private boolean isValidDate(final String date) {
        final int belowYear = 1900;
        final int aboveYear = 2023;
        final int maxMonths = 12;
        final int february = 2;
        final int maxDaysFebruary = 28;
        final int april = 4;
        final int june = 6;
        final int september = 9;
        final int november = 11;
        final int maxDays = 30;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            Date parsedDate = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);

            if (year < belowYear || year > aboveYear) {
                return false;
            }
            if (month > maxMonths) {
                return false;
            }
            if (month == february && day > maxDaysFebruary) {
                return false;
            }

            return (month != april && month != june
                    && month != september && month != november)
                    || day <= maxDays;

        } catch (ParseException e) {
            return false;
        }
    }

    /**
     *
     * @param eventName
     * @return
     */
    public String removeEvent(final String eventName) {
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

    /**
     *
     * @param name
     * @param description
     * @param price
     * @return
     */
    public String addMerch(final String name, final String description, final int price) {
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

    /**
     *
     */
    public void clearAlbums() {
        albums.clear();
    }

    /**
     * Ia numarul total de like-uri al albumelor artistului
     * @return numarul total de like-uri
     */
    public int getTotalLikes() {
        return albums.stream().flatMap(album -> album.getSongs().stream())
                .mapToInt(Song::getLikes).sum();
    }

}
