package com.portfolioy0711.api.typings.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Data
@AllArgsConstructor
public class BlarBlarEventDto {

    @NotEmpty
    @ApiModelProperty(dataType = "EventType", example = "REVIEW")
    String type;

    @NotEmpty
    @ApiModelProperty(position = 1, dataType = "String", example = "ADD")
    String action;

    @NotEmpty
    @ApiModelProperty(position = 1, dataType = "String", example = "ADD")
    String blarblarInfo;

    public static class EventDto {
        String type;
        String action;
    }
}
