package com.example.dell.artravel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_WEBVIEW =1;
    public static final int VIEW_TYPE_IMAGE_TEXT = 2;
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        switch (viewType) {
            case VIEW_TYPE_IMAGE_TEXT:
                holder = new BaseViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.item_text_image, parent,
                        false));
                break;
            case VIEW_TYPE_WEBVIEW:
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                WebView web = new WebView(parent.getContext());
                web.setLayoutParams(lp);

                String url="https://www.booking.com/index.zh-cn.html?aid=397647;label=bai408jc-1DCAEoggI46AdIM1gDaDGIAQGYASu4ARfIAQzYAQPoAQGIAgGoAgM;sid=06e085e5f3da6185b4ef09c6455c9172;keep_landing=1&sb_price_type=total&";
                WebSettings settings = web.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setDomStorageEnabled(true);

                web.loadUrl(url);
                holder = new BaseViewHolder(web);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_WEBVIEW;
        }else{
            return VIEW_TYPE_IMAGE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return 500;
    }

}
