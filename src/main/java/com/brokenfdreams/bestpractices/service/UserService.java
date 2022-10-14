package com.brokenfdreams.bestpractices.service;

import com.brokenfdreams.bestpractices.dto.UpdateUserDTO;
import com.brokenfdreams.bestpractices.dto.UserDTO;
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
