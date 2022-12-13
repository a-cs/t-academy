package com.twms.wms.email;

import com.twms.wms.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EmailLayout {

    @Autowired
    private Environment env;
    @Value("${PROD_URL:Unknown}")
    private String uri;

    public String buildNewUserEmail(UserDTO user, String token){

        String url = "";
        if (Arrays.asList(env.getActiveProfiles()).contains("test")){
            url = "http://localhost:4200/";
        }else if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            url = "http://localhost:4200/";
        }else if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            url = uri;
        }

        return
            "    <div class='email-container' style='width: 100%;'>\n" +
            "        <div class='email-header' style='height: fit-content;width: 90%;border-radius:32px;margin: 0 auto;background-color: #E20074;padding: 0 40px;'>\n" +
            "            <div class='header-logo' style='text-align: center;color: white;margin-bottom:24px;font-size: 60px;font-weight: bold;white-space: nowrap;'>T-WMS</div>\n" +
            "            <div class='header-text' style='text-align: center; padding-bottom:20px;color: white;font-family: Arial, Helvetica, sans-serif;font-size: 32px;font-weight: 600;line-height: 50px;vertical-align: middle;'>Confirm your email</div>\n" +
            "        </div>\n" +
            "        <div class='email-body' style='font-size: 20px;color: black;padding: 16px 28px;'>\n" +
            "            <p>Hi " + user.getUsername() + ",</p>\n" +
            "            <p>You're email have been registered on <a href='"+url+"' target='_blank'>T-wms</a> platform.</p>\n" +
            "                <p> Set your new password by clicking on the link below.</p>\n" +
            "            <a href='"+url+"confirmation?token="+ token + "' style='border-radius:32px;padding: 12px 48px;font-size: 24px;text-decoration: none;color: white;width: 100%;'>\n" +
            "                    <p class='email-link' style='border-radius:32px;padding:12px 60px;margin: 30px;height: 40px;cursor: pointer;width: fit-content;background-color: #E20074;line-height: 40px;'>Reset password</p></a>\n" +
            "        </div>\n" +
            "    </div>\n";
    }

    public String buildPasswordRecoverEmail(UserDTO user, String token){

        String url = "";
        if (Arrays.asList(env.getActiveProfiles()).contains("test")){
            url = "http://localhost:4200/";
        }else if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            url = "http://localhost:4200/";
        }else if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            url = uri;
        }

        return
                "    <div class='email-container' style='width: 100%;'>\n" +
                        "        <div class='email-header' style='height: fit-content;width: 90%;border-radius:32px;margin: 0 auto;background-color: #E20074;padding: 0 40px;'>\n" +
                        "            <div class='header-logo' style='text-align: center;color: white;margin-bottom:24px;font-size: 60px;font-weight: bold;white-space: nowrap;'>T-WMS</div>\n" +
                        "            <div class='header-text' style='text-align: center; padding-bottom:20px;color: white;font-family: Arial, Helvetica, sans-serif;font-size: 32px;font-weight: 600;line-height: 50px;vertical-align: middle;'>Confirm your email</div>\n" +
                        "        </div>\n" +
                        "        <div class='email-body' style='font-size: 20px;color: black;padding: 16px 28px;'>\n" +
                        "            <p>Hi " + user.getUsername() + ",</p>\n" +
                        "            <p>We've received a password change request for you at <a href='"+url+"' target='_blank'>T-wms</a> platform.</p>\n" +
                        "                <p> Set your new password by clicking on the link below.</p>\n" +
                        "            <a href='"+url+"confirmation?token="+ token + "' style='border-radius:32px;padding: 12px 48px;font-size: 24px;text-decoration: none;color: white;width: 100%;'>\n" +
                        "                    <p class='email-link' style='border-radius:32px;padding:12px 60px;margin: 30px;height: 40px;cursor: pointer;width: fit-content;background-color: #E20074;line-height: 40px;'>Set new password</p></a>\n" +
                        "        </div>\n" +
                        "    </div>\n";
    }
}
