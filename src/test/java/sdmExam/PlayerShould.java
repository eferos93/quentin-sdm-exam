package sdmExam;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerShould {
    @Test
    public void assignColorProperly() {
        Player player = new Player(Stone.BLACK, "Eros");
        assertEquals(Stone.BLACK, player.getColor());
    }

    @Test
    public void assignColorAndNameProperly() {
        Player player = new Player(Stone.WHITE, "Eros");
        assertAll(
                () -> assertEquals(Stone.WHITE, player.getColor()),
                () -> assertEquals("Eros", player.getName())
        );
    }

    @Test
    public void changeSideCorrectly() {
        Player player = new Player(Stone.WHITE, "Eros");
        player.changeSide();
        assertEquals(Stone.BLACK, player.getColor());
    }
}
