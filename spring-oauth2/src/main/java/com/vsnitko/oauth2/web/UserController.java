package com.vsnitko.oauth2.web;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.payload.EditRequest;
import com.vsnitko.oauth2.model.payload.UserResponse;
import com.vsnitko.oauth2.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import static com.vsnitko.oauth2.web.Constants.EDIT_USER_PATH;
import static com.vsnitko.oauth2.web.Constants.USER_INFO_PATH;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
@RestController
@RequestMapping(USER_INFO_PATH)
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final ObjectMapper objectMapper;
    
    @GetMapping("/exists")
    public ResponseEntity<Boolean> signIn(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmail(email).isPresent());
    }
    
    @GetMapping
    public UserResponse getPrincipal(@AuthenticationPrincipal User principal) {
        if (principal == null) {
            return null;
        }
        return UserResponse.builder()
                .id(principal.getId())
                .username(principal.getName())
                .email(principal.getEmail())
                .avatar(principal.getAvatar())
                .build();
    }

    @PostMapping(EDIT_USER_PATH)
    public UserResponse edit(@RequestBody EditRequest editRequest, @AuthenticationPrincipal User principal) {
        final User editedUser = userService.edit(editRequest, principal);
        return objectMapper.convertValue(editedUser, UserResponse.class);
    }
}
