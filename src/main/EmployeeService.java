
package com.example.employees.service;

import com.example.employees.entity.Employee;
import com.example.employees.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployee(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    @Async("employeeExecutor")
    public CompletableFuture<String> processEmployee(Long id) {

        String threadName =
                Thread.currentThread().getName();

        System.out.println(
                "Processing employee " + id +
                        " using " + threadName
        );

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            return CompletableFuture.failedFuture(e);
        }

        return CompletableFuture.completedFuture(
                "Employee " + id +
                        " processed by " + threadName
        );
    }
}
