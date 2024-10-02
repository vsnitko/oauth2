package com.vsnitko.oauth2.web;

import static com.vsnitko.oauth2.web.Constants.ADMIN_ACCESS_PATH;
import static com.vsnitko.oauth2.web.Constants.AUTHENTICATED_ACCESS_PATH;
import static com.vsnitko.oauth2.web.Constants.CHECK_ACCESS_PATH;
import static com.vsnitko.oauth2.web.Constants.NO_RIGHTS_MESSAGE;
import static com.vsnitko.oauth2.web.Constants.PUBLIC_ACCESS_PATH;
import static com.vsnitko.oauth2.web.Constants.VERIFIED_ACCESS_PATH;

import com.vsnitko.oauth2.model.entity.Role;
import com.vsnitko.oauth2.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to check permissions of users. Mostly for test purposes
 *
 * @author v.snitko
 * @since 2022.12.17
 */
@Slf4j
@RestController
@RequestMapping(CHECK_ACCESS_PATH)
@RequiredArgsConstructor
public class AccessController {

    @GetMapping(PUBLIC_ACCESS_PATH)
    public String publicAccess() {
        return "Content available to everyone";
    }

    @GetMapping(AUTHENTICATED_ACCESS_PATH)
    public String authenticatedAccess(@AuthenticationPrincipal User principal) {
        if (principal != null) {
            return "Content available to all authenticated users";
        }

        return NO_RIGHTS_MESSAGE;
    }

    @GetMapping(VERIFIED_ACCESS_PATH)
    public String verifiedAccess(@AuthenticationPrincipal User principal) {
        if (principal != null && principal.getEmailVerified()) {
            return "Content available to all users that verified their emails or were signed-in with oauth2";
        }
        return NO_RIGHTS_MESSAGE;
    }

    @GetMapping(ADMIN_ACCESS_PATH)
    public String adminAccess(@AuthenticationPrincipal User principal) {
        if (principal != null && principal.getRole() == Role.ADMIN) {
            return "Content available to admins";
        }
        return NO_RIGHTS_MESSAGE;
    }
}
