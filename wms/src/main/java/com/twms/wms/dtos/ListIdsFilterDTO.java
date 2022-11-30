package com.twms.wms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListIdsFilterDTO {
    private List<Long> ids = new ArrayList<>();
}
