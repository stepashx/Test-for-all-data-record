package com.mycompany.app;

public class UnlimitSub extends Subscriber
{
    private int freeMinutes;
    final private int nextMinuteCost;

    UnlimitSub(String input)
    {
        super(input, 100);

        this.freeMinutes = 300;
        this.nextMinuteCost = 1;
    }

    @Override
    void calcCallCost(Call call)
    {
        long durationInMin = (call.DurationSeconds() + 59) / 60;

        if (this.freeMinutes > 0) {
            if (this.freeMinutes >= durationInMin) {
                this.freeMinutes -= durationInMin;
            } else {
                call.setCost((durationInMin - this.freeMinutes) * this.nextMinuteCost);

                this.freeMinutes = 0;
            }
        } else {
            call.setCost(durationInMin * this.nextMinuteCost);
        }

        this.total += call.getCost();
    }
}