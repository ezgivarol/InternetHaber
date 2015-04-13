package com.nomad.internethaber.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nomad.internethaber.fragment.PhotoDetailPagerFragment;
import com.nomad.internethaber.model.PhotoDetail;

import java.util.ArrayList;

public class PhotosDetailPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<PhotoDetail> mList;

    public PhotosDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public PhotosDetailPagerAdapter(@NonNull FragmentManager fm, @NonNull ArrayList<PhotoDetail> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoDetail photoDetail = mList.get(position);

        return PhotoDetailPagerFragment.getInstance(photoDetail);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void setPhotoList(ArrayList<PhotoDetail> list) {
        this.mList = list;
    }
}