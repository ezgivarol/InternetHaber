package com.nomad.internethaber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsListAdapter extends ArrayAdapter<News> {
    private Picasso mPicasso;

    public NewsListAdapter(Context context, List<News> objects) {
        super(context, 0, objects);

        mPicasso = Picasso.with(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_news, parent, false);
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