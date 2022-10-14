package com.brokenfdreams.bestpractices.controller.web;

import com.brokenfdreams.bestpractices.dto.UpdateUserDTO;
import com.brokenfdreams.bestpractices.dto.UserDTO;
import com.brokenfdreams.bestpractices.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SwaggerDefinition(tags = @Tag(name = "User Controller Web", description = "CRUD controller for user model"))
@Api(tags = "User Controller Web")
@RequestMapping("web")
public class UserControllerWeb {

    @NonNull
    private final UserService userService;

    public UserControllerWeb(@NonNull UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("Get all users")
    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ApiOperation("Get user by id")
    @GetMapping("users/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@ApiParam("user id") @PathVariable("userId") long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @ApiOperation("Create new user")
    @PostMapping("users/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.createUser(updateUserDTO));
    }

    @ApiOperation("Update user by user id")
    @PutMapping("users/user/{userId}")
    public ResponseEntity<UserDTO> updateUser(@ApiParam("user id") @PathVariable("userId") long userId,
                                              @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, updateUserDTO));
    }

    @ApiOperation("Delete user by user id")
    @DeleteMapping("users/user/{userId}")
    public ResponseEntity<Void> deleteUser(@ApiParam("user id") @PathVariable("userId") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
