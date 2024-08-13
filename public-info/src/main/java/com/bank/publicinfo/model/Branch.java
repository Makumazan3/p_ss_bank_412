package com.bank.publicinfo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.OffsetTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch", schema = "public_bank_information")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number", unique = true)
    private int phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "start_of_work")
    private OffsetTime startOfWork;

    @Column(name = "end_of_work")
    private OffsetTime endOfWork;

    @OneToMany(mappedBy = "branch",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Atm> atmList;

}
