package com.idlepilot.xuzy.scrap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2015/12/26.
 */
public class CalendarCard extends View
{

    private static final int TOTAL_COL = 7;
    private static final int TOTAL_ROW_SHORT = 5;
    private static final int TOTAL_ROW_LONG = 6;
    private int realRows;

    private Paint mCirclePaint; // 绘制圆形的画笔
    private Paint mPointPaint; // 告诉用户改天有没有记录的画笔
    private Paint mTextPaint; // 绘制文本的画笔
    private int mViewWidth; // 视图的宽度
    private int mViewHeight; // 视图的高度
    private int mCellSpace; // 单元格间距
    private Row rows[];// 行数组，每个元素代表一行
    private static CustomDate mShowDate; // 自定义的日期，包括year,month,day
    private OnCellClickListener mCellClickListener; // 单元格点击回调事件
    private int touchSlop; //
    private boolean callBackCellSpace;

    private Cell mClickCell;
    private float mDownX;
    private float mDownY;

    /**
     * 单元格点击的回调接口
     */
    public interface OnCellClickListener
    {
        void clickDate(CustomDate date, State state); // 回调点击的日期

        void changeDate(CustomDate date); // 回调滑动ViewPager改变的日期
    }

    public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CalendarCard(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public CalendarCard(Context context)
    {
        super(context);
        init(context);
    }

    public CalendarCard(Context context, OnCellClickListener listener)
    {
        super(context);
        this.mCellClickListener = listener;
        init(context);
    }

    private void init(Context context)
    {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.parseColor("#4aa5e4"));
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setStrokeWidth(2);
        mPointPaint.setColor(Color.parseColor("#fe80eb"));
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        initDate();
    }

    private void initDate()
    {
        mShowDate = new CustomDate();
        fillDate();
    }

    private void fillDate()
    {
        int monthDay = DateUtil.getCurrentMonthDay();
        int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month - 1);
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month);
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
                mShowDate.month);
        boolean isCurrentMonth = false;
        if (DateUtil.isCurrentMonth(mShowDate))
        {
            isCurrentMonth = true;
        }
        realRows = (firstDayWeek + currentMonthDays) > 35 ? TOTAL_ROW_LONG : TOTAL_ROW_SHORT;
        rows = new Row[realRows];
        int day = 0;
        for (int i = 0; i < realRows; i++)
        {
            rows[i] = new Row(i);
            for (int j = 0; j < TOTAL_COL; j++)
            {
                int position = j + i * TOTAL_COL;
                if (position >= firstDayWeek
                        && position < firstDayWeek + currentMonthDays)
                {
                    day++;
                    CustomDate date = CustomDate.modifyDayForObject(mShowDate, day);
                    //如果当天有卡片，设置为红圈
                    if ((new CardManager(getContext())).isThisDayHasCard(date.toString()))
                    {
                        if (isCurrentMonth && day == monthDay)
                        {
                            rows[i].cells[j] = new Cell(date, State.TODAY_WROTE, j, i);
                        } else
                        {
                            rows[i].cells[j] = new Cell(date, State.CURRENT_MONTH_DAY_WROTE, j, i);
                        }
                    } else
                    {
                        if (isCurrentMonth && day == monthDay)
                        {
                            rows[i].cells[j] = new Cell(date, State.TODAY_UNWROTE, j, i);
                        } else
                        {
                            rows[i].cells[j] = new Cell(date, State.CURRENT_MONTH_DAY_UNWROTE, j, i);
                        }
                    }
                } else if (position < firstDayWeek)
                {
                    rows[i].cells[j] = new Cell(new CustomDate(mShowDate.year,
                            mShowDate.month - 1, lastMonthDays
                            - (firstDayWeek - position - 1)),
                            State.PAST_MONTH_DAY, j, i);
                } else if (position >= firstDayWeek + currentMonthDays)
                {
                    rows[i].cells[j] = new Cell((new CustomDate(mShowDate.year,
                            mShowDate.month + 1, position - firstDayWeek
                            - currentMonthDays + 1)),
                            State.NEXT_MONTH_DAY, j, i);
                }
            }
        }
        mCellClickListener.changeDate(mShowDate);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        for (int i = 0; i < realRows; i++)
        {
            if (rows[i] != null)
            {
                rows[i].drawCells(canvas);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mCellSpace = Math.min(mViewHeight / TOTAL_ROW_LONG, mViewWidth / TOTAL_COL);
        if (!callBackCellSpace)
        {
            callBackCellSpace = true;
        }
        mTextPaint.setTextSize(mCellSpace / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float disX = event.getX() - mDownX;
                float disY = event.getY() - mDownY;
                if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop)
                {
                    int col = (int) (mDownX / mCellSpace);
                    int row = (int) (mDownY / mCellSpace);
                    measureClickCell(col, row);
                }
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 计算点击的单元格
     *
     * @param col
     * @param row
     */
    private void measureClickCell(int col, int row)
    {
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month);
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
                mShowDate.month);

        if (col >= TOTAL_COL || row >= realRows)
            return;
        if (mClickCell != null)
        {
            rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
        }
        if (rows[row] != null)
        {
            CustomDate date = rows[row].cells[col].date;
            mClickCell = new Cell(date,
                    rows[row].cells[col].state, rows[row].cells[col].i,
                    rows[row].cells[col].j);

            int position = mClickCell.i + mClickCell.j * TOTAL_COL;
            if (position >= firstDayWeek && position < (firstDayWeek + currentMonthDays))
            {
                mCellClickListener.clickDate(date, State.CURRENT_MONTH_DAY);
            } else if (position < firstDayWeek)
            {
                mCellClickListener.clickDate(date, State.PAST_MONTH_DAY);
            } else
            {
                mCellClickListener.clickDate(date, State.NEXT_MONTH_DAY);
            }
            // 刷新界面
            update();
        }
    }

    /**
     * 组元素
     */
    class Row
    {
        public int i;

        Row(int i)
        {
            this.i = i;
        }

        public Cell[] cells = new Cell[TOTAL_COL];

        // 绘制单元格
        public void drawCells(Canvas canvas)
        {
            for (int j = 0; j < cells.length; j++)
            {
                if (cells[j] != null)
                {
                    cells[j].drawSelf(canvas);
                }
            }
        }

    }

    /**
     * 单元格元素
     */
    class Cell
    {
        public CustomDate date;
        public State state;
        public int i;
        public int j;

        public Cell(CustomDate date, State state, int i, int j)
        {
            super();
            this.date = date;
            this.state = state;
            this.i = i;
            this.j = j;
        }

        public void drawSelf(Canvas canvas)
        {
            switch (state)
            {
                case TODAY_UNWROTE:
                    mTextPaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mCirclePaint);
                    break;
                case TODAY_WROTE:
                    mTextPaint.setColor(R.color.white);
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mCirclePaint);
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mPointPaint);
                    break;
                case CURRENT_MONTH_DAY_UNWROTE:
                    mTextPaint.setColor(Color.BLACK);
                    break;
                case CURRENT_MONTH_DAY_WROTE:
                    mTextPaint.setColor(Color.BLACK);
                    canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                            (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                            mPointPaint);
                    break;
                case PAST_MONTH_DAY:
                    mTextPaint.setColor(Color.GRAY);
                case NEXT_MONTH_DAY:
                    mTextPaint.setColor(Color.GRAY);
                    break;
                default:
                    break;
            }

            String content = date.day + "";
            canvas.drawText(content,
                    (float) ((i + 0.5) * mCellSpace - mTextPaint
                            .measureText(content) / 2), (float) ((j + 0.7)
                            * mCellSpace - mTextPaint
                            .measureText(content, 0, 1) / 2), mTextPaint);
        }
    }

    // 从左往右划，上一个月
    public void leftSlide()
    {
        if (mShowDate.month == 1)
        {
            mShowDate.month = 12;
            mShowDate.year -= 1;
        } else
        {
            mShowDate.month -= 1;
        }
        update();
    }

    // 从右往左划，下一个月
    public void rightSlide()
    {
        if (mShowDate.month == 12)
        {
            mShowDate.month = 1;
            mShowDate.year += 1;
        } else
        {
            mShowDate.month += 1;
        }
        update();
    }

    public void update()
    {
        fillDate();
        invalidate();
    }

}
