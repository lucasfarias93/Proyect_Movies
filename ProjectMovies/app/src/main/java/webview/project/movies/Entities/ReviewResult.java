package webview.project.movies.Entities;

import java.util.ArrayList;

/**
 * Created by LUCAS on 19/04/2017.
 */
public class ReviewResult {
    private Integer id;
    private Integer page;

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
