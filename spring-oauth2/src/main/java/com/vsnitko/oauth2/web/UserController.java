package com.vsnitko.oauth2.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.payload.EditRequest;
import com.vsnitko.oauth2.model.payload.UserResponse;
import com.vsnitko.oauth2.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;
  private final ObjectMapper objectMapper;

  @GetMapping
  public UserResponse getPrincipal(@AuthenticationPrincipal User principal) {
    return objectMapper.convertValue(principal, UserResponse.class);
  }

  @PostMapping("/edit")
  public UserResponse edit(@RequestBody EditRequest editRequest, @AuthenticationPrincipal User principal) {
    final User editedUser = userService.edit(editRequest, principal);
    return objectMapper.convertValue(editedUser, UserResponse.class);
  }
}
