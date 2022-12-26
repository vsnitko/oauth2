package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.payload.EditRequest;

/**
 * @author v.snitko
 * @since 2022.12.26
 */
public interface UserService {

    User getById(final Long id);

    User save(User user);

    User edit(EditRequest editRequest, User principal);
}
