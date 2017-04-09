package net.angrycode.wesou.ui.adapter;

import android.widget.TextView;

import net.angrycode.wesou.R;
import net.angrycode.wesou.bean.TorrentProject;
import net.angrycode.wesou.component.BaseSimpleRecycleAdapter;
import net.angrycode.wesou.component.ViewHolder;
import net.angrycode.wesou.utils.MagnetUtils;
import net.angrycode.wesou.utils.Utils;

/**
 * Created by lancelot on 2017/4/4.
 */

public class TorrentAdapter extends BaseSimpleRecycleAdapter<TorrentProject> {
    public TorrentAdapter() {
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.item_torrent;
    }

    @Override
    public void onRender(ViewHolder holder, int position) {
        TextView titleTv = holder.getView(R.id.tv_title);
        TextView categoryTv = holder.getView(R.id.tv_category);
        TextView lengthTv = holder.getView(R.id.tv_length);
        TextView datetimeTv = holder.getView(R.id.tv_datetime);
        TextView magnetTv = holder.getView(R.id.tv_magnet);

        TorrentProject torrentProject = getItem(position);
        titleTv.setText(torrentProject.getTitle());
        categoryTv.setText("类型："+torrentProject.getCategory());
        lengthTv.setText("大小："+Utils.getReadableLength(torrentProject.getLength()));
        datetimeTv.setText(torrentProject.getPubDate());
        magnetTv.setText(torrentProject.getMagnetLink());
    }
}
