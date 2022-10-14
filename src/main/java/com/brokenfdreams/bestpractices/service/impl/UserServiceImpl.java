package com.brokenfdreams.bestpractices.service.impl;

import com.brokenfdreams.bestpractices.dao.UserDAO;
import com.brokenfdreams.bestpractices.dto.UpdateUserDTO;
import com.brokenfdreams.bestpractices.dto.UserDTO;
import com.brokenfdreams.bestpractices.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @NonNull
    private final UserDAO userDAO;

    public UserServiceImpl(@NonNull UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @NonNull
    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll();
    }

    @NonNull
    @Override
    public UserDTO getUserById(long userId) {
        return userDAO.getUserById(userId);
    }

    @NonNull
    @Override
    public UserDTO createUser(@NonNull UpdateUserDTO updateUserDTO) {
        return userDAO.createUserAndReturn(updateUserDTO);
    }

    @NonNull
    @Override
    public UserDTO updateUser(long userId, @NonNull UpdateUserDTO updateUserDTO) {
        if (userDAO.updateUser(userId, updateUserDTO)) {
            return userDAO.getUserById(userId);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public void deleteUser(long userId) {
        if (!userDAO.deleteUser(userId)) {
            throw new RuntimeException("User not found");
        }
    }
}
