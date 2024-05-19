package com.kajisaab.ecommerce.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDetails getAuthenticatedUser() {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication object is not null and if the principal is an instance of UserDetails
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            // Cast the principal to UserDetails and return it
            return (UserDetails) authentication.getPrincipal();
        }

        // Return null or throw an exception if the user is not authenticated
        return null;
    }

}
