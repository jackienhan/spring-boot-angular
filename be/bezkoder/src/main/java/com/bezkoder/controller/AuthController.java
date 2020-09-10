package com.bezkoder.controller;


import com.bezkoder.configure.jwt.JwtUtils;
import com.bezkoder.enumeration.ERole;
import com.bezkoder.model.Role;
import com.bezkoder.model.User;
import com.bezkoder.payload.request.LoginRequest;
import com.bezkoder.payload.request.SignUpRequest;
import com.bezkoder.payload.response.JwtResponse;
import com.bezkoder.payload.response.MessageResponse;
import com.bezkoder.repository.UserRepository;
import com.bezkoder.service.SessionManagementService;
import com.bezkoder.service.impl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;
//    @Autowired
//    private SessionManagementService sessionManagementService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpSession session) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
//        List<String> info = (List<String>) sessionManagementService.getAttribute("MY_SESSION_MESSAGES");
        logger.info("sign in success: "  + "info");
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        List<Role> roles = new ArrayList<>();
        Role roleInfo;
        if (strRoles == null) {
            roleInfo = new Role();
            roleInfo.setName(ERole.ROLE_USER.getName());
            roleInfo.setUser(user);
            roles.add(roleInfo);
        } else {
            for (String role: strRoles) {
                role = role.toUpperCase();
                switch (role) {
                    case "ADMIN":
                        roleInfo = new Role();
                        roleInfo.setName(ERole.ROLE_ADMIN.getName());
                        roleInfo.setUser(user);
                        roles.add(roleInfo);
                        break;
                    case "MODERATOR":
                        roleInfo = new Role();
                        roleInfo.setName(ERole.ROLE_MODERATOR.getName());
                        roleInfo.setUser(user);
                        roles.add(roleInfo);

                        break;
                    default:
                        roleInfo = new Role();
                        roleInfo.setName(ERole.ROLE_USER.getName());
                        roleInfo.setUser(user);
                        roles.add(roleInfo);
                }
            }
        }

        user.setRoles(roles);
        userRepository.saveAndFlush(user);
//        sessionManagementService.setAttribute("MY_SESSION_MESSAGES", user.getEmail());
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
