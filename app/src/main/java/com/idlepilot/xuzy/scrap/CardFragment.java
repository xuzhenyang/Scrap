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
                Log.d("CardFragment", "正在显示-" + dataList.get(index).getContent());
            }

            @Override
            public void onCardVanish(int index, int type)
            {
                Log.d("CardFragment", "正在消失-" + dataList.get(index).getContent() + " 消失type=" + type);
            }

            @Override
            public void onItemClick(View cardView, int index)
            {
                Log.d("CardFragment", "卡片点击-" + dataList.get(index).getContent());
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
        CardManager cm = new CardManager(getActivity());
        cm.clearCardTable();
        CardDataItem card1 = new CardDataItem("assets://wall01.jpg", "test1", "2015-12-30");
        CardDataItem card2 = new CardDataItem("assets://wall02.jpg", "test2", "2015-12-30");
        CardDataItem card3 = new CardDataItem("assets://wall03.jpg", "test3", "2015-12-30");
        CardDataItem card4 = new CardDataItem("assets://wall04.jpg", "test4", "2015-12-30");
        CardDataItem card5 = new CardDataItem("assets://wall05.jpg", "test5", "2015-12-30");
        cm.addCard(card1);
        cm.addCard(card2);
        cm.addCard(card3);
        cm.addCard(card4);
        cm.addCard(card5);
        dataList = cm.loadAllCard();
    }

}
