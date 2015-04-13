package com.nomad.internethaber.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.PhotosDetailPagerAdapter;
import com.nomad.internethaber.bean.PhotosDetailResponseBean;
import com.nomad.internethaber.interfaces.PhotosDetailPagerRestInterface;
import com.nomad.internethaber.model.Photo;
import com.nomad.internethaber.model.PhotoDetail;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PhotoGalleryDetailActivity extends BaseActivity implements Callback<PhotosDetailResponseBean> {

    @InjectView(R.id.layout_photo_detail_viewpager)
    protected ViewPager mViewPager;

    private Photo mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPhoto = (Photo) getIntent().getExtras().get("photo");

        String id = mPhoto.getId();
        PhotosDetailPagerRestInterface restInterface = RestAdapterProvider.getInstance().create(PhotosDetailPagerRestInterface.class);
        restInterface.get(id, this);
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_photo_gallery_detail;
    }

    @Override
    public void success(PhotosDetailResponseBean bean, Response response) {
        ArrayList<PhotoDetail> photos = bean.getNews();
        PhotosDetailPagerAdapter adapter = new PhotosDetailPagerAdapter(getSupportFragmentManager());
        adapter.setPhotoList(photos);

        mViewPager.setAdapter(adapter);
    }

    @Override
    public void failure(RetrofitError error) {
        Logger.e(error);
    }
}
