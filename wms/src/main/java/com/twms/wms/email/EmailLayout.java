package com.twms.wms.email;

import com.twms.wms.dtos.UserDTO;

public class EmailLayout {

    public static String buildEmail(UserDTO user, String token){
        return
            "    <div class='email-container' style='width: 100%;'>\n" +
            "        <div class='email-header' style='height: 50px;width: 100%;background-color: #E20074;padding: 0 40px;display: flex;justify-content: space-between;'>\n" +
            "            <div class='header-text' style='color: white;font-family: Arial, Helvetica, sans-serif;font-size: 32px;font-weight: 600;line-height: 50px;vertical-align: middle;'>Confirm your email</div>\n" +
            "            <div class='header-logo' style='color: white;font-size: 40px;font-weight: bold;white-space: nowrap;'>T-WMS</div>\n" +
            "        </div>\n" +
            "        <div class='email-body' style='font-size: 20px;color: black;padding: 16px 28px;'>\n" +
            "            <p>Hi " + user.getUsername() + ",</p>\n" +
            "            <p>You're email have been registered on <a href='http://localhost:4200' target='_blank'>T-wms</a> platform.</p>\n" +
            "                <p> Confirm your email by clicking on the link below.</p>\n" +
            "            <p class='email-link' style='margin: 30px;height: 40px;cursor: pointer;width: fit-content;background-color: #E20074;line-height: 40px;'>\n" +
        "                    <a href='http://localhost:4200/confirmation?token="+ token + "' style='padding: 8px 40px;font-size: 24px;text-decoration: none;color: white;width: 100%;'>Confirm Email</a></p>\n" +
            "        </div>\n" +
            "    </div>\n";
    }
}
