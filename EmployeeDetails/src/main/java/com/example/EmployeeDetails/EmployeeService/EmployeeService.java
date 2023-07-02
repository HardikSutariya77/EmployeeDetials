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
        /*getting old data from database*/
        Employee employee1=employeeRepository.findById(employee.getId()).orElse(null);
        Employee employee2=employeeRepository.findByEmailId(employee.getEmailId());

        /*check there should be email id for update data must be unique*/
        /* case :1 if we want update data other than email than we just fetch data from database and get employee1 by
                   searching on id and employee2 by searching on email id here both object is not null and id is same
                   so if condition is not executed and data gets updated
        */
        /* case :2 if we want to update email id than we first check that email id is not occupied some other employee
                   object
                   --so first findById give object of employee1 old data
                   --findByEmailId() give object for given Email-id which is we want to update
                   --case:1 email id is unique or not occupied so employee2 is having null value and if condition is
                     not executed we can use given email id
                   --case:2 email id is occupied by some other data and already present in our data base that bring
                     object that are already present in our database so it's employee2.getId() is not equal to employee1.getId()
                     and if id is same than we try update same object which is have no issue
         */

        if(employee2!=null && employee1.getId()!=employee2.getId())
            throw new EmployeeDataAlreadyExistsException("Employee Data with email id= "+employee.getEmailId()+" already present with Employee id= "+employee2.getId());


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
