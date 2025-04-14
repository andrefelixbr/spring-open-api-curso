package br.com.feltex.spring.open.api.media;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imagem")
public class ImagemController {

    final ChatModel chatModel;
    final ImageModel imageModel;


    public ImagemController(ChatModel chatModel, ImageModel imageModel) {
        this.chatModel = chatModel;
        this.imageModel = imageModel;
    }

    @GetMapping("/imagem-para-texto")
    public String descreverImagem() {
        return ChatClient.create(chatModel)
            .prompt()
            .user(useSpec ->
                useSpec.text("Descreva com detalhes o que você vê nesta imagem")
                    .media(MimeTypeUtils.IMAGE_JPEG,
                        new ClassPathResource("imagens/F1.jpg")))
            .call()
            .content();
    }

    @GetMapping("/texto-para-imagem")
    public String generateImage(@RequestParam String prompt) {

        ImageResponse response = imageModel.call(
            new ImagePrompt(prompt,
                OpenAiImageOptions
                    .builder()
                    .withN(1)
                    .withWidth(1024)
                    .withHeight(1024)
                    .withQuality("hd")
                    .build())
        );

        return response.getResult().getOutput().getUrl();
    }


}
