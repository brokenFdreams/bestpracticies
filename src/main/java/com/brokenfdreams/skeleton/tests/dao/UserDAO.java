package com.brokenfdreams.skeleton.tests.dao;

import com.brokenfdreams.skeleton.tests.dto.UpdateUserDTO;
import com.brokenfdreams.skeleton.tests.dto.UserDTO;
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
