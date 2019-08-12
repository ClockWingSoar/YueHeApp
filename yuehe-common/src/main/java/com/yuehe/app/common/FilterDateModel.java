package com.yuehe.app.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  class FilterDateModel {
    private String startDate; // default page size is 10
    private String endDate; // default page number is 0 (yes it is weird)

}