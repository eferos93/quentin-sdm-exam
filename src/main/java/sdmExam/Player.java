package sdmExam;

public class Player {
    private Stone color;
    private String name;

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
        if (color == Stone.BLACK) {
            color = Stone.WHITE;
        } else {
            color = Stone.BLACK;
        }
    }
}
