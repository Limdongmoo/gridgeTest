package com.example.GTC.auth;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

@Service
@Transactional
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
