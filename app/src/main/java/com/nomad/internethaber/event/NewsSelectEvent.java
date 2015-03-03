package com.nomad.internethaber.event;

import com.nomad.internethaber.model.News;

public final class NewsSelectEvent {
    private News mNews;

    public void setNews(News news) {
        mNews = news;
    }

    public News getNews() {
        return mNews;
    }
}
