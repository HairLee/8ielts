package a8ielts.haipham.com.a8ielts;

/**
 * Created by hailpt on 13/10/2016.
 */

public class Youtube {

    private String videoId;
    private String thumbnail;
    private String title;
    private String publishedAt;

    public Youtube(){

    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
