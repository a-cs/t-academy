package com.twms.wms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchIdsProductIdsFilterDTO {
    private List<Long> branchIds = new ArrayList<>();
    private List<Long> productIds = new ArrayList<>();
}
