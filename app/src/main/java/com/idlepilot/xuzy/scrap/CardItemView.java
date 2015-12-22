package com.idlepilot.xuzy.scrap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 卡片View项
 * @author xmuSistone
 */
@SuppressLint("NewApi")
public class CardItemView extends LinearLayout
{

    public ImageView imageView;
    private TextView textTv;
    private TextView dateTv;

    public CardItemView(Context context) {
        this(context, null);
    }

    public CardItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.card_item, this);
        imageView = (ImageView) findViewById(R.id.card_image_view);
        textTv = (TextView) findViewById(R.id.card_text);
        dateTv = (TextView) findViewById(R.id.card_date);
    }

    public void fillData(CardDataItem itemData) {
        ImageLoader.getInstance().displayImage(itemData.imagePath, imageView);
        textTv.setText(itemData.text);
        dateTv.setText(itemData.date);
    }
}
