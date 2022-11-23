package com.twms.wms.services;

import com.twms.wms.entities.ConfirmationToken;
import com.twms.wms.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ConfirmationTokenService {

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getConfirmationToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(
                ()->new EntityNotFoundException("Token don't exist")
        );
        return confirmationToken;
    }
}
