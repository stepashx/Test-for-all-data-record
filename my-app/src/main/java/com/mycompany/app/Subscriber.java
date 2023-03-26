package com.mycompany.app;

import java.io.*;
import java.util.*;

abstract class Subscriber
{
    String phone;
    ArrayList<Call> calls;
    RateType rateType;
    protected float total;

    abstract void calcCallCost(Call call);

    public Subscriber(String input, float total)
    {
        String[] data = input.split(", ");

        this.phone = data[1];
        calls = new ArrayList<>();

        switch (data[4])
        {
            case("06"):
                this.rateType = RateType.UNLIMIT;
                break;
                case("11"):
                    this.rateType = RateType.MINUTE_BY_MINUTE;
                    break;
                    case("03"):
                        this.rateType = RateType.DEFUALT;
                        break;
                        default:
                            throw new ExceptionInInitializerError("Rate type is wrong");
        }

        addCall(input);

        this.total = total;
    }

    void addCall(String input)
    {
        Call call = new Call(input);
        calls.add(call);
    }

    void calcAllCallCost() {
        for (Call call: calls) {
            calcCallCost(call);
        }
    }

    public void drawReport()
    {
        try {
            String filename = String.format("./reports/%s.txt", this.phone);

            File cdrFile = new File(filename);
            cdrFile.createNewFile();

            PrintStream cdrStream = new PrintStream(cdrFile);

            System.setOut(cdrStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(String.format("Tariff index: %s", this.rateType.getCode()));
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(String.format("Report for phone number %s:", this.phone));
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("| Call Type |   Start Time        |     End Time        | Duration | Cost  |");
        System.out.println("----------------------------------------------------------------------------");

        for (Call call: calls) {
            System.out.println(String.format("|     %s    | %s | %s | %s |  %.2f |",
                                             call.getCallType().getCode(), call.getStartTime(),
                                             call.getEndTime(), call.DurationStr(), call.getCost()));
        }

        System.out.println("----------------------------------------------------------------------------");
        System.out.println(String.format("|                                           Total Cost: |     %.2f rubles |", this.total));
        System.out.println("----------------------------------------------------------------------------");
    }
}

enum RateType
{
    UNLIMIT("06"),
    MINUTE_BY_MINUTE("11"),
    DEFUALT("03");

    final private String code;

    RateType(String input)
    {
        this.code = input;
    }

    public String getCode()
    {
        return code;
    }
}