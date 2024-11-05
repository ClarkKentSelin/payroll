package payrollsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Salaries {
    Config conf = new Config();

    public void salaryConfig(Scanner scan) {
        int opt;
        do {
            try {
                System.out.println("\n\t=== Salary Management ===\n");
                System.out.println("1. View All Salaries\n"
                        + "2. Add a Salary\n"
                        + "3. Remove a Salary\n"
                        + "4. Edit a Salary\n"
                        + "5. Go back..");

                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();

                switch (opt) {
                    case 1:
                        viewSalaries("SELECT * FROM salaries");
                        break;
                    case 2:
                        addSalary(scan);
                        break;
                    case 3:
                        deleteSalary(scan);
                        break;
                    case 4:
                        editSalary(scan);
                        break;
                    case 5:
                        System.out.println("\nGoing back to Payroll Management...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
                System.out.println("\n");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                opt = -1;
            }
        } while (opt != 5);
    }

    public void viewSalaries(String query) {
        System.out.println("\n\t\t\t\t\t\t\t   === SALARIES LIST ===\n");

        String[] Headers = {"ID", "Employee ID", "Amount"};
        String[] Columns = {"id", "employee_id", "amount"};

        conf.viewRecords(query, Headers, Columns);
    }

    public void addSalary(Scanner scan) {
        System.out.println("\n\t\t=== ADD A NEW SALARY ===\n");

        int employeeId;
        do {
            System.out.print("\nEmployee ID: ");
            employeeId = scan.nextInt();
            if (!conf.doesIDExist("employees", employeeId)) {
                System.out.println("Employee ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("employees", employeeId));
        scan.nextLine();

        System.out.print("Salary Amount: ");
        double amount = scan.nextDouble();

        String sql = "INSERT INTO salaries (employee_id, amount) VALUES (?, ?)";
        conf.addRecord(sql, employeeId, amount);
    }

    public void deleteSalary(Scanner scan) {
        System.out.println("\n\t\t=== REMOVE A SALARY ===\n");

        System.out.print("Enter Salary ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM salaries WHERE id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editSalary(Scanner scan) {
        System.out.println("\n\t\t=== EDIT A SALARY ===\n");

        int id;
        do {
            System.out.print("\nEnter Salary ID: ");
            id = scan.nextInt();
            if (!conf.doesIDExist("salaries", id)) {
                System.out.println("Salary ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("salaries", id));
        scan.nextLine();

        System.out.println("Selected Record:");
        viewSalaries("SELECT * FROM salaries WHERE id = " + id);

        System.out.print("New Salary Amount: ");
        double newAmount = scan.nextDouble();

        String sql = "UPDATE salaries SET amount = ? WHERE id = ?";
        conf.updateRecord(sql, newAmount, id);
    }
}
