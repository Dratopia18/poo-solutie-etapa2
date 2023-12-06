package app.user.host;

import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.user.User;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Host extends User {
    Set<Podcast> podcasts;
    public Host(String username, int age, String city) {
        super(username, age, city);
        this.podcasts = new LinkedHashSet<>();
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
}
