package com.twms.wms.components;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer extends JwtAccessTokenConverter implements TokenEnhancer {
    @Autowired
    private UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        UserDTO user = userService.getUserByUsername(authentication.getName());

        Map<String, Object> map = new HashMap<>();
        map.put("user_id",user.getId());

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(map);

//        String encoded = super.encode(accessToken, authentication);

//        ((DefaultOAuth2AccessToken) accessToken).setValue(encoded);

        return accessToken;
    }
}