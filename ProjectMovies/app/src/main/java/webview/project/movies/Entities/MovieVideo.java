package webview.project.movies.Entities;

import java.util.ArrayList;

/**
 * Created by lfarias on 4/12/17.
 */
public class MovieVideo {

    public ArrayList<VideoDetailObject> getVideoDetails() {
        return videoDetails;
    }

    public void setVideoDetails(ArrayList<VideoDetailObject> videoDetails) {
        this.videoDetails = videoDetails;
    }

    private ArrayList<VideoDetailObject> videoDetails;
}
