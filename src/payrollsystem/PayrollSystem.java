package payrollsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PayrollSystem {
    static Config conf = new Config();
    static Scanner scan = new Scanner(System.in);
    static Employees employee = new Employees();
    static Salaries salary = new Salaries();
    static PayrollRecords payrollRecord = new PayrollRecords();

    public static void main(String[] args) {
        int choice;

        do {
            try {
                System.out.println("\n   + Payroll Management System +\n");
                System.out.println("1. Employee Management");
                System.out.println("2. Salary Management");
                System.out.println("3. Payroll Records Management");
                System.out.println("4. Generate Reports");
                System.out.println("5. Exit");

                System.out.print("\nEnter Option: ");
                choice = scan.nextInt();
                scan.nextLine();
                System.out.println("");

                switch (choice) {
                    case 1:
                        employee.employeeConfig(scan);
                        break;
                    case 2:
                        salary.salaryConfig(scan);
                        break;
                    case 3:
                        payrollRecord.payrollRecordConfig(scan);
                        break;
                    case 4:
                        generateReports(scan);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                choice = -1;
            }
        } while (choice != 5);
    }

    public static void generateReports(Scanner scan) {
        System.out.println("\n\t\t\t\t\t\t\t     + PAYROLL REPORT +");

        employee.viewEmployees("SELECT * FROM employees");

        int employeeId;
        do {
            System.out.print("\nEmployee ID: ");
            employeeId = scan.nextInt();
            if (!conf.doesIDExist("employees", employeeId)) {
                System.out.println("Employee ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("employees", employeeId));

        String employeeName = conf.getDataFromID("employees", employeeId, "name");
        String email = conf.getDataFromID("employees", employeeId, "email");
        String position = conf.getDataFromID("employees", employeeId, "position");

        System.out.println("\n===================================================================================================================");
        System.out.printf("%45s\n\n", "+ Employee Payroll Report +");
        System.out.println("===================================================================================================================");

        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + employeeName);
        System.out.println("Email: " + email);
        System.out.println("Position: " + position);

        System.out.println("\nSalary and Payroll Information:");

        String combinedQuery = "SELECT p.id AS payroll_id, s.id AS salary_id, s.amount, p.pay_period, p.payment_date " +
                                "FROM payroll p " +
                                "JOIN salaries s ON p.salary_id = s.id " +
                                "WHERE s.employee_id = " + employeeId;

        String[] combinedHeaders = {"Payroll ID", "Salary ID", "Amount", "Pay Period", "Payment Date"};
        String[] combinedColumns = {"payroll_id", "salary_id", "amount", "pay_period", "payment_date"};

        conf.viewRecords(combinedQuery, combinedHeaders, combinedColumns);
        System.out.println("\n===================================================================================================================\n");
}

}
