package com.vsnitko.oauth2.model.payload;

import lombok.Builder;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
@Data
@Builder
public class UserResponse {

  private Long id;
  private String name;
  private String email;
  private String avatar;
}
