package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

public class ConsumerClient{
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public ConsumerClient(@Value("${serviceUrl}") String url) {
        this.url = url;
        this.restTemplate = new RestTemplate();
        System.out.println("URL: " + url);
    }

    public HttpStatus foos() {
        return restTemplate.exchange(url + "/posts/1", HttpMethod.GET, null, String.class).getStatusCode();
    }
}
