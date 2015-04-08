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
import com.nomad.internethaber.model.Photo;
import com.paging.gridview.PagingBaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public final class PhotosGridAdapter extends PagingBaseAdapter<Photo> {
    private Picasso mPicasso;
    private Context mContext;

    public PhotosGridAdapter(Context context, List<Photo> items) {
        super(items);

        mContext = context;
        mPicasso = Picasso.with(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Photo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    public List<Photo> getItems() {
        return items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_photos_extended, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Photo photo = getItem(position);

        String thumbnailUrl = photo.getThumbnail();
        mPicasso.load(thumbnailUrl).fit().centerCrop().into(viewHolder.mThumbnailImageView);

        String title = photo.getTitle();
        Spanned spannedTitle = Html.fromHtml(title);
        viewHolder.mTitleTextView.setText(spannedTitle);

        return convertView;
    }


    protected static class ViewHolder {

        @InjectView(R.id.cell_photos_title_textview)
        TextView mTitleTextView;

        @InjectView(R.id.cell_photos_thumbnail_imageview)
        ImageView mThumbnailImageView;

        protected ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}