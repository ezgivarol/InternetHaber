package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.VideosResponseBean;

public final class VideosMoreNoItemResponseEvent extends VideosMoreResponseEvent {
    private VideosResponseBean mBean;

    public VideosResponseBean getBean() {
        return mBean;
    }

    public void setBean(VideosResponseBean bean) {
        mBean = bean;
    }
}
