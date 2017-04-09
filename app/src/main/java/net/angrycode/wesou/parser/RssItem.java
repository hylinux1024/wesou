package net.angrycode.wesou.parser;

/**
 * 通用的Rss Item 实体
 * Created by lancelot on 2017/4/4.
 */

public class RssItem {
    private String title;
    private String description;
    private String link;
    private String category;
    private Enclosure enclosure;
    private String pubDate;

    public RssItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public static class Enclosure {
        public String url;
        public String length;
        public String type;

        public Enclosure(String url, String length, String type) {
            this.url = url;
            this.length = length;
            this.type = type;
        }
    }
}
