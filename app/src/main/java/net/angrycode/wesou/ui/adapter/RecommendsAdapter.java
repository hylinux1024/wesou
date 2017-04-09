package net.angrycode.wesou.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import net.angrycode.wesou.R;
import net.angrycode.wesou.bean.Video;
import net.angrycode.wesou.component.BaseSimpleRecycleAdapter;
import net.angrycode.wesou.component.ViewHolder;
import net.angrycode.wesou.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lancelot on 2017/3/24.
 */

public class RecommendsAdapter extends BaseSimpleRecycleAdapter<Video> {
    public RecommendsAdapter() {
        List<Video> list = new ArrayList<>();
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        list.add(new Video());
        setData(list);
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.item_main_recommends;
    }

    @Override
    public void onRender(ViewHolder holder, int position) {
        ImageView imageView = holder.getView(R.id.iv_poster);
        TextView videoName = holder.getView(R.id.tv_video_name);
        TextView descName = holder.getView(R.id.tv_video_desc);

        GlideUtils.loadImage(imageView.getContext(),R.mipmap.ic_launcher,"http://i65.tinypic.com/v3qg48.jpg",imageView);
        videoName.setText("飓风营救第一季/全集Season 1");
        descName.setText("本季看点：《飓风营救》电视剧版可被看做电影版的前传，Bryan Mills（Clive Standen）还很年轻，既没有妻子也没有孩子。他曾是一名绿色贝雷帽，因为一场个人悲剧而遭受重创，正努力摆脱心理阴影。");
    }
}
