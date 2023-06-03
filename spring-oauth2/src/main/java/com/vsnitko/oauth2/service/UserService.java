package com.vsnitko.oauth2.service;

import com.vsnitko.oauth2.model.entity.User;
import com.vsnitko.oauth2.model.payload.EditRequest;

/**
 * Service which performs CRUD operations with application users
 *
 * @author v.snitko
 * @since 2022.12.26
 */
public interface UserService {

    User getById(Long id);

    void save(User user);

    User edit(EditRequest editRequest, User principal);
}
