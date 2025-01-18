package com.example.demo.model;

import org.springframework.data.annotation.Id;

public class ChatEntryDB {
    @Id
    private String prompt;
    private ChatResponse response;

    public ChatEntryDB(String prompt, ChatResponse response) {
        this.prompt = prompt;
        this.response = response;
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
