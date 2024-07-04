package br.com.mascenadev.screanmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import io.github.cdimascio.dotenv.Dotenv;

public class ChatGPTConsultation {

    public static String getTranslation(String text) {

        Dotenv dotenv = Dotenv.load();
        OpenAiService service = new OpenAiService(dotenv.get("OPENAI_KEY"));
        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}