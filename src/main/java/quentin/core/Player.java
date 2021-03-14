package quentin.core;

public class Player {
    private Stone color;
    private final String name;

    public Player(Stone color, String name) {
        this.color = color;
        this.name = name;
    }

    public final Stone getColor() {
        return color;
    }

    public final String getName() {
        return name;
    }

    protected void changeSide() {
        color = color.getOppositeColor();
    }
}
