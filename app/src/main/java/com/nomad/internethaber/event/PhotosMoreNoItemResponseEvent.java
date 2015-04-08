package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.PhotosResponseBean;

public final class PhotosMoreNoItemResponseEvent extends PhotosMoreResponseEvent {
    private PhotosResponseBean mBean;

    public PhotosResponseBean getBean() {
        return mBean;
    }

    public void setBean(PhotosResponseBean bean) {
        mBean = bean;
    }
}
