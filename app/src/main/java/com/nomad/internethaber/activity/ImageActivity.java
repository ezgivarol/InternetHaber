package com.nomad.internethaber.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nomad.internethaber.R;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public final class ImageActivity extends BaseActivity {

    @InjectView(R.id.layout_image_imageview)
    protected ImageViewTouch mImageView;

    @InjectView(R.id.activity_toolbar)
    protected Toolbar mToolbar;

    private String mPhotoUrl;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPhotoUrl = getIntent().getExtras().getString("url");
        mPhotoUrl = "http://www.bealecorner.com/trv900/respat/eia1956-small.jpg";
        Picasso.with(getBaseContext()).load(mPhotoUrl).into(mImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_share:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, mPhotoUrl);
                startActivity(Intent.createChooser(share, "Share link!"));

                // TODO Read image from uri and share.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
