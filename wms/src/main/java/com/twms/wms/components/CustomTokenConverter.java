package com.twms.wms.components;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenConverter extends JwtAccessTokenConverter{
    @Autowired
    private UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        UserDTO user = userService.getUserByUsername(authentication.getName());

        Map<String, Object> map = new HashMap<>();
        map.put("user_id",user.getId());
        if(user.getBranch() == null)
            map.put("user_branch_id", null);
        else
            map.put("user_branch_id",user.getBranch().getId());

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(map);

        accessToken = super.enhance(accessToken, authentication);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(new HashMap<>());

        return accessToken;
    }
}

