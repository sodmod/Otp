package com.memmcol.Otp.Services;

import com.memmcol.Otp.Entity.CustomerEntity;
import com.memmcol.Otp.Entity.EmailService;
import com.memmcol.Otp.Entity.Messages;
import com.memmcol.Otp.Entity.Models;
import com.memmcol.Otp.Repository.CustomerRepository;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Service
@Component
public class CustomerServices {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServices.class);
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    Messages messages;
    @Autowired
    EmailService emailService;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    CustomerEntity customerEntity;

    private int getOtp(String email) {
        Integer OTP = customerRepository.findByEmail1(email);
        System.out.println(OTP);
        return OTP;
    }

    private boolean getStatus(String email) {
        return customerRepository.findByStatus(email);
    }

    private int generateOtp() {
        return (int) (Math.random() * 9000) + 1000;
    }

    private Optional<CustomerEntity> getEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    private void setConfirmedAt(String email) {
        LocalDateTime localDateTime = LocalDateTime.now();
        customerRepository.findByConfirmedAt(localDateTime, email);

    }

    public String save(@NotNull Models models) {
        int Otp = generateOtp();
        boolean userExist = customerRepository.findByEmail(models.getEmail()).isPresent();
        if (userExist) {
            logger.error("{} already exist", models.getEmail());
            return "email is already registered";
        } else {
            CustomerEntity customer = new CustomerEntity(
                    models.getEmail(),
                    Otp, LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(5),
                    false
            );
            emailService.send(models.getEmail(), from, messages.OtpMessage(Otp));
            customerRepository.save(customer);
            logger.info("all you data are stored succefully{}", customer);
            return "Successfully";
        }
    }

    public String resendOtp(@NotNull Models models) {
        int Otp = generateOtp();
        boolean status = getStatus(models.getEmail());
        if (!status) {
            customerRepository.updateOtp(Otp, models.getEmail());
            emailService.send(models.getEmail(), from, messages.OtpMessage(Otp));
            return "Otp resent successfully";
        } else {
            return "your mail is already confirmed";
        }
    }

    @Transactional
    public String confirmOtp(@NotNull Models models) {
        CustomerEntity customer = getEmail(models.getEmail()).orElseThrow(() -> new IllegalStateException("email not found"));
        if (customer.getConfirmedAt() == null) {
            LocalDateTime expiredAt = customer.getExpiresAt();

            if (expiredAt.isBefore(LocalDateTime.now())){
                return "Otp expired";
            }

            setConfirmedAt(models.getEmail());
            customerEntity.setEmail(models.getEmail());
            customerEntity.setOtp(models.getOtp());

            boolean status = getStatus(models.getEmail());
            int otp = getOtp(models.getEmail());

            System.out.println("This is the value of otp " + otp);
            if (otp == customerEntity.getOtp() && !status) {
                customerRepository.findByEmail(customerEntity.getEmail(), customerEntity.getOtp());
                return "Okay";
            } else if (otp != customerEntity.getOtp() && !status) {
                return "you otp is invalid";
            } else if (otp == customerEntity.getOtp() && status) {
                return "you otp is already validated";
            } else {
                return "ahhh watin you enter";
            }
        } else {
            return "otp already confirmed";
        }

    }
}
