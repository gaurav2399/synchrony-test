package org.synchrony;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synchrony.model.ApiResponse;
import org.synchrony.model.EmployeeRequestDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/")
public class ApiController {

    private final static Logger LOG = LoggerFactory.getLogger(ApiController.class);
    private final EmployeeManager employeeManager;
    private final EmployeeSmartManager employeeSmartManager;

    public ApiController(EmployeeManager employeeManager, EmployeeSmartManager employeeSmartManager) {
        this.employeeManager = employeeManager;
        this.employeeSmartManager = employeeSmartManager;
    }

    @PostMapping("/addEmployee")
    public ApiResponse<Employee> addEmployee(@RequestBody EmployeeRequestDto employee) {
        ApiResponse<Employee> apiResponse = new ApiResponse<>();
        try {
            Employee employee2 = employeeManager.addEmployee(employee);

            if(employee2 != null){
                LOG.info("employee we get name : {}", employee2.getName());
                apiResponse.setData(employee2);
                apiResponse.setSuccess(true);
                apiResponse.setMessage("Employee Added");
            }else{
                apiResponse.setMessage("Employee not added");
                LOG.error("employee not added");
            }
        } catch (InterruptedException e) {
            apiResponse.setMessage("Exception " + e.getMessage());
        }
        return apiResponse;
    }

    @GetMapping("/test")
    public ResponseEntity<String> checkApi(){
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping("/addEmployees")
    public ApiResponse<String> addEmployees(){
        LOG.info("Adding employees to DB");
        ApiResponse<String> apiResponse = new ApiResponse<>();
        List<Employee> employees = getDemoEmployees();
        boolean res = false;
        try {
            res = employeeSmartManager.addEmployees(employees);
            LOG.info("All employees added to DB");
            apiResponse.setSuccess(res);
        } catch (InterruptedException e) {
            apiResponse.setMessage(e.getMessage());
        }
        return apiResponse;
    }

    @GetMapping("/getEmployee/{phone}")
    public ApiResponse<Employee> getEmployeeByPhone(@PathVariable String phone){
        ApiResponse<Employee> apiResponse = new ApiResponse<>();
        try {
            Optional<Employee> employee = employeeManager.findEmployeeByPhone(phone);
            if(employee.isPresent()){
                apiResponse.setSuccess(true);
                apiResponse.setData(employee.get());
            }
        } catch (InterruptedException e) {
            apiResponse.setMessage(e.getMessage());
        }
        return apiResponse;
    }

    @DeleteMapping("/deleteEmployee/{phone}")
    public ApiResponse<Employee> deleteEmployeeByPhone(@PathVariable String phone){
        ApiResponse<Employee> apiResponse = new ApiResponse<>();
        try {
            boolean res = employeeManager.deleteEmployeeByPhone(phone);
            apiResponse.setSuccess(res);
        } catch (InterruptedException e) {
            apiResponse.setMessage(e.getMessage());
        }
        return apiResponse;
    }

    private List<Employee> getDemoEmployees(){
        return Arrays.asList(new Employee("gaurav","564656"),
                new Employee("aditya","5476886"),
                new Employee("nilay","987755"));
    }

}

