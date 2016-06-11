package com.bbits.dto;

public class Days
{
    private Price price;

    private boolean available;

    private String date;

    public Price getPrice ()
    {
        return price;
    }

    public void setPrice (Price price)
    {
        this.price = price;
    }

    public boolean getAvailable ()
    {
        return available;
    }

    public void setAvailable (boolean available)
    {
        this.available = available;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [price = "+price+", available = "+available+", date = "+date+"]";
    }
}
