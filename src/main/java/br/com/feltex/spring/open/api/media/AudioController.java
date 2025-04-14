package br.com.feltex.spring.open.api.media;

import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.SpeechRequest.Voice;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audio")
public class AudioController {

    final OpenAiAudioSpeechModel audioSpeechModel;
    private final SpeechModel speechModel;


    public AudioController(OpenAiAudioSpeechModel audioSpeechModel, SpeechModel speechModel) {
        this.audioSpeechModel = audioSpeechModel;
        this.speechModel = speechModel;
    }

    @GetMapping("/gerar-audio")
    public String converter(
        final @RequestParam(value = "texto", defaultValue = "Hoje é um novo dia. De um novo tempo que começou!!") String texto) {
        textToAudio(texto);
        return "Texto convertido para áudio!";
    }

    private void textToAudio(String content) {
        final Float SPEED = 1.0f;
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
            .voice(Voice.NOVA)
            .speed(SPEED)
            .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
            .model(OpenAiAudioApi.TtsModel.TTS_1.value)
            .build();
        final var speechPrompt = new SpeechPrompt(content, speechOptions);
        final var response = speechModel.call(speechPrompt);
        byte[] audioBytes = response.getResult().getOutput();

        saveToMp3(audioBytes, "output.mp3");

    }

    private void saveToMp3(byte[] audioBytes, String outputFileName) {
        try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.write(audioBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
