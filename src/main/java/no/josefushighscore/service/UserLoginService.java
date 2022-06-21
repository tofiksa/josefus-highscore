package no.josefushighscore.service;

import no.josefushighscore.dto.UserDto;
import no.josefushighscore.register.UserRegister;
import no.josefushighscore.security.jwt.JwtTokenProvider;
import no.josefushighscore.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserLoginService {
    @Autowired
    UserRegister users;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;


    public APIResponse login(UserDto loginRequestDTO) {

        APIResponse apiResponse = new APIResponse();

        String username = loginRequestDTO.getUsername();
        String pwd = loginRequestDTO.getPassword();

        String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        apiResponse.setData(model);

        return apiResponse;
    }
}
