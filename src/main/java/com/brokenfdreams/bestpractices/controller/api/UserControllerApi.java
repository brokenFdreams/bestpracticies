package com.brokenfdreams.bestpractices.controller.api;

import com.brokenfdreams.bestpractices.dto.UpdateUserDTO;
import com.brokenfdreams.bestpractices.dto.UserDTO;
import com.brokenfdreams.bestpractices.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "User Controller Api")
@RequestMapping("api")
public class UserControllerApi {

    @NonNull
    private final UserService userService;

    public UserControllerApi(@NonNull UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("Get user by id")
    @GetMapping("users/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@ApiParam("user id") @PathVariable("userId") long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @ApiOperation("Update user by user id")
    @PutMapping("users/user/{userId}")
    public ResponseEntity<UserDTO> updateUser(@ApiParam("user id") @PathVariable("userId") long userId,
                                              @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, updateUserDTO));
    }
}
