package sdmExam;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerShould {
    @Test
    public void assignColorProperly() {
        Player player = new Player(Stone.BLACK);
        assertEquals(Stone.BLACK, player.getColor());
    }
}
