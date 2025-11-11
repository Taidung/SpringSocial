package vn.taidung.springsocial.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class PostFeedFilter {

    @Min(0)
    private int pageNumber = 0;

    @Min(1)
    @Max(20)
    private int pageSize = 10;

    @Pattern(regexp = "asc|desc")
    private String sort = "desc";

    public PostFeedFilter() {
    }

    public PostFeedFilter(@Min(0) int pageNumber, @Min(1) @Max(20) int pageSize,
            @Pattern(regexp = "asc|desc") String sort) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
