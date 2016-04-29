package com.bbits;


public class Calendar_months
{
private Days[] days;

private String name;

private String month;

private String year;

private Condition_ranges[] condition_ranges;

private String abbr_name;

private String dynamic_pricing_updated_at;

private String[] day_names;

public Days[] getDays ()
{
return days;
}

public void setDays (Days[] days)
{
this.days = days;
}

public String getName ()
{
return name;
}

public void setName (String name)
{
this.name = name;
}

public String getMonth ()
{
return month;
}

public void setMonth (String month)
{
this.month = month;
}

public String getYear ()
{
return year;
}

public void setYear (String year)
{
this.year = year;
}

public Condition_ranges[] getCondition_ranges ()
{
return condition_ranges;
}

public void setCondition_ranges (Condition_ranges[] condition_ranges)
{
this.condition_ranges = condition_ranges;
}

public String getAbbr_name ()
{
return abbr_name;
}

public void setAbbr_name (String abbr_name)
{
this.abbr_name = abbr_name;
}

public String getDynamic_pricing_updated_at ()
{
return dynamic_pricing_updated_at;
}

public void setDynamic_pricing_updated_at (String dynamic_pricing_updated_at)
{
this.dynamic_pricing_updated_at = dynamic_pricing_updated_at;
}

public String[] getDay_names ()
{
return day_names;
}

public void setDay_names (String[] day_names)
{
this.day_names = day_names;
}

@Override
public String toString()
{
return "ClassPojo [days = "+days+", name = "+name+", month = "+month+", year = "+year+", condition_ranges = "+condition_ranges+", abbr_name = "+abbr_name+", dynamic_pricing_updated_at = "+dynamic_pricing_updated_at+", day_names = "+day_names+"]";
}
}
