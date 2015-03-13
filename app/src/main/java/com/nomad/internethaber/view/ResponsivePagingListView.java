package com.nomad.internethaber.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.nomad.internethaber.annotation.Platform;
import com.nomad.internethaber.application.InternetHaberApplication;
import com.nomad.internethaber.event.NewsItemClickEvent;
import com.nomad.internethaber.provider.BusProvider;
import com.paging.listview.PagingListView;

public final class ResponsivePagingListView extends PagingListView implements AdapterView.OnItemClickListener {

    public ResponsivePagingListView(Context context) {
        super(context);

        init();
    }

    public ResponsivePagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ResponsivePagingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        setOnItemClickListener(this);
    }

    @Platform(device = Platform.Device.BOTH)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean isTablet = InternetHaberApplication.getResponsiveController().isTablet();

        NewsItemClickEvent event = NewsItemClickEvent.newInstance(isTablet);
        event.setPosition(position);
        BusProvider.getInstance().post(event);
    }
}
