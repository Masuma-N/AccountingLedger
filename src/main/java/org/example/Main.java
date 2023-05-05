package org.example;



import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        DisplayScreen();

    }

    public static void DisplayScreen() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Screen Ledger Options:");
            System.out.println("[P] Make payment (Debit)");
            System.out.println("[D] Add deposit");
            System.out.println("[L] Display Ledger");
            System.out.println("[X] Exit");

            input = scanner.nextLine();
            switch (input.toUpperCase()) {// switch statement to check each choice
                case "P":
                    makePayment();
                    break;// add break at end of every case so it can check/move on to next case
                case "D":
                    addDeposit();
                    break;
                case "L":
                    Ledger.displayLedger();//look in the ledger class
                    break;
                case "X":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid entry");// restarts in case invalid entry is put in
                    break;
            }
        } while (!input.equalsIgnoreCase("x"));
    }

    public static void displayLedger(){
        Ledger.displayLedger();

    }


    private static void makePayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To make a payment enter date,time,description,vendor, amount in the following format: ");
        System.out.println("Date: YYYY-MM-DD");
        String date = scanner.nextLine();
        System.out.println("Time: HH:MM:SS");
        String time = scanner.nextLine();
        System.out.println("Description:");
        String description = scanner.nextLine();
        System.out.println("Vendor:");
        String vendor = scanner.nextLine();
        System.out.println("Payment amount:");
        double amount = scanner.nextDouble();

        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            fileWriter.write("\n" +
                    date + "|" +
                    time + "|" +
                    description + "|" +
                    vendor + "|" + "-" +
                    amount
            );
            fileWriter.close();

            System.out.println("Payment made succesfully");
        } catch (IOException e) {
            System.out.println("Error inputting data please try again");


        }


    }


    public static void addDeposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To add a deposit enter date,time,description,vendor, transaction in the following format: ");
        System.out.println("Date: YYYY-MM-DD");
        String date = scanner.nextLine();
        System.out.println("Time: HH:MM:SS");
        String time = scanner.nextLine();
        System.out.println("Description:");
        String description = scanner.nextLine();
        System.out.println("Vendor:");
        String vendor = scanner.nextLine();
        System.out.println("Transaction amount:");
        double amount = scanner.nextDouble();

        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
            fileWriter.write("\n" +
                    date + "|" +
                    time + "|" +
                    description + "|" +
                    vendor + "|" + "+" +
                    amount
            );
            fileWriter.close();

            System.out.println("Deposit made successfully");
        } catch (IOException e) {
            System.out.println("error inputting data please try again");


        }



    }
}

