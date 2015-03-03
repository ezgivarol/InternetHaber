package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.NewsDetailResponseBean;

public final class NewsDetailSuccessResponseEvent {
    private NewsDetailResponseBean mBean;

    public NewsDetailResponseBean getBean() {
        return mBean;
    }

    public void setBean(NewsDetailResponseBean bean) {
        mBean = bean;
    }
}
