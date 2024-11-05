package payrollsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Employees {
    Config conf = new Config();

    public void employeeConfig(Scanner scan) {
        int opt;
        do {
            try {
                System.out.println("\n\t=== Employee Management ===\n");
                System.out.println("1. View All Employees\n"
                        + "2. Add an Employee\n"
                        + "3. Remove an Employee\n"
                        + "4. Edit an Employee\n"
                        + "5. Go back..");

                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();

                switch (opt) {
                    case 1:
                        viewEmployees("SELECT * FROM employees");
                        break;
                    case 2:
                        addEmployee(scan);
                        break;
                    case 3:
                        deleteEmployee(scan);
                        break;
                    case 4:
                        editEmployee(scan);
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

    public void viewEmployees(String query) {
        System.out.println("\n\t\t\t\t\t\t\t   === EMPLOYEES LIST ===\n");

        String[] Headers = {"ID", "Name", "Email", "Position"};
        String[] Columns = {"id", "name", "email", "position"};

        conf.viewRecords(query, Headers, Columns);
    }

    public void addEmployee(Scanner scan) {
        System.out.println("\n\t\t=== ADD A NEW EMPLOYEE ===\n");

        System.out.print("Full Name: ");
        String name = scan.nextLine();

        System.out.print("Email: ");
        String email = scan.nextLine();

        System.out.print("Position: ");
        String position = scan.nextLine();

        String sql = "INSERT INTO employees (name, email, position) VALUES (?, ?, ?)";
        conf.addRecord(sql, name, email, position);
    }

    public void deleteEmployee(Scanner scan) {
        System.out.println("\n\t\t=== REMOVE AN EMPLOYEE ===\n");

        System.out.print("Enter Employee ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM employees WHERE id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editEmployee(Scanner scan) {
        System.out.println("\n\t\t=== EDIT AN EMPLOYEE ===\n");

        int id;
        do {
            System.out.print("\nEnter Employee ID: ");
            id = scan.nextInt();
            if (!conf.doesIDExist("employees", id)) {
                System.out.println("Employee ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("employees", id));
        scan.nextLine();

        System.out.println("Selected Record:");
        viewEmployees("SELECT * FROM employees WHERE id = " + id);

        System.out.println("Enter New Employee Details:");

        System.out.print("New Full Name: ");
        String name = scan.nextLine();

        System.out.print("New Email: ");
        String email = scan.nextLine();

        System.out.print("New Position: ");
        String position = scan.nextLine();

        String sql = "UPDATE employees SET name = ?, email = ?, position = ? WHERE id = ?";
        conf.updateRecord(sql, name, email, position, id);
    }
}
