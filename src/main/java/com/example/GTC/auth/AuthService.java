package com.example.GTC.auth;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class AuthService {
    public String extractAccessToken(String accessTokenResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(accessTokenResponse);
        return jsonObject.getString("access_token");
    }

    public HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    public String extractEmail(String loginResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(loginResponse);
        JSONObject kakao_account = jsonObject.getJSONObject("kakao_account");

        return kakao_account.getString("email");

    }

}
