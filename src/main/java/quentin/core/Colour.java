package quentin.core;

public enum Colour {
    WHITE,
    BLACK;

    public Colour getOppositeColor() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
        };
    }
}
