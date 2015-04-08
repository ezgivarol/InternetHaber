package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.PhotosResponseBean;

public final class PhotosSuccessResponseEvent extends NewsResponseEvent {
    private PhotosResponseBean mBean;

    public void setBean(PhotosResponseBean bean) {
        mBean = bean;
    }

    public PhotosResponseBean getBean() {
        return mBean;
    }
}
