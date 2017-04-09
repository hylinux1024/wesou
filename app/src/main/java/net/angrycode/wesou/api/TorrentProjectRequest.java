package net.angrycode.wesou.api;

import net.angrycode.wesou.bean.TorrentProject;
import net.angrycode.wesou.parser.RssReader;
import net.angrycode.wesou.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by lancelot on 2017/4/4.
 */

public class TorrentProjectRequest extends BaseRequest<List<TorrentProject>> {
    String url = "https://torrentproject.se/rss/";

    public TorrentProjectRequest(Map<String, String> params) {
        super(params);
        String keywords = params.remove("keywords");
        url = url + keywords+"/";
    }

    @Override
    public List<TorrentProject> onParse(int code, String response) {
        if (isSuccessful(code)) {
            try {
                RssReader reader = new RssReader(response);
                List<TorrentProject> list = TorrentProject.build(reader);
                return list;
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return null;
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
