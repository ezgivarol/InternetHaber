package com.nomad.internethaber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.NavigationItem;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public final class NavigationDrawerListAdapter extends ArrayAdapter<NavigationItem> {

    public NavigationDrawerListAdapter(Context context, List<NavigationItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_drawer, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        int textResourceId = getItem(position).getTextResource();
        CharSequence text = getContext().getString(textResourceId);
        viewHolder.mTextView.setText(text);

        int drawableResourceId = getItem(position).getDrawableResource();
        viewHolder.mTextView.setCompoundDrawablesWithIntrinsicBounds(drawableResourceId, 0, 0, 0);

        return convertView;
    }

    
    protected static class ViewHolder {

        @InjectView(R.id.cell_drawer_textview)
        TextView mTextView;

        protected ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}