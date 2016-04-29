package com.bbits;

public class Condition_ranges
{
    private Conditions conditions;

    private String end_date;

    private String start_date;

    public Conditions getConditions ()
    {
        return conditions;
    }

    public void setConditions (Conditions conditions)
    {
        this.conditions = conditions;
    }

    public String getEnd_date ()
    {
        return end_date;
    }

    public void setEnd_date (String end_date)
    {
        this.end_date = end_date;
    }

    public String getStart_date ()
    {
        return start_date;
    }

    public void setStart_date (String start_date)
    {
        this.start_date = start_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [conditions = "+conditions+", end_date = "+end_date+", start_date = "+start_date+"]";
    }
}
