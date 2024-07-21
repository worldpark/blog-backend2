package com.example.blog.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {


    NOT_EXISTS_PATH("501", "저장경로가 존재하지 않습니다."),
    INVALID_AUTH("400", "권한이 없습니다."),

    FAIL_SAVE("300", "데이터를 저장하지 못하였습니다."),
    NOT_EXISTS_DATA("301", "필요한 데이터를 서버가 전달받지 못했습니다."),

    UNKNOWN_ERROR("000", "알 수 없는 에러가 발생하였습니다."),


    //로그인 관련 에러 코드
    INVALID_SESSION("100", "유효하지 않은 세션입니다."),
    INVALID_LOGIN("101", "유효하지 않은 로그인 정보입니다."),

    DIFFERENCE_ID_OR_PW("001", "아이디 혹은 비밀번호가 다릅니다."),
    SERVER_ERROR("002", "서버문제로 인하여 로그인 요청을 처리 할 수 없습니다."),
    NOT_EXISTS_ID("001", "아이디 혹은 비밀번호가 다릅니다."),
    REJERCT_AUTH("004", "인증요청이 거부되었습니다.");



    private final String code;
    private final String message;
}
