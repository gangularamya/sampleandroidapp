package com.ms.ramya.sampleapp;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    Context context;
    List<ImageData> postArrayList;
    List<ImageData> originalData;
    private ItemFilter mFilter = new ItemFilter();

    public CustomListAdapter(Context context, List<ImageData> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
        this.originalData =postArrayList;
    }
    public int getCount() {
        return postArrayList.size();
    }
    public Object getItem(int position) {
        return postArrayList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder {
        TextView txtPostTitle,txtPostDate,txtPostDescription;
        ImageView imgPost;
    }
    public View getView(final int position, View convertView,ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.txtPostTitle = (TextView) convertView.findViewById(R.id.txtPostTitle);
            holder.imgPost = (ImageView) convertView.findViewById(R.id.imgPost);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtPostTitle.setText(postArrayList.get(position).getContentNoFormatting());
        Picasso.with(context).load(postArrayList.get(position).getUrl()).into(holder.imgPost);


        return convertView;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<ImageData> originalList = originalData;

            int count = originalList.size();
            final List<ImageData>  newList = new ArrayList<ImageData>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = originalList.get(i).getContentNoFormatting();
                if (filterableString.toLowerCase().contains(filterString)) {
                    newList.add(originalList.get(i));
                }
            }

            results.values = newList;
            results.count = newList.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            postArrayList = (List<ImageData>) results.values;
            notifyDataSetChanged();
        }

    }
}