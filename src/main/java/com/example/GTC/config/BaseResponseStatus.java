package com.example.GTC.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청성공

     */
    SUCCESS(true,1000,"요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    TOO_LONG_TEXT(false,2000,"텍스트가 1000자 이상입니다."),

    //login 관련 오류
    NOT_REGEX_EMAIL(false, 2001, "이메일의 형식을 확인해주세요"),
    NOT_REGEX_PHONENUM(false, 2002, "전화번호 형식을 확인해주세요"),
    NOT_EXIST_EMAIL(false, 2003, "존재하지 않는 이메일입니다."),
    WRONG_PASSWORD(false, 2004, "비밀번호가 틀렸습니다."),
    NOT_EXIST_PHONENUM(false,2005,"존재하지 않는 전화번호입니다."),
    NOT_EXIST_USERNAME(false,2006,"존재하지 않는 사용자 이름 입니다."),
    NOT_EXIST_NAME(false, 2007, "해당 이름의 유저가 존재하지 않습니다."),
    NOT_EXIST_DATE(false, 2008, "해당 일자의 유저가 존재하지 않습니다."),

    EMPTY_PASSWORD(false, 2010, "비밀번호를 입력해주세요"),
    INVALID_PHONENUM(false, 2011, "유효하지 않은 전화번호 입니다."),
    INVALID_USERID(false, 2012, "유효하지 않은 유저ID 입니다."),


    //chat 관련 오류
    EMPTY_MESSAGE(false,2020,"메시지가 존재하지 않습니다."),
    EMPTY_USER_DATA(false, 2021, "로그인 된 유저 정보가 존재하지 않습니다."),

    //paging
    NOT_PAGED(false,2030,"페이지를 입력하세요"),
    NOT_SIZED(false,2031,"페이징 사이즈를 입력하세요"),
    INVALID_SIZE(false, 2032, "페이징 사이즈를 10으로 입력하세요"),
    CANNOT_REPORT_OWN_FEED(false,2040,"자신의 피드를 신고할 수 없습니다."),
    CANNOT_REPORT_OWN_COMMENT(false,2041,"자신의 댓글을 신고할 수 없습니다."),
    /**
     * 3000 : Response 오류
     */


    /**
     * 4000 : Database, Server 오류
     */

    FAILED_TO_CREATE_FEED_IN_SERVER(false,4000,"게시물 생성 서버 오류"),
    FAILED_TO_MODIFY_FEED_IN_SERVER(false,4001,"게시물 수정 서버 오류"),
    FAILED_TO_DELETE_FEED_IN_SERVER(false, 4002, "게시물 삭제 서버 오류"),
    FAILED_TO_CREATE_CHAT_IN_SERVER(false, 4003, "채팅 생성 서버 오류"),
    FAILED_TO_GET_CHAT_IN_SERVER(false, 4004, "채팅 조회 서버 오류"),
    FAILED_TO_CHECK_PHONENUMBER_IN_SERVER(false, 4005, "전화번호 인증 서버 오류"),
    FAILED_TO_CHANGE_PASSWORD_IN_SERVER(false, 4006, "비밀번호 변경 서버 오류"),
    FAILED_TO_CREATE_FOLLOW_IN_SERVER(false, 4007, "팔로우 생성 서버 오류"),
    FAILED_TO_ACCEPT_FOLLOW_IN_SERVER(false,4008,"팔로우 승인 서버 오류"),
    FAILED_TO_LOAD_UNACCEPTED_FOLLOW(false,4009,"미승인 팔로우 로딩 서버 오류"),
    FAILED_TO_REFUSEFOLLOW_IN_SERVER(false,4010,"팔로우 거절 서버 오류"),
    FAILED_TO_CALCELFOLLOW_IN_SERVER(false,4011,"팔로우 취소 서버 오류"),
    FAILED_TO_CREATE_FEEDREPORT_IN_SERVER(false,4012,"피드 신고 서버 오류"),
    FAILED_TO_CREATE_COMMENTREPORT_IN_SERVER(false,4013,"댓글 신고 서버 오류"),

    /**
     * 5000 : Jwt 오류
     */

    EMPTY_JWT(false, 5000, "JWT 토큰이 존재하지 않습니다."),
    INVALID_JWT(false, 5001, "JWT 토큰이 유효하지 않습니다."),;

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}