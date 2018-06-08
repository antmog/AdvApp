package com.infosystem.advertisment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvInitialDataDto {

    private List<AdvTariffDto> advTariffDtoList;
}
