package com.example.GTC.auth;

import com.example.GTC.auth.model.AuthLoginModel;
import com.example.GTC.auth.model.AuthModel;
import com.example.GTC.auth.model.AuthProvider;
import com.example.GTC.auth.model.AuthSignUpModel;
import com.example.GTC.config.BaseResponse;
import com.example.GTC.user.UserService;
import com.example.GTC.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final NaverAuthService naverAuthService;
    private final KakaoAuthService kakaoAuthService;
    private final AuthService authService;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthController(NaverAuthService naverAuthService, KakaoAuthService kakaoAuthService,
                          AuthService authService, JwtService jwtService, UserService userService) {
        this.naverAuthService = naverAuthService;
        this.kakaoAuthService = kakaoAuthService;
        this.authService = authService;
        this.jwtService = jwtService;
        this.userService = userService;
    }
//
//    @GetMapping("/naver")
//    public String authNaver(@RequestParam String code) throws JSONException {
//        String accessToken = authService.extractAccessToken(naverAuthService.requestNaverAccessToken(naverAuthService.generateNaverAuthCodeRequest(code)).getBody());
//        return naverAuthService.requestNaverProfile(authService.generateProfileRequest(accessToken)).getBody();
//    }


    @ApiOperation(value = "카카오 로그인, 최초 로그인시 회원가입을 위해 email 반환")
    @ApiImplicitParam(name = "code", value = "authorize code",required = true, dataType = "String", paramType = "query")
    @GetMapping("/kakao")
    public ResponseEntity<BaseResponse>  authKakao(@RequestParam String code) throws JSONException {
        String accessToken = authService.extractAccessToken(kakaoAuthService.requestKakaoAccessToken(kakaoAuthService.generateKakaoAuthCodeRequest(code)).getBody());
        String body = kakaoAuthService.requestKakaoProfile(authService.generateProfileRequest(accessToken)).getBody();
        String email = authService.extractEmail(body);
        if (userService.findByEmail(email).isPresent()) {
            Long userId = userService.findByEmail(email).get().getUserId();

            AuthLoginModel authLoginModel = new AuthLoginModel(jwtService.creatJwt(userId), userId);
            BaseResponse<AuthLoginModel> a = new BaseResponse<>(authLoginModel);
            return new ResponseEntity<>(a,HttpStatus.OK);
        }
        else{

            return new ResponseEntity<>(new BaseResponse<>(new AuthSignUpModel(AuthProvider.kakao, email)), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
