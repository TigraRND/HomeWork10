package ru.otus.APIHelpers.pojo.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ListUsersResp {
    @JsonProperty("page")
    private int page;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("data")
    private List<SingleUserResp.UserDataResp> data;

    @JsonProperty("support")
    private SingleUserResp.SupportResp support;
}
