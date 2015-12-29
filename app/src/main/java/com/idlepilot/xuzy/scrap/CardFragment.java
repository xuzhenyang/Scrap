package com.idlepilot.xuzy.scrap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idlepilot.xuzy.scrap.CardSlidePanel.CardSwitchListener;
import com.idlepilot.xuzy.scrap.CardSlidePanel.AddBtnListener;
import com.idlepilot.xuzy.scrap.model.CardDataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡片Fragment
 *
 * @author xmuSistone
 */
@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class CardFragment extends Fragment
{

    private CardSwitchListener cardSwitchListener;
    private CardSlidePanel.AddBtnListener addBtnListener;

    private String imagePaths[] = {"assets://wall01.jpg",
            "assets://wall02.jpg", "assets://wall03.jpg",
            "assets://wall04.jpg", "assets://wall05.jpg",
            "assets://wall06.jpg", "assets://wall07.jpg",
            "assets://wall08.jpg", "assets://wall09.jpg",
            "assets://wall10.jpg", "assets://wall11.jpg",
            "assets://wall12.jpg", "assets://wall01.jpg",
            "assets://wall02.jpg", "assets://wall03.jpg",
            "assets://wall04.jpg", "assets://wall05.jpg",
            "assets://wall06.jpg", "assets://wall07.jpg",
            "assets://wall08.jpg", "assets://wall09.jpg",
            "assets://wall10.jpg", "assets://wall11.jpg", "assets://wall12.jpg"}; // 24个图片资源名称

    private String text[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟", "周星驰", "赵本山", "郭德纲", "周润发", "邓超",
            "王祖蓝", "王宝强", "黄晓明", "张卫健", "徐峥", "李亚鹏", "郑伊健"}; // 24个人

    private List<CardDataItem> dataList = new ArrayList<CardDataItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.card_layout, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView)
    {
        CardSlidePanel slidePanel = (CardSlidePanel) rootView
                .findViewById(R.id.image_slide_panel);
        cardSwitchListener = new CardSwitchListener()
        {

            @Override
            public void onShow(int index)
            {
                Log.d("CardFragment", "正在显示-" + dataList.get(index).getText());
            }

            @Override
            public void onCardVanish(int index, int type)
            {
                Log.d("CardFragment", "正在消失-" + dataList.get(index).getText() + " 消失type=" + type);
            }

            @Override
            public void onItemClick(View cardView, int index)
            {
                Log.d("CardFragment", "卡片点击-" + dataList.get(index).getText());
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);


        addBtnListener = new AddBtnListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("CardFragment", "btnOnclick");
                Intent i = new Intent(getActivity(), AddCardActivity.class);
                startActivity(i);
            }
        };
        slidePanel.setAddBtnListener(addBtnListener);

        prepareDataList();
        slidePanel.fillData(dataList);
    }

    private void prepareDataList()
    {
        int num = imagePaths.length;

        for (int j = 0; j < 3; j++)
        {
            for (int i = 0; i < num; i++)
            {
                CardDataItem dataItem = new CardDataItem();
                dataItem.setText(text[i]);
                dataItem.setImagePath(imagePaths[i]);
                dataItem.setDate("2015-12-12");
                dataList.add(dataItem);
            }
        }
    }

}
