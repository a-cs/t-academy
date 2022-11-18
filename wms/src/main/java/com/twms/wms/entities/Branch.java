package com.twms.wms.entities;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Branch", uniqueConstraints = {
        @UniqueConstraint(name = "uc_branch_address_id", columnNames = {"address_id"})
})
@Data
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name must not be blank!")
    private String name;
    @Min(message = "Minimum accepted number of rows:1;Numero minimo aceito de linhas:1",value = 1)
    private int max_rows;
    @Min(message = "Minimum accepted number of colums:1;Numero minimo aceito de colunass:1",value = 1)
    private int max_columns;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
