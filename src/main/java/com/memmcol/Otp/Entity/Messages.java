package com.memmcol.Otp.Entity;

import org.springframework.stereotype.Component;

@Component
public class Messages {
    public String OtpMessage(int Otp){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div>\n" +
                "      <h1>Your Otp</h1>\n" +
                "      <p>Dear customer</p>\n" +
                "      <p>Your Otp is "+ Otp + " and it shall expired in the next 60 seconds</p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
    }
}
