package de.htwberlin.recipecreator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OpenAiCallTests {

    @Mock
    private HttpClient client;

    @InjectMocks
    private OpenAiCall openAiCall;

    @Test
    public void chatGPTTest() throws Exception {
        // Mock the HTTP response
        HttpResponse<String> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.body()).thenReturn("{\"choices\": [{\"message\": {\"content\": \"Test content\"}}]}");

        // Mock the HTTP client
        when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        // Perform the request
        Map<String, String> payload = Map.of("zutaten", "Erdbeer, Marmelade, Milch, Mehl, Eier, Zucker, Butte");
        ResponseEntity<Map<String, String>> result = openAiCall.chatGPT(payload);

        // Assert the result
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody().get("content"));
    }
}

