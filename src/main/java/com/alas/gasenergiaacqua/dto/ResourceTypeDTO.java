package com.alas.gasenergiaacqua.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceTypeDTO {
    private Integer id;
    private String typeName;
}
