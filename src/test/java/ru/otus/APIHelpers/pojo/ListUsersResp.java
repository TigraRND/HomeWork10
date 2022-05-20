package ru.otus.APIHelpers.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.otus.APIHelpers.pojo.responses.SupportResp;
import ru.otus.APIHelpers.pojo.responses.UserDataResp;

import java.util.List;

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
    private List<UserDataResp> data;

    @JsonProperty("support")
    private SupportResp support;

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<UserDataResp> getData() {
        return data;
    }

    public SupportResp getSupport() {
        return support;
    }

    @Override
    public String toString() {
        return "ListUsersResponse{" +
                "page=" + page +
                ", perPage=" + perPage +
                ", total=" + total +
                ", totalPages=" + totalPages +
                ", data=" + data +
                ", support=" + support +
                '}';
    }
}
