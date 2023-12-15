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
     * Adauga un nou album.
     * @param name numele albumului
     * @param releaseYear anul de lansare
     * @param description descrierea albumului
     * @param songs lista de melodii
     * @return 3 cazuri:
     * 1. Daca artistul are deja un album cu acelasi nume, se returneaza un mesaj corespunzator
     * 2. Daca artistul are deja o melodie cu acelasi nume, se returneaza un mesaj corespunzator
     * 3. Daca artistul nu are deja un album cu acelasi nume si nici o melodie cu acelasi nume,
     * se adauga albumul si se returneaza un mesaj corespunzator
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
        Admin.addAlbum(newAlbum);
        return getUsername() + " has added new album successfully.";
    }

    /**
     * Sterge un album.
     * @param albumName numele albumului
     * @return 3 cazuri:
     * 1. Daca albumul nu exista, se returneaza un mesaj corespunzator
     * 2. Daca albumul exista, dar il ruleaza un user, se returneaza un mesaj corespunzator
     * 3. Daca albumul exista si nu e rulat de user,
     * se sterge si se returneaza un mesaj corespunzator
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
     * Se verifica daca artistul are un eveniment cu acelasi nume
     * @param eventName numele evenimentului
     * @return true daca artistul are un eveniment cu acelasi nume, false in caz contrar
     */
    public boolean hasEvent(final String eventName) {
        return events.stream().anyMatch(event -> event.getName().equals(eventName));
    }

    /**
     * Se adauga un nou eveniment
     * @param name numele evenimentului
     * @param description descrierea evenimentului
     * @param date data evenimentului
     * @return 3 cazuri:
     * 1. Daca data nu este valida, se returneaza un mesaj corespunzator
     * 2. Daca artistul are deja un eveniment cu acelasi nume, se returneaza un mesaj corespunzator
     * 3. Daca artistul nu are deja un eveniment cu acelasi nume si data este valida,
     * se adauga evenimentul si se returneaza un mesaj corespunzator
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
     * @return true daca data este valida, false in caz contrar
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
     * Sterge un eveniment
     * @param eventName numele evenimentului
     * @return 2 cazuri:
     * 1. Daca evenimentul nu exista, se returneaza un mesaj corespunzator
     * 2. Daca evenimentul exista, se sterge si se returneaza un mesaj corespunzator
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
     * Adauga un nou produs de merch
     * @param name numele produsului
     * @param description descrierea produsului
     * @param price pretul produsului
     * @return 3 cazuri:
     * 1. Daca artistul are deja un produs de merch cu acelasi nume,
     * se returneaza un mesaj corespunzator
     * 2. Daca pretul este negativ, se returneaza un mesaj corespunzator
     * 3. Daca artistul nu are deja un produs de merch cu acelasi nume si pretul este pozitiv,
     * se adauga produsul de merch si se returneaza un mesaj corespunzator
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
     * Sterge toate albumele ale artistului
     */
    public void clearAlbums() {
        albums.clear();
    }

    /**
     * Ia numarul total de like-uri al albumului artistului,
     * adica suma like-urilor tuturor melodiilor din toate albumele artistului
     * @return numarul total de like-uri
     */
    public int getTotalLikes() {
        return albums.stream().flatMap(album -> album.getSongs().stream())
                .mapToInt(Song::getLikes).sum();
    }

}
