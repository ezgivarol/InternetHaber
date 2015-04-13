package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.VideosResponseBean;

public final class VideosMoreSuccessResponseEvent extends VideosMoreResponseEvent {
    private VideosResponseBean mBean;

    public void setBean(VideosResponseBean bean) {
        mBean = bean;
    }

    public VideosResponseBean getBean() {
        return mBean;
    }
}
