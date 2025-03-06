package com.clients;

import com.dtos.LoginRequestDTO;
import com.dtos.LoginResponseDTO;
import com.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface AuthClient {

    @PostMapping("/api/auth/login")
    LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest);

    @PostMapping("/api/auth/register")
    UserDTO register(@RequestBody UserDTO userDTO);
}