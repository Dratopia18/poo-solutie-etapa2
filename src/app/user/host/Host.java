package app.user.host;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.user.User;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Getter
public class Host extends User {
    private final Set<Podcast> podcasts;
    private final Set<Announcement> announcements;

    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        this.podcasts = new LinkedHashSet<>();
        this.announcements = new LinkedHashSet<>();
    }

    /**
     * Adauga un nou podcast.
     * @param name numele podcastului
     * @param owner numele hostului
     * @param episodes lista de episoade
     * @return 3 cazuri:
     * 1. Daca hostul are deja un podcast cu acelasi nume, se returneaza un mesaj corespunzator
     * 2. Daca hostul are deja un episod cu acelasi nume, se returneaza un mesaj corespunzator
     * 3. Daca hostul nu are deja un podcast cu acelasi nume si nici un episod cu acelasi nume,
     * se adauga podcastul si se returneaza un mesaj corespunzator
     */
    public String addPodcast(final String name, final String owner, final List<Episode> episodes) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return getUsername() + " has another podcast with the same name.";
            }
        }
        Set<String> episodeNames = new HashSet<>();
        for (Episode episode : episodes) {
            if (!episodeNames.add(episode.getName())) {
                return getUsername() + " has the same episode in this podcast.";
            }
        }
        Podcast newPodcast = new Podcast(name, owner, episodes);
        podcasts.add(newPodcast);
        return getUsername() + " has added new podcast successfully.";
    }

    /**
     * Sterge un podcast.
     * @param podcastName numele podcastului
     * @return 3 cazuri:
     * 1. Daca podcastul nu exista, se returneaza un mesaj corespunzator
     * 2. Daca podcastul exista, dar il ruleaza un user, se returneaza un mesaj corespunzator
     * 3. Daca podcastul exista si nu e rulat de user,
     * se sterge si se returneaza un mesaj corespunzator
     */
    public String removePodcast(final String podcastName) {
        Podcast podcastToRemove = podcasts.stream()
                .filter(podcast -> podcast.getName().equals(podcastName))
                .findFirst()
                .orElse(null);

        if (podcastToRemove == null) {
            return getUsername() + " doesn't have a podcast with the given name.";
        }

        for (String username : Admin.getAllUsers()) {
            User user = Admin.findUser(username);
            if (user != null && user.isCurrentlyPlaying(podcastToRemove)) {
                return getUsername() + " can't delete this podcast.";
            }
        }

        podcasts.remove(podcastToRemove);
        return getUsername() + " deleted the podcast successfully.";
    }

    /**
     * Adauga un nou anunt.
     * @param name numele anuntului
     * @param description descrierea anuntului
     * @return 2 cazuri:
     * 1. Daca hostul are deja un anunt cu acelasi nume, se returneaza un mesaj corespunzator
     * 2. Daca hostul nu are deja un anunt cu acelasi nume, se adauga anuntul si se returneaza
     */
    public String addAnnouncement(final String name, final String description) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return getUsername() + " has already added an announcement with this name.";
            }
        }
        Announcement newAnnouncement = new Announcement(name, description);
        announcements.add(newAnnouncement);
        return getUsername() + " has successfully added new announcement.";
    }

    /**
     * Sterge un anunt.
     * @param name numele anuntului
     * @return 2 cazuri:
     * 1. Daca hostul nu are un anunt cu acel nume, se returneaza un mesaj corespunzator
     * 2. Daca hostul are un anunt cu acel nume, se sterge si se returneaza un mesaj corespunzator
     */
    public String removeAnnouncement(final String name) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                announcements.remove(announcement);
                return getUsername() + " has successfully deleted the announcement.";
            }
        }
        return getUsername() + " has no announcement with the given name.";
    }

    /**
     * Sterge toate podcasturile facute de host.
     */
    public void clearPodcasts() {
        podcasts.clear();
    }
}
