package com.chrisr.userservice.controller;

import com.chrisr.userservice.exception.AppException;
import com.chrisr.userservice.repository.entity.User;
import com.chrisr.userservice.request.SignUpRequest;
import com.chrisr.userservice.response.ApiResponse;
import com.chrisr.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class UserRestControllerImpl implements UserRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRestControllerImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public UserRestControllerImpl(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @Override
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        User user = userService.registerUser(signUpRequest);
        return ResponseEntity.ok().body(new ApiResponse(true, "User (" + user.getUsername() + ") registered successfully."));

//		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}").buildAndExpand(result.getUsername()).toUri();
//		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            removeSensitiveInfoFromUser(user);
        }
        return ResponseEntity.ok().body(users);
    }

    @Override
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") long id) {
        User user = userService.getUserById(id);
        removeSensitiveInfoFromUser(user);
        return ResponseEntity.ok().body(user);
    }

    @Override
    public ResponseEntity<User> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return ResponseEntity.ok().body(user);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(name = "id") long id) {
        userService.deleteUserById(id);
        String responseMessage = String.format("User with id = %s has been deleted.", id);
        return ResponseEntity.ok().body(new ApiResponse(true, responseMessage));
    }

    @Override
    public ResponseEntity<byte[]> downloadPDF() {
        String filename = "public/scrum.pdf";
        try (
                InputStream inputStream = new FileInputStream(filename);
//				OutputStream outputStream = new FileOutputStream(outputFile);
        ) {
            long fileSize = new File(filename).length();
            byte[] pdfContent = new byte[(int) fileSize];
            inputStream.read(pdfContent);                   // ***  this line is necessary
            return ResponseEntity.ok().body(pdfContent);
        } catch (IOException ex) {
            logger.error("Error occurred while reading PDF file...", ex);
            throw new AppException("Error occurred while reading PDF file. " + ex.getMessage(), ex);
        }
    }

    private void removeSensitiveInfoFromUser(User user) {
        user.setPassword(null);
    }

}
