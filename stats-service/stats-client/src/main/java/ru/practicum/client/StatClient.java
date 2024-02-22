package ru.practicum.client;

import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.exception.CallServerException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatClient {

    @Value("${stat.server.url}")
    private String url;

    private final RestTemplate restTemplate;

    public StatClient() {
        this.restTemplate = new RestTemplate();
    }

    public void save(EventDto createDto) {
        try {
            restTemplate.exchange(url + "/hit", HttpMethod.POST, new HttpEntity<>(createDto, getDefaultHeaders()),
                    Object.class);
        } catch (HttpStatusCodeException e) {
            throw new CallServerException("При вызове сервера произошла ошибка");
        }
    }

    public List<StatsDto> get(LocalDateTime startDate, LocalDateTime endDate, @Nullable List<String> uris, boolean unique) {

        StringBuilder urlBuilder = new StringBuilder(url);

        urlBuilder.append("/stats?")
                .append("start=").append(startDate)
                .append("&end=").append(endDate)
                .append("&unique=").append(unique);

        if (uris != null) {
            for (String uri : uris) {
                urlBuilder.append("&uris=").append(uri);
            }
        }

        try {
            return restTemplate.exchange(urlBuilder.toString(),
                    HttpMethod.GET,
                    new HttpEntity<>(getDefaultHeaders()),
                    new ParameterizedTypeReference<List<StatsDto>>() {
                    }).getBody();
        } catch (HttpStatusCodeException e) {
            throw new CallServerException("При вызове сервера произошла ошибка");
        }
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
