package com.twms.wms.services;

import com.twms.wms.email.EmailSender;
import com.twms.wms.email.EmailService;
import com.twms.wms.entities.ConfirmationToken;
import com.twms.wms.entities.User;
import com.twms.wms.repositories.ConfirmationTokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ConfirmationTokenServiceTest {

    @InjectMocks
    @Spy
    private ConfirmationTokenService service;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private EmailService emailSender;

    @Test
    public void shouldReturnNothingAfterSave(){
        ConfirmationToken confirmationToken = new ConfirmationToken();
        Assertions.assertDoesNotThrow(() -> service.saveConfirmationToken(confirmationToken));
        Mockito.verify(confirmationTokenRepository, Mockito.times(1)).save(confirmationToken);
    }

    @Test
    public void shouldReturnConfirmationTokenAfterGetAndExisting(){
        Mockito.when(confirmationTokenRepository.findByToken(any())).thenReturn(Optional.of(new ConfirmationToken()));
        Assertions.assertDoesNotThrow(() -> service.getConfirmationToken("token"));
    }

    @Test
    public void shouldThrowExceptionWhenTheresNoSuchToken(){
        Mockito.when(confirmationTokenRepository.findByToken(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()-> service.getConfirmationToken("token"));
    }

    @Test
    public void shouldReturnNothingAfterCreatingAndSendingEmail(){
        Mockito.doNothing().when(emailSender).send(any(),any());
        Mockito.doNothing().when(service).saveConfirmationToken(any());
        Assertions.assertDoesNotThrow(()->service.createTokenAndSendEmail(new User(), true));
    }

}
