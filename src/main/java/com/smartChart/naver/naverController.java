package com.smartChart.naver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;

@Slf4j
@RestController
@RequestMapping("/doctor")
public class naverController {

    // https://openapi.naver.com/v1/search/local.json?
    // query=%EC%A3%BC%EC%8B%9D&display=10&
    // start=1&
    // sort=random

    @PostMapping("/naver")
    public @ResponseBody String naver(
            @RequestBody naverRequest naverRequest
    ){

        //String query = "갈비집";
        //String encode = Base64.getEncoder().encodeToString(query.getBytes(StandardCharsets.UTF_8));

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query",naverRequest.getQuery())
                .queryParam("display",10)
                .queryParam("start",1)
                .queryParam("sort","random")
                .encode(Charset.forName("UTF-8"))
                .encode()
                .build()
                .toUri();

        RequestEntity<Void> req = (RequestEntity<Void>) RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id","yM8RRFvCe5dD3Ki1IREe")
                .header("X-Naver-Client-Secret","5rdfphHO_E")
                .build();

        //주소 로그 확인
        log.info("################# uri : {}",uri);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();
    }

}