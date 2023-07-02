package com.example.EmployeeDetails.EmployeeController;

import com.example.EmployeeDetails.EmployeeService.EmployeeService;
import com.example.EmployeeDetails.Exception.EmployeeDataAlreadyExistsException;
import com.example.EmployeeDetails.Exception.EmployeeDataNotExistException;
import com.example.EmployeeDetails.Exception.ErrorResponse;
import com.example.EmployeeDetails.Model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
@RestController
@RequestMapping("/EmployeeData")
public class EmployeeController {

    public Logger logger= LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create")
    public String createEmployee(@RequestBody Employee employee){
        logger.info("createEmployee method called");
        return employeeService.createEmployee(employee);

    }

    @GetMapping("/detailsById")
    public Employee detailsById(@RequestParam int id){
        logger.info("detailsById method called");
        return employeeService.detailsById(id);
    }

    @GetMapping("detailsByEmailId")
    public ResponseEntity<Employee> detailsByEmail(@RequestParam String emailId){
        logger.info("detailsByEmail method called");
        Employee employee=null;
        employee=employeeService.detailsByEmailId(emailId);
        try{
            if(employee==null)
                throw new Exception("Employee Data Not Present");
            else
                return new ResponseEntity(employee,HttpStatus.ACCEPTED);
        }
        catch(Exception exception){
            System.out.println(exception);
        }
        return null;
    }

    @PutMapping("update")
    public ResponseEntity<String> updateEmployeeData(@RequestBody Employee employee){
        logger.info("updateEmployeeData method called");
        employeeService.updateEmployeeData(employee);
        return new ResponseEntity("Employee data successfully updated",HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteEmployeeData(@RequestParam int id){
        logger.info("deleteEmployeeData method called");
            String response= employeeService.deleteEmployeeData(id);
        return new ResponseEntity(response,HttpStatus.ACCEPTED);
    }


        @ExceptionHandler(value = EmployeeDataAlreadyExistsException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        public ErrorResponse handleEmployeeDataAlreadyExistsException(EmployeeDataAlreadyExistsException e){
            return new ErrorResponse(HttpStatus.CONFLICT.value(),e.getMessage());
        }

        @ExceptionHandler(value = EmployeeDataNotExistException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ErrorResponse handleEmployeeDataNotExistException(EmployeeDataNotExistException e){
            return new ErrorResponse(HttpStatus.NOT_FOUND.value(),e.getMessage());
        }




}
