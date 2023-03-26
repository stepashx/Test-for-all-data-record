package com.mycompany.app;

public class DefaultSub extends Subscriber
{
    private long rateMinutes;
    private Float nextMinuteCost;

    DefaultSub(String input)
    {
        super(input, 0);

        this.rateMinutes = 100;
        this.nextMinuteCost = 0.5f;
    }

    @Override
    void calcCallCost(Call call)
    {
        long durationInMin = (call.DurationSeconds() + 59) / 60;

        if (this.rateMinutes > 0) {
            if (this.rateMinutes >= durationInMin) {
                this.rateMinutes -= durationInMin;
                call.setCost(durationInMin * this.nextMinuteCost);
            } else {
                call.setCost(rateMinutes * this.nextMinuteCost);
                this.nextMinuteCost = 1.5f;
                call.addCost((durationInMin - this.rateMinutes) * this.nextMinuteCost);

                this.rateMinutes = 0;
            }
        } else {
            call.setCost(durationInMin * this.nextMinuteCost);
        }

        this.total += call.getCost();
    }
}
