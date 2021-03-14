package quentin.core;

public class Player {
    private Color color;
    private final String name;

    public Player(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public final Color getColor() {
        return color;
    }

    public final String getName() {
        return name;
    }

    protected void changeSide() {
        color = color.getOppositeColor();
    }
}
