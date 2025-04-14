package br.com.feltex.spring.open.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatControllerAvancado {

    private final ChatClient chatClient;

    public ChatControllerAvancado(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/")
    public String piada(
        @RequestParam(value = "mensagem", defaultValue = "Conte uma piada sobre carros") String mensagem) {
        return chatClient.prompt()
            .user(mensagem)
            .call()
            .content();
    }

    @GetMapping("/piada")
    public String piadaPorAssunto(@RequestParam String assunto) {
        return chatClient.prompt()
            .user(u -> u.text("Conte uma piada sobre {assunto}").param("assunto", assunto))
            .call()
            .content();
    }

    @GetMapping("/resposta")
    public ChatResponse piadaComRepostaCompleta(
        @RequestParam(value = "mensagem", defaultValue = "Conte uma piada sobre computadores") String mensagem) {
        return chatClient.prompt()
            .user(mensagem)
            .call()
            .chatResponse();
    }


}
