package com.bank.publicinfo.dto;

import lombok.Data;


@Data
public class BankDetailsDto {

    private Long id;
    private Long bik;
    private Long inn;
    private Long kpp;
    private Long corAccount;
    private String city;
    private String jointStockCompany;
    private String name;

}
