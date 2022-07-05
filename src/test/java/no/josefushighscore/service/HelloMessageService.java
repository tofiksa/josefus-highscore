package no.josefushighscore.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class HelloMessageService implements MessageService{

    @PreAuthorize("authenticated")
    public String getMessage() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return "Username: " +  authentication.toString() + " Password: " + authentication.getCredentials().toString();
    }
}
