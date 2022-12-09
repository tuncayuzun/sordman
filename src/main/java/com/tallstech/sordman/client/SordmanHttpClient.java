package com.tallstech.sordman.client;

import static com.tallstech.sordman.client.SordmanHttpClientConstants.AUTHORIZATION_KEY;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.CONTENT_TYPE_KEY;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.CORRELATION_ID;
import static com.tallstech.sordman.client.SordmanHttpClientConstants.TOKEN_KEY;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import com.tallstech.sordman.constant.SordmanConstants;
import com.tallstech.sordman.exception.SordmanException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SordmanHttpClient {

    private ObjectMapper objectMapper;
    private HttpClient httpClient;


    public SordmanHttpClient(@Qualifier(SordmanConstants.SORDMAN_OBJECT_MAPPER) ObjectMapper objectMapper, @Qualifier(SordmanConstants.SORDMAN_HTTP_CLIENT) HttpClient httpClient) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
    }

    public <T> T doGet(String url, Map<String, String> header, Class<T> responseType) {
        try {
            var request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .headers(generateHeaders(header))
                    .build();

            var httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validatePayloadResponse(httpResponse.statusCode(), httpResponse.body());

            if (isArray(httpResponse.body())) {
                return objectMapper.readValue(httpResponse.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
            } else {
                return objectMapper.readValue(httpResponse.body(), responseType);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (SordmanException exception) {
            throw new SordmanException(exception.getStatus(), exception.getMessage());
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    public <T> T doPost(String url, Map<String, String> header, Object postObject, Class<T> responseType) {
        try {
            var request = HttpRequest.newBuilder()
                    .POST(generateBodyPublisher(postObject, header))
                    .uri(URI.create(url))
                    .headers(generateHeaders(header))
                    .build();

            var httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validatePayloadResponse(httpResponse.statusCode(), httpResponse.body());

            if (isArray(httpResponse.body())) {
                return objectMapper.readValue(httpResponse.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
            } else {
                return objectMapper.readValue(httpResponse.body(), responseType);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (SordmanException exception) {
            throw new SordmanException(exception.getStatus(), exception.getMessage());
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    public <T> T doPut(String url, Map<String, String> header, Object putObject, Class<T> responseType) {
        try {
            var request = HttpRequest.newBuilder()
                    .PUT(generateBodyPublisher(putObject, header))
                    .uri(URI.create(url))
                    .headers(generateHeaders(header))
                    .build();

            var httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validatePayloadResponse(httpResponse.statusCode(), httpResponse.body());

            if (isArray(httpResponse.body())) {
                return objectMapper.readValue(httpResponse.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
            } else {
                return objectMapper.readValue(httpResponse.body(), responseType);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (SordmanException exception) {
            throw new SordmanException(exception.getStatus(), exception.getMessage());
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    public <T, S> T doPatch(String url, Map<String, String> header, S patchObject, Class<T> responseType) {
        try {
            var request = HttpRequest.newBuilder()
                    .method("PATCH", generateBodyPublisher(patchObject, header))
                    .uri(URI.create(url))
                    .headers(generateHeaders(header))
                    .build();

            var httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validatePayloadResponse(httpResponse.statusCode(), httpResponse.body());

            if (isArray(httpResponse.body())) {
                return objectMapper.readValue(httpResponse.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
            } else {
                return objectMapper.readValue(httpResponse.body(), responseType);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (SordmanException exception) {
            throw new SordmanException(exception.getStatus(), exception.getMessage());
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    public void doDelete(String url, Map<String, String> header) {
        try {
            var request = HttpRequest.newBuilder()
                    .DELETE()
                    .uri(URI.create(url))
                    .headers(generateHeaders(header))
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (SordmanException exception) {
            throw new SordmanException(exception.getStatus(), exception.getMessage());
        } catch (Exception exception) {
            throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }


    private HttpRequest.BodyPublisher generateBodyPublisher(Object object, Map<String, String> header) throws JsonProcessingException {
        if (object instanceof Map && !ObjectUtils.isEmpty(header) && MediaType.APPLICATION_FORM_URLENCODED.toString().equals(header.get(CONTENT_TYPE_KEY))) {
            String params = ((Map<?, ?>) object)
                    .entrySet()
                    .stream()
                    .map(entry -> Stream.of(
                                    URLEncoder.encode((String) entry.getKey(), UTF_8),
                                    URLEncoder.encode((String) entry.getValue(), UTF_8))
                            .collect(Collectors.joining("="))
                    ).collect(Collectors.joining("&"));
            return HttpRequest.BodyPublishers.ofString(params);
        } else {
            return HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(object), UTF_8);
        }
    }

    private boolean isArray(String json) {
        return JsonParser.parseString(json).isJsonArray();
    }

    private String[] generateHeaders(Map<String, String> headerMap) {
        if (ObjectUtils.isEmpty(headerMap)) {
            headerMap = new HashMap<>();
        }
        headerMap.put(CORRELATION_ID, MDC.get(CORRELATION_ID));
        headerMap.put(AUTHORIZATION_KEY, MDC.get(TOKEN_KEY));

        String[] headers;
        int i = 0;
        headers = new String[headerMap.size() * 2];
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            headers[i] = entry.getKey();
            headers[++i] = entry.getValue();
            ++i;
        }
        return headers;
    }

    private void validatePayloadResponse(int statusCode, String responseBody) {
        if (HttpStatus.valueOf(statusCode).isError()) {
            if (statusCode == 400) {
                throw new SordmanException(HttpStatus.BAD_REQUEST, responseBody);
            } else if (statusCode == 404) {
                throw new SordmanException(HttpStatus.NOT_FOUND, responseBody);
            } else if (statusCode == 401) {
                throw new SordmanException(HttpStatus.UNAUTHORIZED, responseBody);
            } else if (statusCode == 403) {
                throw new SordmanException(HttpStatus.FORBIDDEN, responseBody);
            } else {
                throw new SordmanException(HttpStatus.INTERNAL_SERVER_ERROR, responseBody);
            }
        }
    }
}
