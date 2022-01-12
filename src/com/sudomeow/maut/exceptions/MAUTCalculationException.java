package com.sudomeow.maut.exceptions;

public class MAUTCalculationException extends Exception{

    public MAUTCalculationException(){
        super("An exception has occurred while trying to calculate the MAUT value");
    }

    public MAUTCalculationException(String msg){
        super(msg);
    }

}
