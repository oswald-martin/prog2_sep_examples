package ch.zhaw.prog2.functional.streaming;

import ch.zhaw.prog2.functional.streaming.finance.CurrencyAmount;
import ch.zhaw.prog2.functional.streaming.finance.Payment;
import ch.zhaw.prog2.functional.streaming.humanresource.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PraktikumExample_Company {
    private final List<Employee> employeeList;

    public PraktikumExample_Company(List<Employee> employeeList) {
        Objects.requireNonNull(employeeList);
        this.employeeList = employeeList;
    }

    /**
     * This method is provided by lecturer - do not change
     * Getter for all employees.
     *
     * @return List of employees, never {@code null}
     */
    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(employeeList);
    }

    /*
     * Aufgabe a1)
     */
    public List<String> getDistinctFirstnamesOfEmployees() {
        return getAllEmployees().stream().map(employee -> employee.getFirstName()) // or Employee::getFirstName
            .distinct()
            .collect(Collectors.toList());
    }

    /*
     * Aufgabe a2)
     */
    public String[] getDistinctLastnamesOfEmployees() {
        return getAllEmployees().stream().map(employee -> employee.getLastName()) // or Employee::getLastName
            .distinct()
            .toArray(String[]::new);
    }

    /*
     * Aufgabe b)
     */
    public List<Employee> getEmployeesWorkingForCompany() {
        return getAllEmployees().stream()
            .filter(employee -> employee.isWorkingForCompany()) // or Employee::isWorkingForcompany
            .collect(Collectors.toList());
    }

    /*
     * Aufgabe c) - Test in Klasse CompanyTestStudent
     */
    public List<Employee> getEmployeesByPredicate(Predicate<Employee> filterPredicate) {
        return getAllEmployees().stream().filter(filterPredicate).collect(Collectors.toList());
    }

    /**
     * This method is provided by lecturer - do not change
     * Create List of payments for employees which are selected by the employeePredicate
     *
     * @param employeePredicate Predicate-Function that returns true for all Employee which
     *                          get a payment
     * @return list of Payments
     */
    public List<Payment> getPayments(Predicate<Employee> employeePredicate) {
        List<Payment> paymentList = new ArrayList<>();
        for(Employee employee: employeeList) {
            if (employeePredicate.test(employee)) {
                Payment payment = new Payment();
                CurrencyAmount salary = employee.getYearlySalary();
                int paymentsPerYear = employee.getPaymentsPerYear().getValue();
                salary = salary.createModifiedAmount(amount -> amount / paymentsPerYear);
                payment.setCurrencyAmount(salary).setBeneficiary(employee).setTargetAccount(employee.getAccount());
                paymentList.add(payment);
            }
        }
        return paymentList;
    }

    /*
     * Aufgabe g1)
     */
    public List<Payment> getPayments(Predicate<Employee> employeePredicate, Function<Employee, Payment> paymentForEmployee) {
        return employeeList.stream()
            .filter(employeePredicate)
            .map(paymentForEmployee)
            .collect(Collectors.toList());
    }

    /*
     * Aufgabe g2)
     */
    public static final Function<Employee, Payment> paymentForEmployeeJanuary = employee -> {
        Payment payment = new Payment();
        CurrencyAmount salary = employee.getYearlySalary();
        int paymentsPerYear = employee.getPaymentsPerYear().getValue();
        salary = salary.createModifiedAmount(amount -> amount / paymentsPerYear);
        payment.setCurrencyAmount(salary).setBeneficiary(employee).setTargetAccount(employee.getAccount());
        return payment;
    };

    /*
     * Aufgabe g3)
     */
    public static final Function<Employee, Payment> paymentForEmployeeDecember = employee -> {
        Payment payment = new Payment();
        CurrencyAmount salary = employee.getYearlySalary();
        int paymentsPerYear = employee.getPaymentsPerYear().getValue();
        salary = salary.createModifiedAmount(amount -> (paymentsPerYear == 13 ? 2 : 1) * amount / paymentsPerYear);
        payment.setCurrencyAmount(salary).setBeneficiary(employee).setTargetAccount(employee.getAccount());
        return payment;
    };
}
