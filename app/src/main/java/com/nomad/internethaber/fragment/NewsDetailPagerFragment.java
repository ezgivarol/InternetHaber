package com.nomad.internethaber.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;
import com.nomad.internethaber.R;
import com.nomad.internethaber.activity.ImageActivity;
import com.nomad.internethaber.bean.NewsDetailResponseBean;
import com.nomad.internethaber.interfaces.NewsDetailPagerRestInterface;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.model.NewsDetail;
import com.nomad.internethaber.provider.RestAdapterProvider;
import com.nomad.internethaber.view.RectangularedImageView;
import com.nomad.internethaber.view.StatusBarView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public final class NewsDetailPagerFragment extends BaseFragment implements Callback<NewsDetailResponseBean>, com.squareup.picasso.Callback, Palette.PaletteAsyncListener {

    @InjectView(R.id.fragment_news_detail_pager_photo_view)
    protected RectangularedImageView mPhotoView;

    @InjectView(R.id.fragment_news_detail_pager_title_view)
    protected RobotoTextView mTitleTextView;

    @InjectView(R.id.fragment_news_detail_pager_content_view)
    protected RobotoTextView mContentTextView;

    @InjectView(R.id.fragment_news_detail_pager_statusbar_view)
    protected StatusBarView mStatusBarView;

    private News mNews;
    private NewsDetail mNewsDetail;
    private boolean shouldRun;
    private Picasso mPicasso;

    public static NewsDetailPagerFragment getInstance(News news) {
        NewsDetailPagerFragment fragment = new NewsDetailPagerFragment();
        fragment.setNews(news);
        return fragment;
    }

    @NonNull
    @Override
    protected int getTitleResource() {
        return NO_ID;
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_detail_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPicasso = Picasso.with(getContext());

        String id = mNews.getId();
        String type = mNews.getType();

        NewsDetailPagerRestInterface newsDetailPagerRestInterface = RestAdapterProvider.getInstance().create(NewsDetailPagerRestInterface.class);
        newsDetailPagerRestInterface.get(id, type, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        shouldRun = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldRun = false;
    }

    @OnClick(R.id.fragment_news_detail_pager_photo_view)
    public void onPhotoClicked(RectangularedImageView view) {
        String photoUrl = mNewsDetail.getPhoto();
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.putExtra("url", photoUrl);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "image");
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    public void setNews(News news) {
        mNews = news;
    }

    @Override
    public void success(NewsDetailResponseBean bean, Response response) {
        if (!shouldRun)
            return;

        if (bean == null)
            return;

        mNewsDetail = bean.getNewsDetail();

        String photoUrl = mNewsDetail.getThumbnail();
        mPicasso.load(photoUrl).fit().centerCrop().into(mPhotoView, this);

        String title = mNewsDetail.getTitle();
        Spanned spannedTitle = Html.fromHtml(title);
        mTitleTextView.setText(spannedTitle);

        String content = mNewsDetail.getContent();
        Spanned spannedContent = Html.fromHtml(content);
        mContentTextView.setText(spannedContent);
    }

    @Override
    public void failure(RetrofitError error) {
        String errorMessage = error.getMessage();
        Logger.e(errorMessage);
    }

    @Override
    public void onSuccess() {
        if (mPhotoView == null)
            return;

        BitmapDrawable drawable = (BitmapDrawable) mPhotoView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Palette.generateAsync(bitmap, this);
    }

    @Override
    public void onError() {
        String errorMessage = "Could not get vibrant color.";
        Logger.e(errorMessage);
    }

    @Override
    public void onGenerated(Palette palette) {
        if (!shouldRun)
            return;

        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

        if (vibrantSwatch == null)
            return;

        int backgroundColor = vibrantSwatch.getRgb();
        mTitleTextView.setBackgroundColor(backgroundColor);
        mStatusBarView.setBackgroundColor(backgroundColor);
    }

}
