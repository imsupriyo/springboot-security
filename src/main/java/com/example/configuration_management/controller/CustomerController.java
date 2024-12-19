package com.example.configuration_management.controller;

import com.example.configuration_management.DTO.LoginRequestDTO;
import com.example.configuration_management.DTO.LoginResponseDTO;
import com.example.configuration_management.entity.Customer;
import com.example.configuration_management.repository.CustomerRepository;
import com.example.configuration_management.security.ApplicationConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final Environment env;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        String hashPwd = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashPwd);
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
    }

    @GetMapping("/user")
    public ResponseEntity<String> loginCustomer() {
        return ResponseEntity.status(HttpStatus.OK).body("Please check \"Authorization\" field in the header");
    }

    @PostMapping("/loginAPI")
    public ResponseEntity<LoginResponseDTO> loginAPI(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.username(), loginRequestDTO.password());
        Authentication authenticationResponse = authenticationProvider.authenticate(authentication);
        String jwt = null;
        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            if (env != null) {
                String secret = env.getProperty(ApplicationConstant.JWT_SECRET_KEY, ApplicationConstant.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder()
                        .issuer("demo app")
                        .subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime() + (5 * 60 * 60 * 1000))) // Expires after 5 hour
                        .signWith(secretKey)
                        .compact();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstant.JWT_HEADER, jwt).body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }
}
