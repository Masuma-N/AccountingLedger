package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;




public class Ledger {

    public static ArrayList<Transaction> transactionslist = getTransactions();//naming it transaction list and making sure we are getting the transactions


    public static ArrayList<Transaction> getTransactions() {

        String path = "./src/main/resources/transactions.csv";
        String line = "";

        ArrayList<Transaction> transactionslist = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\|");
                LocalDate date = LocalDate.parse(values[0]);
                LocalTime time = LocalTime.parse(values[1]);
                String description = values[2];
                String vendor = values[3];
                double amount = Double.parseDouble(values[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactionslist.add(transaction);
            }

        } catch (IOException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        return transactionslist;
    }


    public static void displayLedger() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Screen Ledger Options:");
            System.out.println("[A] Display Entries");
            System.out.println("[D] Display all deposits");
            System.out.println("[P] Display all payments");
            System.out.println("[R] Run a report");
            System.out.println("[H] Go back to home");


            String input = scanner.nextLine();
            switch (input.toUpperCase()) {// switch statement to check each choice
                case "A":
                    displayEntry();
                    break;// add break at end of every case so it can check/move on to next case
                case "D":
                    depositEntry();
                    break;
                case "P":
                    paymentEntry();
                    break;
                case "R":
                    reportEntry();
                    break;

                case "H":
                    Main.DisplayScreen();
                default:
                    System.out.println("Invalid entry");// restarts in case invalid entry is put in
                    break;


            }
        }
    }

    public static void displayEntry() {


        System.out.println("All Entries: ");
        for (Transaction entry : transactionslist) {
            System.out.println(
                    entry.getDate() + " " + entry.getTime() + " " + entry.getDescription()
                            + " " + entry.getVendor() + " " + entry.getAmount()

            );

        }


    }

    public static void depositEntry() {
        System.out.println("All deposits: ");
        for (Transaction entry : transactionslist) {
            if (entry.getAmount() > 0) {
                System.out.println(
                        entry.getDate() + " " + entry.getTime() + " " + entry.getDescription()
                                + " " + entry.getVendor() + " " + entry.getAmount()

                );
            }


        }
    }


    public static void paymentEntry() {
        System.out.println("All deposits: ");
        for (Transaction entry : transactionslist) {
            if (entry.getAmount() < 0) {
                System.out.println(
                        entry.getDate() + " " + entry.getTime() + " " + entry.getDescription()
                                + " " + entry.getVendor() + " " + entry.getAmount()

                );
            }


        }


    }


    public static void reportEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Run Report (Options):");
        System.out.println("[1] Month to Date");
        System.out.println("[2] Previous Month");
        System.out.println("[3] Year to Date");
        System.out.println("[4] Previous Year");
        System.out.println("[5] Search by Vendor");
        System.out.println("[0] Go back to report page");
        System.out.println("[H] Go back to home");


        String input = scanner.nextLine();
        switch (input.toUpperCase()) {// switch statement to check each choice
            case "1":
                monthDate();
                break;// add break at end of every case so it can check/move on to next case
            case "2":
                previousMonth();
                break;
            case "3":
                yearDate();
                break;
            case "4":
                previousYear();
                break;
            case "5":
                searchVendor();
                break;
            case "0":
                Ledger.reportEntry();

            case "H":
                Main.DisplayScreen();
            default:
                System.out.println("Invalid entry");// restarts in case invalid entry is put in
                break;
        }

    }

    public static void monthDate() {
        System.out.println("Here is your month to date report: ");
        LocalDate currentDate = LocalDate.now(); // this method gets the current date using 'LocalDate.now()
        LocalDate startOfTheCurrentMonth = currentDate.withDayOfMonth(1); // this method gets the first day of the month
        //using the 'withDayOfMonth(1) method
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy"); // using the DateTimeFormatter
        //class to format the dates in month to date format
        System.out.println("From" + " " + startOfTheCurrentMonth + " to " + currentDate);

        for (Transaction item : transactionslist) {
            //if we don't subtract 1 , the first day of the month will be excluded since we are using 'isAfter' method
            if (item.getDate().isAfter(startOfTheCurrentMonth.minusDays(1)) || item.getDate().isEqual(currentDate)) {
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " + item.getAmount());
            }


        }
    }

    public static void previousMonth() {
        LocalDate today = LocalDate.now();
        int previousMonthsValue = today.minusMonths(1).getMonthValue();
        System.out.println("Previous months");
        for (Transaction item : transactionslist) {
            LocalDate transactionDate = item.getDate();
            if (transactionDate.getMonthValue() == previousMonthsValue && transactionDate.getYear() == today.getYear()) {
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " + item.getAmount());
            }
        }
    }


    public static void previousYear() {
        LocalDate today = LocalDate.now();
        int previousYearValue = today.minusYears(1).getYear();
        System.out.println("Previous Year:");
        for (Transaction item : transactionslist) {
            LocalDate transactionDate = item.getDate();
            if (transactionDate.getYear() == previousYearValue && transactionDate.getYear() != today.getYear()) {
                // condition checks if transaction is in previous year and if so then prints it out
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " + item.getAmount());
            }


        }


    }

    public static void yearDate() {
        System.out.println("Here is your year to date report: ");
        LocalDate currentDate = LocalDate.now(); // this method gets the current date using 'LocalDate.now()
        LocalDate startOfTheCurrentYear = currentDate.withDayOfYear(1); // this method gets the first day of the month
        //using the 'withDayOfMonth(1) method
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy"); // using the DateTimeFormatter
        //class to format the dates in month to date format
        System.out.println("From" + " " + startOfTheCurrentYear.format(formatter) + " to " + currentDate.format(formatter));

        for (Transaction transaction : transactionslist) {
            //if we don't subtract 1 , the first day of the month will be excluded since we are using 'isAfter' method
            if (transaction.getDate().isAfter(startOfTheCurrentYear.minusDays(1)) || transaction.getDate().isEqual(currentDate)) {
                System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " +
                        transaction.getVendor() + " | " + transaction.getAmount());
            }
        }
    }

    public static void searchVendor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vendor's name: ");
        String NameofVendor = scanner.nextLine();
        for (Transaction item : transactionslist) {
            if (item.getVendor().equalsIgnoreCase(NameofVendor)) {
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " + item.getAmount());
            }
        }
    }

    private static void goBack() {
        displayLedger(); //backtoReport takes us


    }
}

