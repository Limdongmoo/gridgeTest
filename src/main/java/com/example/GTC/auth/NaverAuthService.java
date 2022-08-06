package com.example.GTC.auth;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class NaverAuthService {

    private final String CLIENT_ID = "lp8a1mgChZFx4qeKbCwQ";
    private final String CLIENT_SECRET = "tNiWgh_Hcj";

    public ResponseEntity<String> requestNaverProfile(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public ResponseEntity<String> requestNaverAccessToken(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public HttpEntity<MultiValueMap<String, String>> generateNaverAuthCodeRequest(String code) {
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("code", code);
        return new HttpEntity<>(params,headers);
    }


}
