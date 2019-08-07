package com.yuehe.app.yuehecommon;

import org.springframework.data.domain.Sort.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class PaginationAndSortModel {
    private Integer pageSize = 10; // default page size is 10
    private Integer pageNumber = 0; // default page number is 0 (yes it is weird)
    private String sortProperty = "id"; // default sort column is  id
    private Direction sortDirection = Direction.ASC; // default sort direction is ascending

}