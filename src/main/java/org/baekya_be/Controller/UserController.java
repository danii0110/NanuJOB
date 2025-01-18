package org.baekya_be.Controller;

import lombok.RequiredArgsConstructor;
import org.baekya_be.Domain.User;
import org.baekya_be.Dto.LoginRequest;
import org.baekya_be.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) throws Exception {
        // 입력 검증
        if (loginRequest.getLoginId() == null || loginRequest.getLoginId().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Login ID is required");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        User existingUser = userService.findUserByLoginId(loginRequest.getLoginId());

        if (existingUser == null) {
            System.out.println("User not found for loginId: " + loginRequest.getLoginId());
            return ResponseEntity.status(404).body("User not found");
        }

        if (!existingUser.getPassword().equals(loginRequest.getPassword())) {
            System.out.println("Invalid password for user: " + loginRequest.getLoginId());
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // 세션에 사용자 정보 저장
        session.setAttribute("user", existingUser);
        System.out.println("Login successful for user: " + existingUser.getName());

        return ResponseEntity.ok("Login successful for user: " + existingUser.getName());
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("User logged out successfully");
    }
}