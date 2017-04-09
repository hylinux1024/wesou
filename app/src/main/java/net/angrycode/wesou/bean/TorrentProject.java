package net.angrycode.wesou.bean;

import android.support.annotation.NonNull;

import net.angrycode.wesou.parser.RssItem;
import net.angrycode.wesou.parser.RssReader;
import net.angrycode.wesou.utils.IntegerUtils;
import net.angrycode.wesou.utils.MagnetUtils;
import net.angrycode.wesou.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lancelot on 2017/4/4.
 */

public class TorrentProject extends ResponseData {
    private String title;
    private String description;
    private String link;
    private String category;
    private long length;
    private String type;
    private String pubDate;
    private String hash;
    private String magnetLink;

    public static List<TorrentProject> build(@NonNull RssReader rssReader) {
        List<TorrentProject> list = new ArrayList<>();
        List<RssItem> items = rssReader.getItems();
        for (RssItem rssItem : items) {
            TorrentProject torrent = new TorrentProject();
            torrent.title = rssItem.getTitle();
            torrent.description = rssItem.getDescription();
            torrent.link = rssItem.getLink();
            torrent.category = rssItem.getCategory();
            torrent.pubDate = rssItem.getPubDate();
            if (rssItem.getEnclosure() != null) {
                RssItem.Enclosure enclosure = rssItem.getEnclosure();
                torrent.length = IntegerUtils.parseLong(enclosure.length);
                torrent.type = enclosure.type;

                String url = enclosure.url;
                if (url != null) {
                    if (url.lastIndexOf("/") != -1 && url.lastIndexOf(".") != -1) {
                        int startIndex = url.lastIndexOf("/") + 1;
                        int endIndex = url.lastIndexOf(".");
                        torrent.hash = url.substring(startIndex, endIndex);
                    }
                }
                torrent.magnetLink = MagnetUtils.formatMagnet(torrent.hash);
            }
            list.add(torrent);
        }
//        items.forEach(rssItem -> {
//
//        });
        return list;
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

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMagnetLink() {
        return magnetLink;
    }

    public void setMagnetLink(String magnetLink) {
        this.magnetLink = magnetLink;
    }
}
