package com.example.kampustesttask.util;

import com.example.kampustesttask.dto.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class UtAuthorization {

    private static UserPrincipal getUserPrincipal() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        return getUserPrincipal().getId();
    }

    public static String getUserEmail() {
        return getUserPrincipal().getEmail();
    }

}
