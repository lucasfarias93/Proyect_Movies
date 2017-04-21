package webview.project.movies.Entities;

import java.util.ArrayList;

/**
 * Created by LUCAS on 19/04/2017.
 */
public class ReviewResult {
    private Integer id;
    private Integer page;
    private Integer total_pages;

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

    private Integer total_results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ArrayList<ReviewDetails> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReviewDetails> results) {
        this.results = results;
    }

    private ArrayList<ReviewDetails> results;
}
