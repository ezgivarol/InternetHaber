package com.nomad.internethaber.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ScrollView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.nineoldandroids.animation.Animator;
import com.nomad.internethaber.R;
import com.nomad.internethaber.activity.ImageActivity;
import com.nomad.internethaber.bean.NewsDetailResponseBean;
import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NewsDetailFailureResponseEvent;
import com.nomad.internethaber.event.NewsDetailSuccessResponseEvent;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.event.NewsMoreNoItemResponseEvent;
import com.nomad.internethaber.event.NewsSelectEvent;
import com.nomad.internethaber.event.NewsSuccessResponseEvent;
import com.nomad.internethaber.helper.SelectedImageHelper;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.model.NewsDetail;
import com.nomad.internethaber.task.NewsDetailAsyncTask;
import com.nomad.internethaber.view.RectangularImageView;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;
import io.codetail.animation.ViewAnimationUtils;


public final class NewsDetailFragment extends BaseFragment {

    @InjectView(R.id.fragment_news_detail_photo)
    protected RectangularImageView mImageView;

    @InjectView(R.id.fragment_news_detail_title_textview)
    protected RobotoTextView mTitleTextView;

    @InjectView(R.id.fragment_news_detail_content_textview)
    protected RobotoTextView mContentTextView;

    @InjectView(R.id.fragment_news_detail_scrollview)
    protected ScrollView mScrollView;

    private NewsDetailAsyncTask mAsyncTask;
    private NewsDetail mNewsDetail;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAsyncTask.cancel(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_news_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                String url = mNewsDetail.getUrl();
                String title = mNewsDetail.getTitle();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType(url);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "titlet");
                startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
        float finalRadius = Math.max(mScrollView.getWidth(), mScrollView.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(mScrollView, 0, 0, 0, finalRadius);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        circularReveal.start();

        NewsDetailResponseBean bean = event.getBean();
        mNewsDetail = bean.getNewsDetail();

        String photo = mNewsDetail.getThumbnail();
        Picasso.with(getContext()).load(photo).fit().centerCrop().into(mImageView);

        String title = mNewsDetail.getTitle();
        Spanned spannedTitle = Html.fromHtml(title);
        mTitleTextView.setText(spannedTitle);

        String content = mNewsDetail.getContent();
        Spanned spannedContent = Html.fromHtml(content);
        mContentTextView.setText(spannedContent);
    }

    @Subscribe
    public void onNewsDetailFailureResponseEvent(NewsDetailFailureResponseEvent event) {
        // TODO Set empty view.
    }

    @OnClick(R.id.fragment_news_detail_photo)
    public void onPhotoClicked(RectangularImageView view) {
        Drawable drawable = view.getDrawable();
        if (drawable == null)
            return;

        SelectedImageHelper.setPhoto(drawable);

        Intent intent = new Intent(getContext(), ImageActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "image");
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

}
