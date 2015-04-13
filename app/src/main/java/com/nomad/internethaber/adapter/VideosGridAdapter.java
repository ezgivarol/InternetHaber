package com.nomad.internethaber.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.Video;
import com.paging.gridview.PagingBaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public final class VideosGridAdapter extends PagingBaseAdapter<Video> {
    private Picasso mPicasso;
    private Context mContext;

    public VideosGridAdapter(Context context, List<Video> items) {
        super(items);

        mContext = context;
        mPicasso = Picasso.with(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Video getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    public List<Video> getItems() {
        return items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_videos_extended, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Video video = getItem(position);

        String thumbnailUrl = video.getThumbnail();
        mPicasso.load(thumbnailUrl).fit().centerCrop().into(viewHolder.mThumbnailImageView);

        String title = video.getTitle();
        Spanned spannedTitle = Html.fromHtml(title);
        viewHolder.mTitleTextView.setText(spannedTitle);

        return convertView;
    }


    protected static class ViewHolder {

        @InjectView(R.id.cell_videos_title_textview)
        TextView mTitleTextView;

        @InjectView(R.id.cell_videos_thumbnail_imageview)
        ImageView mThumbnailImageView;

        protected ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}