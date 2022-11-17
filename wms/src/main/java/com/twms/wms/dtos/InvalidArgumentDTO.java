package com.twms.wms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidArgumentDTO {
    private String invalidField;
    private String errorMessage;
    private String providedValue;

    @Override
    public String toString() {
        return "InvalidArgumentDTO{" +
                "invalidField='" + invalidField + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", providedValue='" + providedValue + '\'' +
                '}';
    }
}
