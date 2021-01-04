package sdmExam;

public class Game {
    private final Board board = new Board();
    private Stone lastPlay = Stone.NONE;

    public void play(Stone player, Position position) throws Exception {

        if (isInvalidFirstPlayer(player)) {
            throw new Exception("Black player should play first.");
        }

        if (isARepeatedPlay(player)) {
            throw new Exception("A player cannot play twice in a row.");
        }

        if (board.isOccupied(position)) {
            throw new Exception("Position is already occupied.");
        }

      //  if ( position.getColumn()== actualPosition.getColumn()+1 && position.getRow()==actualPosition.getRow()+1)  {
     //       throw new Exception("A player cannot put a stone diagonally adjacent to another stone of the same colour.");
     //   }

        board.addStoneAt(player, position);
        lastPlay = player;
    }

    private boolean isARepeatedPlay(Stone player) {
        return lastPlay == player;
    }

    private boolean isInvalidFirstPlayer(Stone player) {
        return isARepeatedPlay(Stone.NONE) && player == Stone.WHITE;
    }
}
