package com.kosta.api2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BuisnessDto {
    private String buisnessNum; // 사업자 번호

    public BuisnessDto(){}

    public BuisnessDto(String buisnessNum) {
        this.buisnessNum = buisnessNum;
    }
}
