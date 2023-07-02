package com.example.EmployeeDetails.EmployeeService;

import com.example.EmployeeDetails.EmployeeRepository.EmployeeRepository;
import com.example.EmployeeDetails.Exception.EmployeeDataAlreadyExistsException;
import com.example.EmployeeDetails.Exception.EmployeeDataNotExistException;
import com.example.EmployeeDetails.Model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public String createEmployee(Employee employee) {
        Employee existingEmployeeById= employeeRepository.findById(employee.getId()).orElse(null);
        Employee existingEmployeeByEmail=employeeRepository.findByEmailId(employee.getEmailId());
        if(existingEmployeeById==null && existingEmployeeByEmail==null)
        {
            employeeRepository.save(employee);
            return "Employee Data Successfully added";
        }
        else if(existingEmployeeByEmail==null && existingEmployeeById!=null)
        {
            throw new EmployeeDataAlreadyExistsException("Employee Data Already exist for id="+employee.getId());
        }
        else if(existingEmployeeByEmail!=null && existingEmployeeById==null)
        {
            throw new EmployeeDataAlreadyExistsException("Employee Email id must unique");
        }
        else {
            throw new EmployeeDataAlreadyExistsException("Employee Data Already Present for given Employee id and Email id");
        }
    }

    public Employee detailsById(int id) {
        Employee employee=employeeRepository.findById(id).orElse(null);

        if(employee!=null)
        {
            return employee;
        }
        else {
            throw new EmployeeDataNotExistException("Employee Data Not Present for id = "+id);
        }
    }

    public Employee detailsByEmailId(String emailId) {
        Employee employee=employeeRepository.findByEmailId(emailId);
        if(employee!=null)
        {
            return employee;
        }
        else {
            throw new EmployeeDataNotExistException("Employee Data Not Present for id = "+emailId);
        }
    }

    public String deleteEmployeeData(int id) {
        if(employeeRepository.existsById(id))
        {
            employeeRepository.deleteById(id);
            return "Employee Data Successfully Deleted";
        }
        else {
           throw new EmployeeDataNotExistException("Employee Data Not Present for id = "+id);
        }
    }

    public void updateEmployeeData(Employee employee) {
        Employee employee1=employeeRepository.findById(employee.getId()).orElse(null);
        if(employee1!=null) {
            employee1.setDepartment(employee.getDepartment());
            employee1.setName(employee.getName());
            employee1.setSalary(employee.getSalary());
            employee1.setEmailId(employee.getEmailId());
            employee1.setSalary(employee.getSalary());
            employee1.setLastUpdatedDate(new Date());
            employee1.setJoiningDate(employee.getJoiningDate());
            employeeRepository.save(employee1);
        }
        else {
            throw new EmployeeDataNotExistException("Employee Data Not Present for id = "+employee.getId());
        }
    }
}
