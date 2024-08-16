package com.bank.publicinfo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atm", schema = "public_bank_information")
public class Atm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Nullable
    @Column(name = "start_of_work")

    private OffsetTime startOfWork;

    @Nullable
    @Column(name = "end_of_work")
    private OffsetTime endOfWork;

    @Column(name = "all_hours")
    private boolean allHours;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id",
            insertable = false,
            updatable = false)
    private Branch branch;

    @Column(name = "branch_id")
    private Long bankDetailsId;
}