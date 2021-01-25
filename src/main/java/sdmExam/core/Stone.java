package sdmExam.core;

public enum Stone {
    WHITE,
    BLACK,
    NONE;

    public Stone getOppositeColor() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            case NONE -> NONE;
        };
    }
}
