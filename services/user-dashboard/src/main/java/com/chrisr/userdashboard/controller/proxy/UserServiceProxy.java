package com.chrisr.userdashboard.controller.proxy;

import com.chrisr.userdashboard.controller.data.User;
import com.chrisr.userdashboard.request.SignUpRequest;
import com.chrisr.userdashboard.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// @FeignClient(name = "xxx") is used to fetch the URL from Service Registry (e.g. Eureka).
// It should be the registered application name in Service Registry
// CAVEAT: needs @EnableFeignClients in the Application starter, which will allow this FeignClient interface to be @Autowired
@FeignClient(name = "UserService")
@RequestMapping("/user-service/users")       // *** CAVEAT: still need to specify the context path ("/user-service"), as Service Registry does not seem to be aware of context path
public interface UserServiceProxy {

    @GetMapping
    ResponseEntity<List<User>> findUsers(@RequestParam(required = false) String username, @RequestParam(required = false, defaultValue = "false") String isAuth);

    @GetMapping("/{id}")
    ResponseEntity<User> findUserById(@PathVariable(name = "id") long id);

//    @PostMapping("/register")
//    ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest);

    @GetMapping("/downloadPDF")
    ResponseEntity<byte[]> downloadPDF();
}
