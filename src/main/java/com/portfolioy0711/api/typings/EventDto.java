package com.portfolioy0711.api.typings;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Data
@AllArgsConstructor
public class EventDto {
    @NotEmpty
    @ApiModelProperty(dataType = "EventType", example = "REVIEW")
    EventType type;

    @NotEmpty
    @ApiModelProperty(position = 1, dataType = "String", example = "ADD")
    ActionType action;

    @NotEmpty
    @ApiModelProperty(position = 2, dataType = "String", example = "240a0658-dc5f-4878-9831-ebb7b26687772")
    String reviewId;

    @NotEmpty
    @ApiModelProperty(position = 3, dataType = "String", example = "좋아요")
    String content;

    @ApiModelProperty(position = 4, dataType = "String[]", example = "[\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\"]")
    String[] attachedPhotoIds;

    @NotEmpty
    @ApiModelProperty(position = 5, dataType = "String", example = "3ede0ef2-92b7-4817-a5f3-0c575361f745")
    String userId;

    @NotEmpty
    @ApiModelProperty(position = 6, dataType = "String", example = "2e4baf1c-5acb-4efb-a1af-eddada31b00f")
    String placeId;
}
