package com.mycompany.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Call
{
    final private CallType callType;
    final private LocalDateTime startTime;
    final private LocalDateTime endTime;
    final static DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private float cost;

    public Call(String input)
    {
        String[] data = input.split(", ");

        if (data[0].equals("01")) {
            this.callType = CallType.OUTGOING;
        } else if (data[0].equals("02")) {
            this.callType = CallType.INCOMING;
        } else {
            throw new ExceptionInInitializerError("Call type is wrong");
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        try {
            this.startTime = LocalDateTime.parse(data[2], timeFormatter);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Start time has wrong format");
        }

        try {
            this.endTime = LocalDateTime.parse(data[3], timeFormatter);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("End time has wrong format");
        }

        cost = 0;
    }

    public String DurationStr()
    {
        Duration diff = Duration.between(this.startTime, this.endTime);

        return String.format("%02d:%02d:%02d", diff.getSeconds() / 3600,
                             (diff.getSeconds() % 3600) / 60,
                             (diff.getSeconds() % 60));
    }

    public long DurationSeconds()
    {
        return Duration.between(this.startTime, this.endTime).getSeconds();
    }

    public CallType getCallType()
    {
        return callType;
    }

    public String getStartTime()
    {
        return startTime.format(OUTPUT_FORMATTER);
    }

    public String getEndTime()
    {
        return endTime.format(OUTPUT_FORMATTER);
    }

    public void setCost(float cost)
    {
        this.cost = cost;
    }

    public float getCost()
    {
        return cost;
    }

    public void addCost(float cost)
    {
        this.cost += cost;
    }
}

enum CallType
{
    OUTGOING("01"),
    INCOMING("02");

    final private String code;
    CallType(String input)
    {
        code = input;
    }

    public String getCode()
    {
        return code;
    }
}