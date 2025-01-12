package org.example.graphic.model;

public enum PremiumPackages
{
    MONTHLY(5,30),BIMONTHLY(9,60),SEMIYEARLY(14,180);
    private final int price;
    private final int days;
    PremiumPackages(int price,int days)
    {
        this.price = price;
        this.days = days;
    }
    public int getPrice()
    {
        return price;
    }

    public int getDays() {
        return days;
    }
}
