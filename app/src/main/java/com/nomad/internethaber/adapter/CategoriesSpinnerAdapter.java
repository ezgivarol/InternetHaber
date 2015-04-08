package com.nomad.internethaber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.Category;

import butterknife.ButterKnife;
import butterknife.InjectView;


public final class CategoriesSpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private Category[] mCategories = {
            new Category
                    .Builder()
                    .setId("2")
                    .setName("Gündem")
                    .build(),
            new Category
                    .Builder()
                    .setId("4")
                    .setName("Politika")
                    .build(),
            new Category
                    .Builder()
                    .setId("4")
                    .setName("Politika")
                    .build(),
            new Category
                    .Builder()
                    .setId("5")
                    .setName("Dünya")
                    .build()
    };

    public CategoriesSpinnerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mCategories.length;
    }

    @Override
    public Category getItem(int position) {
        return mCategories[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_category, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Category category = getItem(position);
        CharSequence name = category.getName();
        viewHolder.mTextView.setText(name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    protected static class ViewHolder {
        @InjectView(R.id.cell_category_textview)
        TextView mTextView;

        protected ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
