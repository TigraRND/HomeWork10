package ru.retrofit.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class SingleResourceResp {
    @JsonProperty("data")
    public ResourceDataResponse data;
    @JsonProperty("support")
    public SupportResp support;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class ResourceDataResponse {
        public int id;
        public String name;
        public int year;
        public String color;
        @JsonProperty("pantone_value")
        public String pantoneValue;
    }
}