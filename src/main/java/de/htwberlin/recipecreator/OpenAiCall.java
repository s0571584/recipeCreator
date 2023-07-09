package de.htwberlin.recipecreator;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://s0571584.github.io")
@RestController
@RequestMapping("/api/openai")
public class OpenAiCall {

    String API_KEY = "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";


    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chatGPT(@RequestBody Map<String, String> payload) throws Exception {
        String zutaten = payload.get("zutaten");

        HttpClient client = HttpClient.newHttpClient();

        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-3.5-turbo");
        data.put("temperature", 0.5);
        data.put("messages", List.of(
                Map.of("role", "system", "content", "Du bist ein hilfreicher AI Koch"),
                Map.of("role", "user", "content", "Erstelle ein Rezept mit folgenden Zutaten: " + zutaten)
        ));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(ENDPOINT))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Map<String, Object> jsonResponse = objectMapper.readValue(response.body(), Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) jsonResponse.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String content = (String) message.get("content");
            content = content.replaceFirst("^\\s+", "");

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("content", content);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            System.out.println(response.body());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}