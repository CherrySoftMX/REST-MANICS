package com.manics.rest.rest;

import com.manics.rest.mappers.UserMapper;
import com.manics.rest.model.User;
import com.manics.rest.model.request.UserRequest;
import com.manics.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRest {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserRest(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUser());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Integer userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UserRequest request) {
        User user = userService.updateUser(id, userMapper.userRequestToUser(request));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer id) {
        User user = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }
}
