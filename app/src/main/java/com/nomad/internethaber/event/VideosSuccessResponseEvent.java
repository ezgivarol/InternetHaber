package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.VideosResponseBean;

public final class VideosSuccessResponseEvent extends NewsResponseEvent {
    private VideosResponseBean mBean;

    public void setBean(VideosResponseBean bean) {
        mBean = bean;
    }

    public VideosResponseBean getBean() {
        return mBean;
    }
}
