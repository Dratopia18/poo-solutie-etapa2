package app.user.artist;

import lombok.Getter;

@Getter
public class Merch {

    private String name;
    private int price;
    private String description;

    public Merch(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
