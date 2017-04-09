package net.angrycode.wesou.component;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.angrycode.wesou.R;

import java.util.List;

public class LoadMoreWrapper<T> extends BaseSimpleRecycleAdapter<T> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private BaseSimpleRecycleAdapter<T> mInnerAdapter;
    private View mLoadMoreView;
    private TextView mLoadMoreTv;
    private int mLoadMoreLayoutId;

    private RecyclerView.OnScrollListener mOnScrollListener;
    private boolean mIsLoading = false;

    public LoadMoreWrapper(BaseSimpleRecycleAdapter<T> adapter) {
        mInnerAdapter = adapter;
        mLoadMoreLayoutId = R.layout.item_loading_more;
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (!mIsLoading && newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition >= adapter.getItemCount()) {
                        //最后一个itemView的position为adapter中最后一个数据时,说明该itemView就是底部的view了
                        //需要注意position从0开始索引,adapter.getItemCount()是数据量总数
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMoreRequested();
                        }
                        mLoadMoreView.setVisibility(View.VISIBLE);
                        mIsLoading = true;
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();

                    int count = getItemCount();
                    if (!mIsLoading && lastVisiblePosition >= count && count > 0) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMoreRequested();
                        }
//                        mLoadMoreView.setVisibility(View.VISIBLE);
                        mIsLoading = true;
                    }

                } /*else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager gridLayoutManager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
                    gridLayoutManager.findLastVisibleItemPositions()
                }*/
            }
        };
    }

    public void clear() {
        mInnerAdapter.clear();
    }

    public void setData(List<T> list) {
        mInnerAdapter.setData(list);
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        mInnerAdapter.addData(list);
        notifyDataSetChanged();
    }

    public void addData(T t) {
        mInnerAdapter.addData(t);
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return mInnerAdapter.getItem(position);
    }

    public void setLoadFinished() {
        mIsLoading = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setVisibility(View.INVISIBLE);
        }
    }

    public void setLoading() {
        mIsLoading = true;
        if (mLoadMoreView != null) {
            mLoadMoreView.setVisibility(View.VISIBLE);
        }
    }

    public void setLoadDefault() {
        mIsLoading = false;
        if (mLoadMoreView != null) {
            mLoadMoreView.setVisibility(View.VISIBLE);
            mLoadMoreTv.setText(R.string.load_more);
        }
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }


    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            mLoadMoreView = LayoutInflater.from(parent.getContext()).inflate(mLoadMoreLayoutId, parent, false);
            mLoadMoreTv = (TextView) mLoadMoreView.findViewById(R.id.tv_loading);
            ViewHolder holder = new ViewHolder(mLoadMoreView);
            holder.itemView.setVisibility(View.INVISIBLE);
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
//            if (mOnLoadMoreListener != null) {
//                mOnLoadMoreListener.onLoadMoreRequested();
//            }
            holder.itemView.setVisibility(View.INVISIBLE);
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
        recyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemLayout(int viewType) {
        return mInnerAdapter.getItemLayout(viewType);
    }

    @Override
    public void onRender(ViewHolder holder, int position) {
        mInnerAdapter.onRender(holder, position);
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mInnerAdapter.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mInnerAdapter.setOnItemLongClickListener(listener);
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }


    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }
}