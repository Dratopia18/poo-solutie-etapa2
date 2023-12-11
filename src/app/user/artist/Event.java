package app.user.artist;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Event {
    private String name;
    private String description;
    private String date;

    public Event(final String name, final String description, final String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

}

