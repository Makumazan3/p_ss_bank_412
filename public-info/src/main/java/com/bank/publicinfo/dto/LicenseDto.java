package com.bank.publicinfo.dto;

import lombok.Data;

@Data
public class LicenseDto {

    private Long id;
    private byte[] photo;
    private Long bankDetailsId;

}
