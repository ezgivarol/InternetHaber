package com.nomad.internethaber.fragment;

import android.support.annotation.NonNull;

import com.devspark.robototextview.widget.RobotoTextView;
import com.nomad.internethaber.R;
import com.nomad.internethaber.bean.NewsDetailResponseBean;
import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NewsDetailFailureResponseEvent;
import com.nomad.internethaber.event.NewsDetailSuccessResponseEvent;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.event.NewsSelectEvent;
import com.nomad.internethaber.event.NewsSuccessResponseEvent;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.model.NewsDetail;
import com.nomad.internethaber.task.NewsDetailAsyncTask;
import com.nomad.internethaber.view.RectangularImageView;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;


public final class NewsDetailFragment extends BaseFragment {

    @InjectView(R.id.fragment_news_detail_photo)
    protected RectangularImageView mImageView;

    @InjectView(R.id.fragment_news_detail_title_textview)
    protected RobotoTextView mTitleTextView;

    @InjectView(R.id.fragment_news_detail_content_textview)
    protected RobotoTextView mContentTextView;

    private NewsDetailAsyncTask mAsyncTask;

    @NonNull
    @Override
    protected int getTitleResource() {
        return NO_ID;
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_detail;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAsyncTask.cancel(true);
    }

    @Subscribe
    public void onNewsSelectEvent(NewsSelectEvent event) {
        News news = event.getNews();

        String id = news.getId();
        String type = news.getType();

        mAsyncTask = new NewsDetailAsyncTask();
        mAsyncTask.setNewsId(id);
        mAsyncTask.setNewsType(type);
        mAsyncTask.execute();
    }

    @Subscribe
    public void onNewsSuccessResponseEvent(NewsSuccessResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        News news = bean.getNews().get(0);

        String id = news.getId();
        String type = news.getType();

        mAsyncTask = new NewsDetailAsyncTask();
        mAsyncTask.setNewsId(id);
        mAsyncTask.setNewsType(type);
        mAsyncTask.execute();
    }

    @Subscribe
    public void onNewsFailureResponseEvent(NewsFailureResponseEvent event) {
        // TODO Set empty view.
    }

    @Subscribe
    public void onNewsDetailSuccessResponseEvent(NewsDetailSuccessResponseEvent event) {
        NewsDetailResponseBean bean = event.getBean();
        NewsDetail newsDetail = bean.getNewsDetail();

        String photo = newsDetail.getThumbnail();
        Picasso.with(getContext()).load(photo).fit().centerCrop().into(mImageView);

        CharSequence title = newsDetail.getTitle();
        mTitleTextView.setText(title);

        CharSequence content = newsDetail.getContent();
        mContentTextView.setText(content);
    }

    @Subscribe
    public void onNewsDetailFailureResponseEvent(NewsDetailFailureResponseEvent event) {
        // TODO Set empty view.
    }

}
