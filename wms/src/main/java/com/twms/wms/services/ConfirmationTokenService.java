package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.email.EmailLayout;
import com.twms.wms.email.EmailService;
import com.twms.wms.entities.ConfirmationToken;
import com.twms.wms.entities.User;
import com.twms.wms.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    EmailService emailSender;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getConfirmationToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(
                ()->new EntityNotFoundException("Token don't exist")
        );
        return confirmationToken;
    }

    public void createTokenAndSendEmail(User user, boolean isNewUser){
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                user
        );
        String emailbody = isNewUser ? EmailLayout.buildNewUserEmail(new UserDTO(user), token) :
                EmailLayout.buildPasswordRecoverEmail(new UserDTO(user), token);
        emailSender.send(user.getEmail(), emailbody);
        this.saveConfirmationToken(confirmationToken);
    }
}
