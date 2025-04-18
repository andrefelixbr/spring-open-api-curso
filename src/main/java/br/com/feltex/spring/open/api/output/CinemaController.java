package br.com.feltex.spring.open.api.output;

import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema")
public class CinemaController {

    final ChatClient chatClient;

    public CinemaController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/lista-filmes")
    public String gerarListaFilmes(@RequestParam(value = "ator", defaultValue = "Antonio Fagundes") String ator) {
        return chatClient.prompt()
            .user(u -> u.text("Gere a lista de filmes do ator {ator}").param("ator",ator))
            .call()
            .content();
    }

    @GetMapping("/lista-formatada")
    public Ator getActorFilmsByName(@RequestParam(value = "ator", defaultValue = "Antonio Fagundes") String ator) {
        return chatClient.prompt()
            .user(u -> u.text("Gere a lista de filmes do ator {ator}").param("ator",ator))
            .call()
            .entity(Ator.class);
    }


    @GetMapping("/lista-atores")
    public List<Ator> listaAtores() {
        return chatClient.prompt()
            .user("Gere a fimografia dos atores Denzel Washington, Leonardo DiCaprio and Tom Hanks")
            .call()
            .entity(new ParameterizedTypeReference<>() {});
    }

}
