package quentin.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerShould {
    @Test
    public void assignColorProperly() {
        Player player = new Player(Color.BLACK, "Eros");
        assertEquals(Color.BLACK, player.getColor());
    }

    @Test
    public void assignColorAndNameProperly() {
        Player player = new Player(Color.WHITE, "Eros");
        assertAll(
                () -> assertEquals(Color.WHITE, player.getColor()),
                () -> assertEquals("Eros", player.getName())
        );
    }

    @Test
    public void changeSideCorrectly() {
        Player player = new Player(Color.WHITE, "Eros");
        player.changeSide();
        assertEquals(Color.BLACK, player.getColor());
    }
}
