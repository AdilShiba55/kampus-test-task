package com.example.kampustesttask.exception;

public class RecordNotFoundException extends CustomException {

    public RecordNotFoundException() {
        super("Запись не найдена");
    }
}
