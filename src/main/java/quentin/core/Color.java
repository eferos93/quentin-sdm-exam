package quentin.core;

public enum Color {
    WHITE,
    BLACK,
    NONE;

    public Color getOppositeColor() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            case NONE -> NONE;
        };
    }
}
