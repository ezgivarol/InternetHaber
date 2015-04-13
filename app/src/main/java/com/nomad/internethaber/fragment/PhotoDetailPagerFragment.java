package com.nomad.internethaber.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.PhotoDetail;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

public class PhotoDetailPagerFragment extends BaseFragment {

    @InjectView(R.id.fragment_photo_detail_imageview)
    protected ImageView mImageView;

    private PhotoDetail mPhotoDetail;
    private Picasso mPicasso;

    public static PhotoDetailPagerFragment getInstance(PhotoDetail photoDetail) {
        PhotoDetailPagerFragment fragment = new PhotoDetailPagerFragment();
        fragment.setPhotoDetail(photoDetail);
        return fragment;
    }

    @NonNull
    @Override
    protected int getTitleResource() {
        return NO_ID;
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_photo_detail_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPicasso = Picasso.with(getContext());
        mPicasso.load(mPhotoDetail.getPhoto()).centerCrop().fit().into(mImageView);
    }

    public void setPhotoDetail(PhotoDetail photoDetail) {
        mPhotoDetail = photoDetail;
    }


}
