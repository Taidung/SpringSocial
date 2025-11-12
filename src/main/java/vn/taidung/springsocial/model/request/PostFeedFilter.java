package vn.taidung.springsocial.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PostFeedFilter {

    @Min(0)
    private int pageNumber = 0;

    @Min(1)
    @Max(20)
    private int pageSize = 10;

    @Pattern(regexp = "asc|desc")
    private String sort = "desc";

    @Size(max = 100)
    private String search = "";

    @Size(max = 5)
    private String[] tags;

    public PostFeedFilter() {
    }

    public PostFeedFilter(@Min(0) int pageNumber, @Min(1) @Max(20) int pageSize,
            @Pattern(regexp = "asc|desc") String sort, @Size(max = 100) String search, @Size(max = 5) String[] tags) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.search = search;
        this.tags = tags;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean hasSearch() {
        return search != null && !search.trim().isEmpty();
    }

    public boolean hasTags() {
        return tags != null && tags.length > 0;
    }
}
