package com.memmcol.Otp.Entity;

public interface EmailSender {
    void send(String to, String from, String message);
}
