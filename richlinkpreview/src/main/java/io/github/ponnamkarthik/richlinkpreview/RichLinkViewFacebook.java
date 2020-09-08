package io.github.ponnamkarthik.richlinkpreview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;

/**
 * Created by sahil on 06-09-2020.
 */

public class RichLinkViewFacebook extends RelativeLayout {

    private View view;
    Context context;
    private MetaData meta;

    RelativeLayout relativeLayout;
    ImageView imageViewMain;
    ImageView imageViewIcon;
    TextView textViewTitle;
    TextView textViewDescription;
    TextView textViewBaseUrl;

    private String main_url;

    private boolean isDefaultClick = true;

    private RichLinkListener richLinkListener;


    public RichLinkViewFacebook(Context context) {
        super(context);
        this.context = context;
    }

    public RichLinkViewFacebook(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RichLinkViewFacebook(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RichLinkViewFacebook(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void initView() {

        if (findRelativeLayoutChild() != null) {
            this.view = findRelativeLayoutChild();
        } else {
            this.view = this;
            inflate(context, R.layout.facebook_link_layout, this);
        }

        relativeLayout = (RelativeLayout) findViewById(R.id.rich_link_card);
        imageViewMain = (ImageView) findViewById(R.id.imageViewMain);
        imageViewIcon = (ImageView) findViewById(R.id.imageViewIcon);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewBaseUrl = (TextView) findViewById(R.id.textViewBaseUrl);

        if (!meta.getImageurl().isEmpty()) {
            imageViewMain.setVisibility(VISIBLE);
            imageViewIcon.setVisibility(GONE);
            Glide.with(getContext()).load(meta.getImageurl()).into(imageViewMain);
        } else {
            imageViewMain.setVisibility(GONE);
            if (!meta.getFavicon().isEmpty()) {
                Log.d("Icon", meta.getFavicon());
                imageViewIcon.setVisibility(VISIBLE);
                Glide.with(getContext()).load(meta.getFavicon()).placeholder(R.drawable.ic_link).error(R.drawable.ic_link).into(imageViewIcon);
            } else {
                imageViewIcon.setVisibility(GONE);
            }
        }

        if (!meta.getBaseUrl().isEmpty() || !meta.getUrl().isEmpty()) {
            textViewBaseUrl.setVisibility(VISIBLE);
            if (!meta.getBaseUrl().isEmpty()) {
                textViewBaseUrl.setText(meta.getBaseUrl());
            } else {
                textViewBaseUrl.setText(meta.getUrl());
            }
        } else {
            textViewBaseUrl.setVisibility(GONE);
        }

        if (!meta.getTitle().isEmpty()) {
            textViewTitle.setVisibility(VISIBLE);
            textViewTitle.setText(meta.getTitle());
        } else {
            textViewTitle.setVisibility(GONE);
        }

        if (!meta.getDescription().isEmpty()) {
            textViewDescription.setVisibility(VISIBLE);
            textViewDescription.setText(meta.getDescription());
        } else {
            textViewDescription.setVisibility(GONE);
        }

        relativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDefaultClick) {
                    richLinkClicked();
                } else {
                    if (richLinkListener != null) {
                        richLinkListener.onClicked(view, meta);
                    } else {
                        richLinkClicked();
                    }
                }
            }
        });
    }

    private void richLinkClicked() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(main_url));
        context.startActivity(intent);
    }

    protected RelativeLayout findRelativeLayoutChild() {
        if (getChildCount() > 0 && getChildAt(0) instanceof LinearLayout) {
            return (RelativeLayout) getChildAt(0);
        }
        return null;
    }

    public void setLinkFromMeta(MetaData metaData) {
        meta = metaData;
        initView();
    }

    public MetaData getMetaData() {
        return meta;
    }


    public void setDefaultClickListener(boolean isDefault) {
        isDefaultClick = isDefault;
    }

    public void setClickListener(RichLinkListener richLinkListener1) {
        richLinkListener = richLinkListener1;
    }


    public void setLink(String url, final ViewListener viewListener) {
        main_url = url;
        RichPreview richPreview = new RichPreview(new ResponseListener() {
            @Override
            public void onData(MetaData metaData) {
                meta = metaData;

                if (meta.getTitle().isEmpty() || meta.getTitle().equals("")) {
                    viewListener.onSuccess(true);
                }

                initView();
            }

            @Override
            public void onError(Exception e) {
                viewListener.onError(e);
            }
        });
        richPreview.getPreview(url);
    }


}
