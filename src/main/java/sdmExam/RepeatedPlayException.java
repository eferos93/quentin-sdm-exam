package sdmExam;

public class RepeatedPlayException extends Exception{

    public RepeatedPlayException(){
        super("A player %s cannot play twice in a row.");
    }
}
