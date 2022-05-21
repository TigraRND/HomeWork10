package ru.otus.APIHelpers.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class SingleResourceResp {
    @JsonProperty("data")
    public ResourceDataResponse data;
    @JsonProperty("support")
    public SupportResp support;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @ToString
    public static class ResourceDataResponse {
        @JsonProperty("id")
        public int id;
        @JsonProperty("name")
        public String name;
        @JsonProperty("year")
        public int year;
        @JsonProperty("color")
        public String color;
        @JsonProperty("pantone_value")
        public String pantoneValue;
    }
}