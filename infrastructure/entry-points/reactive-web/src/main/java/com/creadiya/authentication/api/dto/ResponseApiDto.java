package com.creadiya.authentication.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ResponseApiDto<T> {
    private Integer code;
    private String identifier;
    private String date;
    private T data;
    private List<ErrorDto> errors;
}
