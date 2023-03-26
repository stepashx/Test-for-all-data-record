package com.mycompany.app;

public class MinByMinSub extends Subscriber
{
    final private Float nextMinuteCost;

    MinByMinSub(String input)
    {
        super(input, 0);

        this.nextMinuteCost = 1.5f;
    }

    @Override
    void calcCallCost(Call call)
    {
        long durationInMin = (call.DurationSeconds() + 59) / 60;

        call.setCost(durationInMin * this.nextMinuteCost);
        this.total += call.getCost();
    }
}