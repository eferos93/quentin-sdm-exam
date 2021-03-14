package quentin.core;

public class Player {
    private Colour colour;
    private final String name;

    public Player(Colour colour, String name) {
        this.colour = colour;
        this.name = name;
    }

    public final Colour getColor() {
        return colour;
    }

    public final String getName() {
        return name;
    }

    protected void changeSide() {
        colour = colour.getOppositeColor();
    }
}
