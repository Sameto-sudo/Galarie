package org.projekt;

public class GalerieException extends RuntimeException{

    public GalerieException(String message){
        super(message);

    }

    public GalerieException(String message, Throwable cause) {
        super(message, cause);
    }

}
