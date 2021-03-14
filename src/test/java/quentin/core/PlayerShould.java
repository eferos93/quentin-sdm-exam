package quentin.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerShould {
    @Test
    public void assignColorProperly() {
        Player player = new Player(Colour.BLACK, "Eros");
        assertEquals(Colour.BLACK, player.getColor());
    }

    @Test
    public void assignColorAndNameProperly() {
        Player player = new Player(Colour.WHITE, "Eros");
        assertAll(
                () -> assertEquals(Colour.WHITE, player.getColor()),
                () -> assertEquals("Eros", player.getName())
        );
    }

    @Test
    public void changeSideCorrectly() {
        Player player = new Player(Colour.WHITE, "Eros");
        player.changeSide();
        assertEquals(Colour.BLACK, player.getColor());
    }
}
