package com.mycompany.app;

import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Duration;
import java.io.FileNotFoundException;

public class App
{
    public static void main( String[] args )
    {
        CDR cdr = new CDR("cdr.txt");
        cdr.generateReports();
    }
}