package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Account entity operations.
 * Provides CRUD operations for Account entities and custom query methods.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * Finds an account by its username.
     * 
     * @param username The username to search for
     * @return The account with the matching username, or null if not found
     */
    Account findByUsername(String username);
} 
