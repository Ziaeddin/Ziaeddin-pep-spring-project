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

     /**
     * Creates a new message.
     * Sets the current time if not provided.
     * 
     * @param message The message to create
     * @return ResponseEntity containing the created message with generated ID
     * @throws InvalidMessageException if message data is invalid
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        // Set current time if not already set
        if (message.getTimePostedEpoch() == null) {
            message.setTimePostedEpoch(Instant.now().getEpochSecond());
        }
        
        return messageService.createMessage(message)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new InvalidMessageException("Invalid message data"));
    }
    
    /**
     * Retrieves all messages in the system.
     * 
     * @return ResponseEntity containing a list of all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    
    /**
     * Retrieves a specific message by its ID.
     * 
     * @param messageId The ID of the message to retrieve
     * @return ResponseEntity containing the message , always 200
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        return messageService.getMessageById(messageId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.ok().build());
    }
    
    /**
     * Deletes a message by its ID.
     * 
     * @param messageId The ID of the message to delete
     * @return ResponseEntity containing a success indicator and the number of rows updated (1) or empty in body
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        if (deleted) {
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.ok().build();
    }
    
    /**
     * Updates the text of an existing message.
     * 
     * @param messageId The ID of the message to update
     * @param updates the new message text
     * @return ResponseEntity containing the number of rows updated
     * @throws InvalidMessageException if message text is missing or update fails
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message updates) {
        
        if (updates.getMessageText() == null) {
            throw new InvalidMessageException("Message text is required");
        }
        
        return messageService.updateMessageText(messageId, updates.getMessageText())
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new InvalidMessageException("Failed to update message"));
    }
    
    /**
     * Retrieves all messages posted by a specific account.
     * 
     * @param accountId The ID of the account whose messages to retrieve
     * @return ResponseEntity containing list of messages posted by the specified account
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByAccount(accountId);
        return ResponseEntity.ok(messages);
    }
}
