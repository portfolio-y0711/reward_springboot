package com.portfolioy0711.api.typings.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Data
@NoArgsConstructor
public class BlarBlarEventDto extends EventDto{
    @NotEmpty
    @ApiModelProperty(dataType = "EventType", example = "REVIEW")
    String type;

    @NotEmpty
    @ApiModelProperty(position = 1, dataType = "String", example = "ADD")
    String action;

    @NotEmpty
    @ApiModelProperty(position = 1, dataType = "String", example = "ADD")
    String blarblarInfo;

    @Builder
    public BlarBlarEventDto(@NotEmpty String type, @NotEmpty String action, @NotEmpty String blarblarInfo) {
        this.type = type;
        this.action = action;
        this.blarblarInfo = blarblarInfo;
    }
}
