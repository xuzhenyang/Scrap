package com.idlepilot.xuzy.scrap.model;

/**
 * 卡片数据装载对象
 *
 * @author xmuSistone
 */
public class CardDataItem
{
    private String imagePath;
    private String text;
    private String date;

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
