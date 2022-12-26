package com.vsnitko.oauth2.web;

import com.vsnitko.oauth2.model.entity.Role;
import com.vsnitko.oauth2.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author v.snitko
 * @since 2022.12.17
 */
@RestController
@RequestMapping("/check-access")
@RequiredArgsConstructor
public class AccessController {

    public static final String NO_RIGHTS_MESSAGE = "You do not have rights to view this content";

    @GetMapping("/public")
    public String publicAccess() {
        return "Content available to everyone";
    }

    @GetMapping("/authenticated")
    public String authenticatedAccess(Principal principal) {
        if (principal != null) {
            return "Content available to all authenticated users";
        }
        return NO_RIGHTS_MESSAGE;
    }

    @GetMapping("/verified")
    public String verifiedAccess(@AuthenticationPrincipal User principal) {
        if (principal != null && principal.getEmailVerified()) {
            return "Content available to all users that verified their emails or were signed-in with oauth2";
        }
        return NO_RIGHTS_MESSAGE;
    }

    @GetMapping("/admin")
    public String adminAccess(@AuthenticationPrincipal User principal) {
        if (principal != null && principal.getRole() == Role.ADMIN) {
            return "Content available to admins";
        }
        return NO_RIGHTS_MESSAGE;
    }
}
