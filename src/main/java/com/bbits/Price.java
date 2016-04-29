package com.bbits;

public class Price
{
    private String local_currency;

    private String native_currency;

    private String native_price;

    private String local_price;

    private String type;

    private String date;

    public String getLocal_currency ()
    {
        return local_currency;
    }

    public void setLocal_currency (String local_currency)
    {
        this.local_currency = local_currency;
    }

    public String getNative_currency ()
    {
        return native_currency;
    }

    public void setNative_currency (String native_currency)
    {
        this.native_currency = native_currency;
    }

    public String getNative_price ()
    {
        return native_price;
    }

    public void setNative_price (String native_price)
    {
        this.native_price = native_price;
    }

    public String getLocal_price ()
    {
        return local_price;
    }

    public void setLocal_price (String local_price)
    {
        this.local_price = local_price;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
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
        return "ClassPojo [local_currency = "+local_currency+", native_currency = "+native_currency+", native_price = "+native_price+", local_price = "+local_price+", type = "+type+", date = "+date+"]";
    }
}
