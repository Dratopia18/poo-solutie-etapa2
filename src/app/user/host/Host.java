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
    Set<Podcast> podcasts;
    Set<Announcement> announcements;

    public Host(String username, int age, String city) {
        super(username, age, city);
        this.podcasts = new LinkedHashSet<>();
        this.announcements = new LinkedHashSet<>();
    }

    public String addPodcast(String name, String owner, List<Episode> episodes) {
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

    public String removePodcast(String podcastName) {
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

    public String addAnnouncement(String name, String description) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                return getUsername() + " has already added an announcement with this name.";
            }
        }
        Announcement newAnnouncement = new Announcement(name, description);
        announcements.add(newAnnouncement);
        return getUsername() + " has successfully added new announcement.";
    }

    public String removeAnnouncement(String name) {
        for (Announcement announcement : announcements) {
            if (announcement.getName().equals(name)) {
                announcements.remove(announcement);
                return getUsername() + " has successfully deleted the announcement.";
            }
        }
        return getUsername() + " has no announcement with the given name.";
    }

    public void clearPodcasts() {
        podcasts.clear();
    }
}
