package net.angrycode.wesou.parser;

import android.util.Xml;

import net.angrycode.wesou.utils.DateUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lancelot on 2017/4/4.
 */

public class RssReader {

    private List<RssItem> mItems = new ArrayList<>();

    XmlPullParser mParser;

    public RssReader(String xml) throws Exception {
        mParser = Xml.newPullParser();
        InputStream input = new ByteArrayInputStream(xml.getBytes());
        mParser.setInput(input, "UTF-8");
        parse();
    }

    public void parse() throws Exception {
        RssItem rssItem = null;
        int event = mParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            String nodeName = mParser.getName();
            switch (event) {
                case XmlPullParser.START_DOCUMENT: // 文档开始
                    mItems = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG: // 标签开始
                    if ("item".equals(nodeName)) {
                        rssItem = new RssItem();
                    }
                    if ("title".equals(nodeName)) {
                        if (rssItem != null) {
                            String title = mParser.nextText();
                            rssItem.setTitle(title.trim());
                        }
                    }
                    if ("description".equals(nodeName)) {
                        if (rssItem != null) {
                            String desc = mParser.nextText();
                            rssItem.setDescription(desc.trim());
                        }
                    }
                    if ("link".equals(nodeName)) {
                        if (rssItem != null) {
                            String link = mParser.nextText();
                            rssItem.setLink(link.trim());
                        }
                    }
                    if ("category".equals(nodeName)) {
                        if (rssItem != null) {
                            String category = mParser.nextText();
                            rssItem.setCategory(category.trim());
                        }
                    }
                    if ("enclosure".equals(nodeName)) {
                        if (rssItem != null) {
                            String url = mParser.getAttributeValue(null,"url");
                            String length = mParser.getAttributeValue(null, "length");
                            String type = mParser.getAttributeValue(null, "type");
                            RssItem.Enclosure enclosure = new RssItem.Enclosure(url, length, type);
                            rssItem.setEnclosure(enclosure);
                        }
                    }
                    if ("pubDate".equals(nodeName)) {
                        if (rssItem != null) {
                            String pubDate = mParser.nextText();
                            rssItem.setPubDate(DateUtils.formatGMTText2Readable(pubDate.trim()));
                        }
                    }
                    break;
                case XmlPullParser.END_TAG: // 标签结束
                    if ("item".equals(nodeName)) {
                        if (rssItem != null) {
                            mItems.add(rssItem);
                        }
                        rssItem = null;
                    }
                    break;
            }
            event = mParser.next(); // 下一个标签
        }
    }

    public List<RssItem> getItems() {
        return mItems;
    }
}
