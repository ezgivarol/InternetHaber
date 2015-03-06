package com.nomad.internethaber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.News;
import com.paging.listview.PagingBaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsListAdapter extends PagingBaseAdapter<News> {
    private Picasso mPicasso;
    private Context mContext;

    public NewsListAdapter(Context context, List<News> items) {
        super(items);

        mContext = context;
        mPicasso = Picasso.with(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public News getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_news_extended, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        News news = getItem(position);

        String thumbnailUrl = news.getThumbnail();
        mPicasso.load(thumbnailUrl).fit().centerCrop().into(viewHolder.mThumbnailImageView);

        CharSequence title = news.getTitle();
        viewHolder.mTitleTextView.setText(title);

        CharSequence spot = news.getSpot();
        viewHolder.mSpotTextView.setText(spot);

        return convertView;
    }


    protected static class ViewHolder {

        @InjectView(R.id.cell_news_title_textview)
        TextView mTitleTextView;

        @InjectView(R.id.cell_news_spot_textview)
        TextView mSpotTextView;

        @InjectView(R.id.cell_news_thumbnail_imageview)
        ImageView mThumbnailImageView;

        protected ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}