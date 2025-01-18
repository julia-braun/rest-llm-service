package com.example.demo.controller;

import com.example.demo.model.ChatEntryDB;
import com.example.demo.model.ChatRequest;
import com.example.demo.model.ChatResponse;
import com.example.demo.repository.ChatEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatController {

    @Qualifier("groqRestTemplate")
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ChatEntryRepository chatEntryRepository;

    @Value("${groq.model}")
    private String model;

    @Value("${groq.api.url}")
    private String apiUrl;

    @GetMapping("/responses")
    public String getSavedResponses(@RequestParam String prompt) {

        ChatEntryDB entry = chatEntryRepository.findByPrompt(prompt);
        if (entry == null || entry.getResponse() == null || entry.getResponse().getChoices() == null || entry.getResponse().getChoices().isEmpty()) {
            return "No saved responses for prompt: " + prompt;
        }
        return "Prompt: " + prompt + "\n" + entry.getResponse().toString();
    }

    @PostMapping("/ask")
    public String createRequest(@RequestParam String prompt) {

        ChatRequest request = new ChatRequest(model,prompt);

        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        ChatEntryDB entry = chatEntryRepository.findByPrompt(prompt);

        if (entry == null) {
            chatEntryRepository.save(new ChatEntryDB(prompt, response));
            return response.getChoices().getFirst().getMessage().getContent() +
                    "\n---Response saved to database---";
        }

        ChatResponse existingResponse = entry.getResponse();
        existingResponse.getChoices().add(response.getChoices().getFirst());

        chatEntryRepository.delete(entry);
        chatEntryRepository.save(new ChatEntryDB(prompt, existingResponse));

        return response.getChoices().getFirst().getMessage().getContent() +
                "\n---Additional response saved to database---";
    }
}
