package webview.project.movies.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MoviesResult implements Serializable {
    private List<MovieData> results = new ArrayList<MovieData>();
    private Integer page;
    private Integer total_pages;
    private Integer total_results;

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieData> getResults() {
        return results;
    }

    public void setResults(List<MovieData> results) {
        this.results = results;
    }

}
