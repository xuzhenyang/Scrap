package com.idlepilot.xuzy.scrap.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.idlepilot.xuzy.scrap.controller.CardManager;
import com.idlepilot.xuzy.scrap.model.CustomDate;
import com.idlepilot.xuzy.scrap.R;
import com.idlepilot.xuzy.scrap.model.State;


public class CalendarActivity extends Activity implements OnClickListener, CalendarCard.OnCellClickListener
{
    private ViewPager mViewPager;
    private int mCurrentIndex = 500;
    private CalendarCard[] mShowViews;
    private CalendarViewAdapter<CalendarCard> adapter;
    private SlideDirection mDirection = SlideDirection.NO_SLIDE;

    enum SlideDirection
    {
        RIGHT, LEFT, NO_SLIDE;
    }

    private ImageButton preImgBtn, nextImgBtn;
    private TextView monthText;
    private TextView yearText;
    private ImageButton closeImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calendar);
        mViewPager = (ViewPager) this.findViewById(R.id.vp_calendar);
        preImgBtn = (ImageButton) this.findViewById(R.id.btnPreMonth);
        nextImgBtn = (ImageButton) this.findViewById(R.id.btnNextMonth);
        monthText = (TextView) this.findViewById(R.id.tvCurrentMonth);
        yearText = (TextView) this.findViewById(R.id.tvCurrentYear);
        closeImgBtn = (ImageButton) this.findViewById(R.id.btnClose);
        preImgBtn.setOnClickListener(this);
        nextImgBtn.setOnClickListener(this);
        closeImgBtn.setOnClickListener(this);

        CalendarCard[] views = new CalendarCard[3];
        for (int i = 0; i < 3; i++)
        {
            views[i] = new CalendarCard(this, this);
        }
        adapter = new CalendarViewAdapter<>(views);
        setViewPager();

    }

    private void setViewPager()
    {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(500);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnPreMonth:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.btnNextMonth:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
            case R.id.btnClose:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void clickDate(CustomDate date, State state)
    {
        switch (state)
        {
            case CURRENT_MONTH_DAY:
                String createTime = date.toString();
//                Toast.makeText(CalendarActivity.this, createTime, Toast.LENGTH_SHORT).show();
                if ((new CardManager(this).isThisDayHasCard(createTime)))
                {
                    Bundle data = new Bundle();
                    data.putSerializable("date", createTime);
                    Intent i  = new Intent(CalendarActivity.this, MainActivity.class);
                    i.putExtras(data);
                    startActivity(i);

                    Toast.makeText(CalendarActivity.this, "True", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(CalendarActivity.this, "False", Toast.LENGTH_SHORT).show();
                break;
            case PAST_MONTH_DAY:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case NEXT_MONTH_DAY:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
            default:
                break;
        }
    }

    @Override
    public void changeDate(CustomDate date)
    {
        monthText.setText(date.month + "月");
        yearText.setText(date.year + "年");
    }

    /**
     * 计算方向
     *
     * @param arg0
     */
    private void measureDirection(int arg0)
    {

        if (arg0 > mCurrentIndex)
        {
            mDirection = SlideDirection.RIGHT;

        } else if (arg0 < mCurrentIndex)
        {
            mDirection = SlideDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }

    // 更新日历视图
    private void updateCalendarView(int arg0)
    {
        mShowViews = adapter.getAllItems();
        if (mDirection == SlideDirection.RIGHT)
        {
            mShowViews[arg0 % mShowViews.length].rightSlide();
        } else if (mDirection == SlideDirection.LEFT)
        {
            mShowViews[arg0 % mShowViews.length].leftSlide();
        }
        mDirection = SlideDirection.NO_SLIDE;
    }


}