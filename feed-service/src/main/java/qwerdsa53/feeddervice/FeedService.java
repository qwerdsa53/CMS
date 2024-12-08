package qwerdsa53.feeddervice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final RestTemplate restTemplate;

    @Value("${post-service.url:http://localhost:8083/api/v1/posts}")
    private String postServiceUrl;

    public Object getAllPosts(int page, int size) {
        String url = UriComponentsBuilder.fromHttpUrl(postServiceUrl)
                .path("/all")
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString();

        return restTemplate.getForObject(url, Object.class);
    }
}