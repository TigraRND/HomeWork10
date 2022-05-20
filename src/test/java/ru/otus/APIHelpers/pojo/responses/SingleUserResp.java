package ru.otus.APIHelpers.pojo.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
public class SingleUserResp {

    @JsonProperty("data")
    private UserDataResp data;

    @JsonProperty("support")
    private SupportResp support;

    //FIXME добавить UserDateResp и SupportResp внутренними классами
}
