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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @ToString
    public static class UserDataResp {
        @JsonProperty("id")
        private int id;
        @JsonProperty("email")
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
        @JsonProperty("avatar")
        private String avatar;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @ToString
    public static class SupportResp {
        @JsonProperty("url")
        private String url;
        @JsonProperty("text")
        private String text;
    }

    //FIXME добавить UserDateResp и SupportResp внутренними классами
}
