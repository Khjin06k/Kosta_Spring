package com.kosta.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalClinic {
    private String trdStateNm; // 영업 상태명
    private String siteTel; // 전화번호
    private String rdnwhlAddr; // 도로명 주소
    private String bplcNm; // 사업자명
    private String x; // 위치
    private String y;

    public AnimalClinic(String trdStateNm, String siteTel, String rdnwhlAddr, String bplcNm, String x, String y) {
        this.trdStateNm = trdStateNm;
        this.siteTel = siteTel;
        this.rdnwhlAddr = rdnwhlAddr;
        this.bplcNm = bplcNm;
        this.x = x;
        this.y = y;
    }
}
