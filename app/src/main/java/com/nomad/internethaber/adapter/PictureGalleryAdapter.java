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

public class PictureGalleryAdapter extends PagingBaseAdapter<News> {
    Context mContext;
    Picasso mPicasso;

    public PictureGalleryAdapter(Context context, List<News> items) {
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
        return items.get(position).hashCode();
    }

    public List<News> getItems() {
        return items;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_picture_gallery, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        News news = getItem(position);

        String thumbnailUrl = news.getThumbnail();
        mPicasso.load(thumbnailUrl).fit().centerCrop().into(viewHolder.mPictureGalleryImageView);

        return convertView;


    }


    protected static class ViewHolder {

        @InjectView(R.id.cell_picture_gallery)
        ImageView mPictureGalleryImageView;

        protected ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
