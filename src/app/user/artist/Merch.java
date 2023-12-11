package app.user.artist;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Merch {

    private String name;
    private int price;
    private String description;

    public Merch(final String name, final int price, final String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
