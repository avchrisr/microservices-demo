package com.chrisr.userdashboard.controller;

import com.chrisr.userdashboard.controller.data.User;
import com.chrisr.userdashboard.request.SignUpRequest;
import com.chrisr.userdashboard.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserRestControllerImpl implements UserRestController {

    private final UserServiceProxy userServiceProxy;

    @Autowired
    public UserRestControllerImpl(UserServiceProxy userServiceProxy) {
        this.userServiceProxy = userServiceProxy;
    }

    @Override
    public ResponseEntity<List<User>> findAllUsers() {
        return userServiceProxy.findAllUsers();
    }

    @Override
    public ResponseEntity<User> findUserById(long id) {
        return userServiceProxy.findUserById(id);
    }

    @Override
    public ResponseEntity<ApiResponse> registerUser(@Valid SignUpRequest signUpRequest) {
        return userServiceProxy.registerUser(signUpRequest);
    }

    @Override
    public ResponseEntity<byte[]> downloadPDF() {
        return userServiceProxy.downloadPDF();
    }
}
