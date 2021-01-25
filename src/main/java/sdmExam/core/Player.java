package sdmExam.core;

public class Player {
    private Stone color;
    private final String name;

    public Player(Stone color, String name) {
        this.color = color;
        this.name = name;
    }

    public Stone getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void changeSide() {
        color = color.getOppositeColor();
    }
}
