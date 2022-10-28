package com.example.appointback.misc;

public class DuplicateException extends ArrayIndexOutOfBoundsException{
    public DuplicateException() {
        super("there is more than one such appointments");
    }
}
