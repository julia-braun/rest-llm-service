package com.example.demo.model;

import org.springframework.data.annotation.Id;

public class ChatEntryDB {
    @Id
    private String id;
    private String prompt;
    private ChatResponse response;

    public ChatEntryDB(String id, String prompt, ChatResponse response) {
        this.id = id;
        this.prompt = prompt;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public ChatResponse getResponse() {
        return response;
    }

    public void setResponse(ChatResponse response) {
        this.response = response;
    }
}
