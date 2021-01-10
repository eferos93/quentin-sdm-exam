package sdmExam.exceptions;

public class InvalidFirstPlayerException extends Exception{

    public InvalidFirstPlayerException(){
        super("Black player should play first");
    }
}
