package payrollsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PayrollRecords {
    Config conf = new Config();

    public void payrollRecordConfig(Scanner scan) {
        int opt;
        do {
            try {
                System.out.println("\n\t=== Payroll Records Management ===\n");
                System.out.println("1. View All Payroll Records\n"
                        + "2. Add a Payroll Record\n"
                        + "3. Remove a Payroll Record\n"
                        + "4. Edit a Payroll Record\n"
                        + "5. Go back..");

                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();

                switch (opt) {
                    case 1:
                        viewPayrollRecords("SELECT * FROM payroll");
                        break;
                    case 2:
                        addPayrollRecord(scan);
                        break;
                    case 3:
                        deletePayrollRecord(scan);
                        break;
                    case 4:
                        editPayrollRecord(scan);
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

    public void viewPayrollRecords(String query) {
        System.out.println("\n\t\t\t\t\t\t\t   === PAYROLL RECORDS LIST ===\n");

        String[] Headers = {"ID", "Salary ID", "Pay Period", "Payment Date"};
        String[] Columns = {"id", "salary_id", "pay_period", "payment_date"};

        conf.viewRecords(query, Headers, Columns);
    }

    public void addPayrollRecord(Scanner scan) {
        System.out.println("\n\t\t=== ADD A NEW PAYROLL RECORD ===\n");

        int salaryId;
        do {
            System.out.print("\nSalary ID: ");
            salaryId = scan.nextInt();
            if (!conf.doesIDExist("salaries", salaryId)) {
                System.out.println("Salary ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("salaries", salaryId));
        scan.nextLine();

        System.out.print("Pay Period: ");
        String payPeriod = scan.nextLine();

        System.out.print("Payment Date (YYYY-MM-DD): ");
        String paymentDate = scan.nextLine();

        String sql = "INSERT INTO payroll (salary_id, pay_period, payment_date) VALUES (?, ?, ?)";
        conf.addRecord(sql, salaryId, payPeriod, paymentDate);
    }

    public void deletePayrollRecord(Scanner scan) {
        System.out.println("\n\t\t=== REMOVE A PAYROLL RECORD ===\n");

        System.out.print("Enter Payroll ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM payroll WHERE id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editPayrollRecord(Scanner scan) {
        System.out.println("\n\t\t=== EDIT A PAYROLL RECORD ===\n");

        int id;
        do {
            System.out.print("\nEnter Payroll ID: ");
            id = scan.nextInt();
            if (!conf.doesIDExist("payroll", id)) {
                System.out.println("Payroll ID Doesn't Exist.");
            }
        } while (!conf.doesIDExist("payroll", id));
        scan.nextLine();

        System.out.println("Selected Record:");
        viewPayrollRecords("SELECT * FROM payroll WHERE id = " + id);

        System.out.print("New Pay Period: ");
        String newPayPeriod = scan.nextLine();

        System.out.print("New Payment Date (YYYY-MM-DD): ");
        String newPaymentDate = scan.nextLine();

        String sql = "UPDATE payroll SET pay_period = ?, payment_date = ? WHERE id = ?";
        conf.updateRecord(sql, newPayPeriod, newPaymentDate, id);
    }
}
