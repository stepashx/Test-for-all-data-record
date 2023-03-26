package com.mycompany.app;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class CDR
{
    HashMap<Long, Subscriber> subscribers;

    CDR(String fileName)
    {
        this.subscribers = new HashMap<>();

        ArrayList<String> cdrInput = new ArrayList<>();

        try {
            File cdrFile = new File(fileName);

            Scanner cdrReader = new Scanner(cdrFile);

            while (cdrReader.hasNextLine()) {
                cdrInput.add(cdrReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        for (String input: cdrInput) {
            addSubscriber(input);
        }
    }

    public void generateReports()
    {
        for (Map.Entry<Long, Subscriber> subscriber: subscribers.entrySet()) {
            subscriber.getValue().calcAllCallCost();
            subscriber.getValue().drawReport();
        }
    }

    private void addSubscriber(String input)
    {
        String[] data = input.split(", ");

        Long phone = Long.parseLong(data[1]);

        if (subscribers.containsKey(phone)) {
            subscribers.get(phone).addCall(input);
        } else {
            switch (data[4])
            {
            case("06"):
                subscribers.put(phone, new UnlimitSub(input));
                break;
                case("11"):
                    subscribers.put(phone, new MinByMinSub(input));
                    break;
                    case("03"):
                        subscribers.put(phone, new DefaultSub(input));
                        break;
                        default:
                            throw new ExceptionInInitializerError("Rate type is wrong");
            }
        }
    }
}