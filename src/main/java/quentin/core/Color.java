package quentin.core;

public enum Color {
    WHITE,
    BLACK;

    public Color getOppositeColor() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
        };
    }
}
