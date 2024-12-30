package org.synchrony;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.synchrony.model.EmployeeRequestDto;

import java.util.List;
import java.util.Optional;

@Service
@EnableAsync
public class EmployeeManager {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeManager.class);

    private final EmployeeService employeeService;

    public EmployeeManager(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public boolean addEmployees(List<Employee> employees) throws InterruptedException {
        int remain=employees.size();
        for(Employee employee:employees){
            Employee employee1 = employeeService.addEmployee(employee);
            if(employee1!=null)
                remain--;
            else LOG.error("Employee " + employee.getName() + " not added to DB.");
        }
        return remain==0;
    }

    public Employee addEmployee(EmployeeRequestDto employee) throws InterruptedException {
        return employeeService.addEmployee(new Employee(employee.getName(), employee.getPhone()));
    }

    public boolean deleteEmployeeByPhone(String phone) throws InterruptedException {
        Optional<Employee> employee = employeeService.getEmployeeByPhone(phone);
        employee.ifPresent(value -> {
            employeeService.deleteEmployeeById(value.getId());
            LOG.info("Employee " + value.getName() + " deleted from DB");
        });
        return true;
    }

    public Optional<Employee> findEmployeeByPhone(String phone) throws InterruptedException {
        return employeeService.getEmployeeByPhone(phone);
    }

}
