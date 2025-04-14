package br.com.feltex.spring.open.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contexto")
public class ContextoController {

    private final ChatClient chatClient;

    public ContextoController(ChatClient.Builder builder) {
        this.chatClient =
            builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    @GetMapping("/chat")
    public String home(@RequestParam String prompt) {
        return chatClient.prompt()
            .user(prompt)
            .call()
            .content();
    }

}
