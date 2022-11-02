package com.brokenfdreams.skeleton.tests.service;

import com.brokenfdreams.skeleton.tests.dto.UpdateUserDTO;
import com.brokenfdreams.skeleton.tests.dto.UserDTO;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {
    @NonNull
    List<UserDTO> getAllUsers();

    @NonNull
    UserDTO getUserById(long userId);

    @NonNull
    UserDTO createUser(@NonNull UpdateUserDTO updateUserDTO);

    @NonNull
    UserDTO updateUser(long userId, @NonNull UpdateUserDTO updateUserDTO);

    void deleteUser(long userId);
}
