package com.example.EmployeeDetails.EmployeeRepository;

import com.example.EmployeeDetails.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByEmailId(String email_Id);
}
