package com.idlepilot.xuzy.scrap.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idlepilot.xuzy.scrap.controller.CardManager;
import com.idlepilot.xuzy.scrap.controller.CardSlidePanel;
import com.idlepilot.xuzy.scrap.controller.CardSlidePanel.CardSwitchListener;
import com.idlepilot.xuzy.scrap.controller.CardSlidePanel.AddBtnListener;
import com.idlepilot.xuzy.scrap.R;
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
    private AddBtnListener addBtnListener;

    private List<CardDataItem> dataList = new ArrayList<CardDataItem>();

    private String date = null;
    private int cardIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //获取CalendarActivity的数据
        Intent intent = getActivity().getIntent();
        this.date = (String) intent.getSerializableExtra("date");
        Log.d("CardFragment", "onCreate传入日期 ：" + date);

        View rootView = inflater.inflate(R.layout.card_layout, null);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView)
    {
        final CardSlidePanel slidePanel = (CardSlidePanel) rootView
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
//                Log.d("CardFragment", "btnOnclick");
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
        dataList = cm.loadAllCard();
    }

}
