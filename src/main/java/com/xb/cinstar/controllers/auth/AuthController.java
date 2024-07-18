package com.xb.cinstar.controllers.auth;

import com.xb.cinstar.exception.TokenRefreshException;
import com.xb.cinstar.models.ERole;
import com.xb.cinstar.models.RefreshToken;
import com.xb.cinstar.models.RoleModel;
import com.xb.cinstar.models.UserModel;
import com.xb.cinstar.payload.request.LoginRequest;
import com.xb.cinstar.payload.request.SignupRequest;
import com.xb.cinstar.payload.request.TokenRefreshRequest;
import com.xb.cinstar.payload.response.JwtResponse;
import com.xb.cinstar.payload.response.MessageResponse;
import com.xb.cinstar.payload.response.TokenRefreshResponse;
import com.xb.cinstar.repository.IRoleRepository;
import com.xb.cinstar.repository.IUserRepository;
import com.xb.cinstar.service.RefreshTokenService;
import com.xb.cinstar.service.UserDetailsImpl;
import com.xb.cinstar.service.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/hello")
    public  String hello()
    {
        return  "hello";
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,  userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles,refreshToken.getToken()));


    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Phonenumber is already taken!"));
        }

        if (userRepository.existsByCic(signUpRequest.getCic())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Cic is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserModel user = new UserModel(signUpRequest.getFullName(), signUpRequest.getBirthDay(), signUpRequest.getPhoneNumber(),
                signUpRequest.getCic(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),signUpRequest.getUserName());


        Set<RoleModel> roles = new HashSet<>();


            RoleModel userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request)
    {
        return refreshTokenService.findByRefreshToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserModel)
                .map(userModel -> {
                    String token = jwtUtils.generateTokenFromUsername(userModel.getUserName());
                    return  ResponseEntity.ok(new TokenRefreshResponse(token, request.getRefreshToken()));

                })
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(),
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
