package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Message entity operations.
 * Provides CRUD operations for Message entities and custom query methods.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Retrieves all messages posted by a specific account.
     * 
     * @param accountId The ID of the account that posted the messages
     * @return A list of messages posted by the specified account
     */
    List<Message> findByPostedBy(Integer accountId);
} 
