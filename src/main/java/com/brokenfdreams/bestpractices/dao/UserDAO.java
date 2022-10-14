package com.brokenfdreams.bestpractices.dao;

import com.brokenfdreams.bestpractices.dto.UpdateUserDTO;
import com.brokenfdreams.bestpractices.dto.UserDTO;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserDAO {

    @NonNull
    List<UserDTO> findAll();

    @NonNull
    UserDTO getUserById(long userId);

    @NonNull
    UserDTO createUserAndReturn(@NonNull UpdateUserDTO updateUserDTO);

    boolean updateUser(long userId, @NonNull UpdateUserDTO updateUserDTO);

    boolean deleteUser(long userId);
}
