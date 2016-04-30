package com.bbits.dto;

public class Conditions
{
    private String closed_to_departure;

    private String max_nights;

    private String closed_to_arrival;

    private String min_nights;

    public String getClosed_to_departure ()
    {
        return closed_to_departure;
    }

    public void setClosed_to_departure (String closed_to_departure)
    {
        this.closed_to_departure = closed_to_departure;
    }

    public String getMax_nights ()
    {
        return max_nights;
    }

    public void setMax_nights (String max_nights)
    {
        this.max_nights = max_nights;
    }

    public String getClosed_to_arrival ()
    {
        return closed_to_arrival;
    }

    public void setClosed_to_arrival (String closed_to_arrival)
    {
        this.closed_to_arrival = closed_to_arrival;
    }

    public String getMin_nights ()
    {
        return min_nights;
    }

    public void setMin_nights (String min_nights)
    {
        this.min_nights = min_nights;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [closed_to_departure = "+closed_to_departure+", max_nights = "+max_nights+", closed_to_arrival = "+closed_to_arrival+", min_nights = "+min_nights+"]";
    }
}
