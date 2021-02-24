package quentin.exceptions;

public class InvalidFirstPlayerException extends quentinException {

    public InvalidFirstPlayerException(){
        super("Black player should play first");
    }
}
