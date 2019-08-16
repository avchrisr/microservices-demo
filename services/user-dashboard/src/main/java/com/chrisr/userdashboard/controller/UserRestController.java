package com.chrisr.userdashboard.controller;

import com.chrisr.userdashboard.controller.data.User;
import com.chrisr.userdashboard.request.SignUpRequest;
import com.chrisr.userdashboard.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// *** CAVEAT: paths cannot conflict and cannot be the same as FeignClient (UserServiceProxy)
// but usually will not be an issue because of the unique context-path
@RequestMapping("/users")
public interface UserRestController {

    // not specifying path will allow both "" and "/" paths
    // i.e.) specifying "/" will only allow "/" path, and not ""
    @GetMapping
    ResponseEntity<List<User>> findUsers(@RequestParam(required = false) String username, @RequestParam(required = false, defaultValue = "false") String isAuth);

    @GetMapping("/{id}")
    ResponseEntity<User> findUserById(@PathVariable(name = "id") long id);

//    @PostMapping("/register")
//    ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest);

    @GetMapping("/downloadPDF")
    ResponseEntity<byte[]> downloadPDF();
}
