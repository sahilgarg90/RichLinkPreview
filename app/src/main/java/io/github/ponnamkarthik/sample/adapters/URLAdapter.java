package io.github.ponnamkarthik.sample.adapters;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import io.github.ponnamkarthik.richlinkpreview.MetaData;
import io.github.ponnamkarthik.richlinkpreview.RichLinkListener;
import io.github.ponnamkarthik.richlinkpreview.RichLinkViewFacebook;
import io.github.ponnamkarthik.richlinkpreview.ViewListener;
import io.github.ponnamkarthik.sample.R;

/**
 * Created by ponna on 04-03-2018.
 */

public class URLAdapter extends BaseAdapter {

    //RichLinkView richLinkView;
    Context context;
    ArrayList<URL> urls;
    String currURL="";

    ArrayList<MetaData> metaData= new ArrayList<>();

    public URLAdapter(Context context, ArrayList<URL> urls){
        this.context = context;
        this.urls = urls;
        for(int i = 0; i < urls.size(); i++) {
            metaData.add(null);
        }
    }

    @Override
    public int getCount() {
        return ((urls == null) ? 0 : urls.size());
    }

    @Override
    public Object getItem(int i) {
        return urls.get(i);
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        RichLinkViewFacebook richLinkView;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_list, viewGroup, false);

            viewHolder.richLinkView = (RichLinkViewFacebook) view.findViewById(R.id.richLinkView);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(urls.get(i)!=null) {
            final URL url = urls.get(i);
            if(metaData.get(i) != null) {
                viewHolder.richLinkView.setLinkFromMeta(metaData.get(i));
            } else {
                viewHolder.richLinkView.setLink(url.toString(), new ViewListener() {
                    @Override
                    public void onSuccess(boolean status) {
                        try {
                            metaData.set(i, viewHolder.richLinkView.getMetaData());
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.i("Karthik", "Error:" + e);
                    }
                });
            }
            viewHolder.richLinkView.setDefaultClickListener(false);
            viewHolder.richLinkView.setClickListener(new RichLinkListener() {


                @Override
                public void onClicked(View view, MetaData meta) {
                    //do stuff

                    Toast.makeText(context, meta.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        return view;
    }

}
