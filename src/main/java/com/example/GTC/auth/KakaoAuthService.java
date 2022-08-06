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
public class KakaoAuthService {


    public ResponseEntity<String> requestKakaoProfile(HttpEntity request){
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public HttpEntity<MultiValueMap<String, String>> generateKakaoAuthCodeRequest(String code) {
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "b4e805d0bae6a9b118fef473aaaa1f24");
        params.add("redirect_uri", "http://localhost:8080/api/v1/auth/kakao");
        params.add("code", code);
        return new HttpEntity<>(params,headers);
    }

    public ResponseEntity<String> requestKakaoAccessToken(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }

}
