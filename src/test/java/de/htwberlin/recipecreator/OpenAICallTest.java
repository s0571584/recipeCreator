package de.htwberlin.recipecreator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsInstanceOf.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenAiCallTest {

    @Mock
    private HttpClient httpClient;

    @Test
    void shouldSendCorrectRequestToOpenAI() throws Exception {
        // Given
        OpenAiCall openAiCall = new OpenAiCall(httpClient);
        Map<String, String> payload = new HashMap<>();
        payload.put("zutaten", "test zutaten");

        // Mock the HttpClient response
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        // When
        openAiCall.chatGPT(payload);

        // Then
        verify(httpClient).send(argThat((HttpRequest request) -> {
            return request.uri().toString().equals(OpenAiCall.ENDPOINT) &&
                    request.headers().map().get("Authorization").equals("Bearer " + OpenAiCall.OPENAI_API_KEY) &&
                    request.headers().map().get("Content-Type").equals("application/json") &&
                    request.method().equals("POST");
        }), any(HttpResponse.BodyHandler.class));
    }
}
