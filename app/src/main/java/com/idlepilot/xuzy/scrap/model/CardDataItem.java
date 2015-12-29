package com.idlepilot.xuzy.scrap.model;

/**
 * 卡片数据装载对象
 *
 * @author xmuSistone
 */
public class CardDataItem
{
    private String imagePath;
    private String content;
    private String date;

    public CardDataItem()
    {

    }

    public CardDataItem(String imagePath, String content, String date)
    {
        this.imagePath = imagePath;
        this.content = content;
        this.date = date;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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
