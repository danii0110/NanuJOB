package org.baekya_be.Controller;

import lombok.RequiredArgsConstructor;
import org.baekya_be.Domain.User;
import org.baekya_be.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getUsers() throws Exception {
        List<User> list = userService.getUsers();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/delete-user/{user_id}")
    public void deleteUser(@PathVariable String user_id) throws Exception {
        userService.deleteUser(user_id);
    }

    @GetMapping("/find-user/{id}")
    public ResponseEntity<User> findUser(@PathVariable String id) throws Exception {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody User user) throws Exception {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }

    // for GitHub test
}