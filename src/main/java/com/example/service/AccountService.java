package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * Registers a new user account after validating the input data.
     * Validates that username is not empty and password is at least 4 characters.
     * Checks for username uniqueness in the database.
     * 
     * @param account The account information to register
     * @return The registered account with generated ID, null if validation fails,
     *         or account with ID=-1 if username already exists
     */
    public Account registerAccount(Account account) {
        // Validate username and password
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return null;
        }
        
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        
        // Check if username already exists
        Optional<Account> existingAccount = Optional.ofNullable(accountRepository.findByUsername(account.getUsername()));
        if (existingAccount.isPresent()) {
            // Special case for conflict
            account.setAccountId(-1); // Use -1 to signal duplicate username
            return account;
        }
        
        return accountRepository.save(account);
    }
    
    /**
     * Authenticates a user by verifying username and password.
     * 
     * @param account The login credentials (username and password)
     * @return Optional containing the authenticated account if credentials are valid, empty otherwise
     */
    public Optional<Account> login(Account account) {
        Optional<Account> existingAccount = Optional.ofNullable(accountRepository.findByUsername(account.getUsername()));
        
        return existingAccount.filter(acc -> acc.getPassword().equals(account.getPassword()));
    }
    
    /**
     * Retrieves an account by its ID.
     * 
     * @param accountId The ID of the account to retrieve
     * @return Optional containing the account with the specified ID, or empty if not found
     */
    public Optional<Account> getAccountById(Integer accountId) {
        return accountRepository.findById(accountId);
    }
} 
