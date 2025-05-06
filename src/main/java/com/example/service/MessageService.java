package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.Instant;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private AccountService accountService;

     /**
     * Creates a new message after validating the input data.
     * Validates message text (not empty, max 255 chars) and verifies the poster exists.
     * Sets the current time if not provided.
     * 
     * @param message The message to create
     * @return Optional containing the created message with generated ID, or empty if validation fails
     */
    public Optional<Message> createMessage(Message message) {
        // Validate message text
        if (message.getMessageText() == null || 
            message.getMessageText().trim().isEmpty() || 
            message.getMessageText().length() > 255) {
            return Optional.empty();
        }
        
        // Validate poster exists
        Integer postedById = message.getPostedBy();
        if (postedById == null) {
            return Optional.empty();
        }
        
        Optional<Account> existingAccount = accountService.getAccountById(postedById);
        if (existingAccount.isEmpty()) {
            return Optional.empty();
        }
        
        // Set current time if not provided
        if (message.getTimePostedEpoch() == null) {
            message.setTimePostedEpoch(Instant.now().getEpochSecond());
        }
        
        return Optional.of(messageRepository.save(message));
    }
    
    /**
     * Retrieves all messages in the system.
     * 
     * @return A list of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * Retrieves a specific message by its ID.
     * 
     * @param messageId The ID of the message to retrieve
     * @return Optional containing the message with the specified ID, or empty if not found
     */
    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }
    
    /**
     * Deletes a message by its ID.
     * 
     * @param messageId The ID of the message to delete
     * @return true if the message was successfully deleted, false if the message was not found
     */
    public boolean deleteMessage(Integer messageId) {
        Optional<Message> messageOpt = messageRepository.findById(messageId);
        if (messageOpt.isPresent()) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }
    
    /**
     * Updates the text of an existing message.
     * Validates the new message text (not empty, max 255 chars).
     * 
     * @param messageId The ID of the message to update
     * @param newMessageText The new text for the message
     * @return Optional containing the number of rows updated (1) if successful, or empty if validation fails or message not found
     */
    public Optional<Integer> updateMessageText(Integer messageId, String newMessageText) {
        // Validate new message text
        if (newMessageText == null || newMessageText.trim().isEmpty() || newMessageText.length() > 255) {
            return Optional.empty();
        }
        
        return messageRepository.findById(messageId)
            .map(message -> {
                message.setMessageText(newMessageText);
                messageRepository.save(message);
                return 1;
            });
    }
    
    /**
     * Retrieves all messages posted by a specific account.
     * Verifies that the account exists before retrieving messages.
     * 
     * @param accountId The ID of the account whose messages to retrieve
     * @return A list of messages posted by the specified account, or empty list if account not found
     */
    public List<Message> getMessagesByAccount(Integer accountId) {
        return accountService.getAccountById(accountId)
            .map(account -> messageRepository.findByPostedBy(accountId))
            .orElse(List.of()); // Return empty list if account doesn't exist
    }
}
