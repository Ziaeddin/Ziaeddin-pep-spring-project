package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidAccountException;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.InvalidMessageException;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.time.Instant;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private MessageService messageService;
    
    // Account-related endpoints
    /**
     * Registers a new user account.
     * 
     * @param account The account information to register
     * @return The registered account with generated ID
     * @throws InvalidAccountException if account data is invalid
     * @throws UsernameAlreadyExistsException if username is already taken
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Account register(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        
        if (registeredAccount == null) {
            throw new InvalidAccountException("Invalid account data");
        } else if (registeredAccount.getAccountId() != null && registeredAccount.getAccountId() == -1) {
            // Special case for duplicate username
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        
        return registeredAccount;
    }
    
  /**
     * Authenticates a user and returns the account information.
     * 
     * @param account The login credentials (username and password)
     * @return ResponseEntity containing the authenticated account information
     * @throws InvalidCredentialsException if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        return accountService.login(account)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
    }
    
    // Message-related endpoints
}
