package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.NewsResponseBean;

public final class NewsMoreSuccessResponseEvent extends NewsMoreResponseEvent {
    private NewsResponseBean mBean;

    public void setBean(NewsResponseBean bean) {
        mBean = bean;
    }

    public NewsResponseBean getBean() {
        return mBean;
    }
}
