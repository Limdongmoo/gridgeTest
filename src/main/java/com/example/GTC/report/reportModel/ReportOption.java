package com.example.GTC.report.reportModel;

import lombok.Getter;

@Getter
public enum ReportOption {
    SPAM("스팸")
    ,OBSCENITY("나체 이미지 또는 성적 행위")
    ,NONLIKE("마음에 들지 않습니다.")
    ,FRAUD("사기 또는 거짓")
    ,HATRED("혐오 발언 또는 상징")
    ,DUMMY("거짓 정보")
    ,BULLY("따돌림 또는 괴롭힘")
    ,VIOLENCE("폭력 또는 위험한 단체")
    ,INTELLECTUAL_PROPERTY_RIGHTS_INFRINGEMENT("지식재산권 침해");

    private String content;

    ReportOption(String content) {
        this.content = content;
    }
}

