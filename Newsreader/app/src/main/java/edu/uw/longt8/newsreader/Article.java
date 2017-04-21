package edu.uw.longt8.newsreader;

/**
 * Created by Leon on 4/20/17.
 */

public class Article {
    private static final String IMAGE_PLACEHOLDER_URL =
            "http://cvalink.com/wp-content/themes/TechNews/images/img_not_available.png";

    public String snippet;
    public String imgUrl;
    public String webUrl;
    public String headline;
    public String pubDate;

    public Article(String snippet, String webUrl, String imgUrl, String headline, String pubDate) {
        this.snippet = snippet;
        this.webUrl = webUrl;
        this.headline = headline;
        this.pubDate = pubDate;
        if (imgUrl == null)
            this.imgUrl = IMAGE_PLACEHOLDER_URL;
        else
            this.imgUrl = imgUrl;
    }

    public String toString() {
        return headline + "\n" + "Date Published: " + pubDate.substring(0, 10);
    }
}