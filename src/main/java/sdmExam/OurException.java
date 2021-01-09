package sdmExam;

public class OurException extends Exception {

       public OurException(String error){
           super(error);
        }

    public OurException(String error, Position position){
        super(error,position);
    }

    }

