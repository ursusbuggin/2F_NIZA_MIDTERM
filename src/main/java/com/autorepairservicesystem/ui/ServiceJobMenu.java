package com.autorepairservicesystem.ui;

import com.autorepairservicesystem.dao.ServiceJobDao;
import com.autorepairservicesystem.model.ServiceJobEntity;

import java.util.List;
import java.util.Scanner;

/**
 * Console menu for the Auto Repair Service Job System.
 */
public class ServiceJobMenu {

    private static final String LINE = "==================================================";
    private static final String[] SERVICE_TYPES = {"OIL_CHANGE", "BRAKE_REPAIR", "TUNE_UP"};
    private static final String[] STATUSES = {"RECEIVED", "IN_PROGRESS", "READY"};

    private final Scanner scanner = new Scanner(System.in);
    private final ServiceJobDao dao = new ServiceJobDao();

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            // Read the whole line so no trailing newline is left over for later String inputs.
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    createServiceJob();
                    break;
                case "2":
                    viewActiveJobs();
                    break;
                case "3":
                    System.out.println();
                    System.out.println("Exiting system... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("\nINVALID CHOICE: Please enter 1, 2, or 3.\n");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println(LINE);
        System.out.println("          AUTO REPAIR SERVICE JOB SYSTEM");
        System.out.println(LINE);
        System.out.println("[1] Create Service Job");
        System.out.println("[2] View Active Jobs");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
    }

    private void createServiceJob() {
        System.out.println("\n--- CREATE SERVICE JOB ---");

        String name;
        do {
            System.out.print("Enter Customer Name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("ERROR: Customer name cannot be empty.");
            }
        } while (name.isEmpty());

        String serviceType = promptChoice(
                "Enter Service Type (OIL_CHANGE / BRAKE_REPAIR / TUNE_UP): ", SERVICE_TYPES);

        double cost = promptCost();

        String status = promptChoice(
                "Enter Status (RECEIVED / IN_PROGRESS / READY): ", STATUSES);

        ServiceJobEntity job = new ServiceJobEntity(name, serviceType, cost, status);
        if (dao.insert(job)) {
            System.out.println("\nSUCCESS: Repair service job created!\n");
        } else {
            System.out.println("\nFAILED: Repair service job was not saved.\n");
        }
    }

    private String promptChoice(String prompt, String[] validValues) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().toUpperCase();
            for (String valid : validValues) {
                if (valid.equals(value)) {
                    return valid;
                }
            }
            System.out.println("ERROR: Invalid input. Allowed values: " + String.join(", ", validValues));
        }
    }

    private double promptCost() {
        while (true) {
            System.out.print("Enter Estimated Cost: ");
            String value = scanner.nextLine().trim();
            try {
                double cost = Double.parseDouble(value);
                if (cost < 0) {
                    System.out.println("ERROR: Cost cannot be negative.");
                } else {
                    return cost;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a valid number (e.g., 2800.00).");
            }
        }
    }

    private void viewActiveJobs() {
        List<ServiceJobEntity> jobs = dao.findAll();
        String border = "+----+---------------------+--------------------+--------------+----------------+";
        String wideLine = "=".repeat(border.length());

        System.out.println();
        System.out.println(wideLine);
        System.out.println("                     AUTO REPAIR SERVICE JOBS");
        System.out.println(wideLine);
        System.out.println(border);
        System.out.printf("| %-2s | %-19s | %-18s | %-12s | %-14s |%n",
                "ID", "CUSTOMER NAME", "SERVICE TYPE", "EST COST", "STATUS");
        System.out.println(border);

        if (jobs.isEmpty()) {
            System.out.printf("| %-" + (border.length() - 4) + "s |%n", "No service jobs recorded yet.");
        } else {
            for (ServiceJobEntity job : jobs) {
                System.out.printf("| %-2d | %-19s | %-18s | %-12.2f | %-14s |%n",
                        job.getId(),
                        truncate(job.getCustomerName(), 19),
                        job.getServiceType(),
                        job.getEstimatedCost(),
                        job.getStatus());
            }
        }

        System.out.println(border);
        System.out.println("Total Records Displayed: " + jobs.size());
        System.out.println();
    }

    private String truncate(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }
}
