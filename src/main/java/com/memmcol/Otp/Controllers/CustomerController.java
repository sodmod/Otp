package com.memmcol.Otp.Controllers;

import com.memmcol.Otp.Entity.Models;
import com.memmcol.Otp.Services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "send")
public class CustomerController {
    @Autowired
    CustomerServices customerServices;
    @PostMapping("/send")
    public String SENDOtp(@RequestBody Models models){
        return customerServices.save(models);
    }
    @PostMapping("/resendOtp")
    public String reSendOtp(@RequestBody Models models){
        return customerServices.resendOtp(models);
    }
    @PostMapping("/confirm")
    public String confirmOtp(@RequestBody Models models){
        return customerServices.confirmOtp(models);
    }
}
