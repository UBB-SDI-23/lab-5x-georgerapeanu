package com.example.app.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.app.exceptions.AppException;
import com.example.app.model.User;
import com.example.app.model.UserProfile;
import com.example.app.repository.UserProfileRepository;
import com.example.app.repository.UserRepository;
import com.example.app.secrets.JWTSecretManager;
import com.example.app.util.JWTUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.Optional;

@Service
@Validated
public class LoginService implements ILoginService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProfileRepository userProfileRepository;


    @Override
    public String register(User user) throws AppException {
        if(user.getPassword().length() < 8) {
            throw new AppException("Password should be at least 8 characters long");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if(userRepository.findById(user.getHandle()).isPresent()) {
            throw new AppException("User with such username already exists");
        }
        userRepository.save(user);
        return JWTUtils.getRegisterToken(user.getHandle());
    }

    @Override
    public String login(User user) throws AppException {
        User repoUser = userRepository.findById(user.getHandle()).orElse(null);
        if(repoUser == null) {
            throw new AppException("Username or password invalid");
        }

        if(!BCrypt.checkpw(user.getPassword(), repoUser.getPassword())) {
            throw new AppException("Username or password invalid");
        }

        return JWTUtils.getLoginToken(user.getHandle());
    }

    @Override
    public void activateUser(String token) throws AppException {
        DecodedJWT decodedJWT = JWTUtils.decodeRegisterToken(token);
        if(decodedJWT.getExpiresAt().before(new Date())) {
            userRepository.deleteById(decodedJWT.getClaim("user_handle").asString());
            throw new AppException("Token is expired");
        }
        Optional<User> user = userRepository.findById(decodedJWT.getClaim("user_handle").asString());
        if(user.isEmpty()){
            throw new AppException("User not found");
        }
        UserProfile userProfile = new UserProfile(
                "name",
                decodedJWT.getClaim("user_handle").asString(),
                "email",
                new java.sql.Date(new Date().getTime()),
                new java.sql.Date(new Date().getTime()),
                user.get()
        );
        user.get().setUserProfile(userProfile);
        user.get().setActivated(true);
        userProfileRepository.save(userProfile);
    }
}
