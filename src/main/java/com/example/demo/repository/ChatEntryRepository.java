package com.example.demo.repository;

import com.example.demo.model.ChatEntryDB;
import com.example.demo.model.ChatResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatEntryRepository extends MongoRepository<ChatEntryDB, String> {
    public ChatEntryDB findByPrompt(String prompt);
}
