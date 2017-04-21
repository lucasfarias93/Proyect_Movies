package webview.project.movies.Entities;

import java.util.ArrayList;

public class MovieVideo {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;

    public ArrayList<VideoDetailObject> getVideoDetails() {
        return results;
    }

    public void setVideoDetails(ArrayList<VideoDetailObject> videoDetails) {
        this.results = videoDetails;
    }

    private ArrayList<VideoDetailObject> results;
}
