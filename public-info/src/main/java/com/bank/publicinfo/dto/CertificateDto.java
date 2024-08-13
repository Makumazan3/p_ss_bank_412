package com.bank.publicinfo.dto;

import lombok.Data;

@Data
public class CertificateDto {

    private Long id;
    private byte[] photo;
    private Long bankDetailsId;

}
