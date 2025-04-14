package br.com.feltex.spring.open.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/")
    public String perguntar(@RequestParam(value = "mensagem", defaultValue = "Quantos paises existem na europa?")
    String mensagem) {

        return chatClient.prompt()
            .user(mensagem)
            .call()
            .content();
    }

}
