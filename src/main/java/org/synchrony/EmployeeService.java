package org.synchrony;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * CLASS USAGE :
 * This class is created to do real database operations
 * For demo purpose added custom time to them, to show the
 * usage and working of redis and threads.
 */
@Service
public class EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(Employee employee) throws InterruptedException {
        LOG.info("Adding Employee -> name : {}, phone {}", employee.getName(), employee.getPhone());
        Employee emp = employee;
        Thread.sleep(Constants.CUSTOM_DB_TAKE_TIME);
        emp = employeeRepository.save(employee);
        LOG.info("Employee : " + employee.getName() + " added to DB");
        return emp;
    }

    public Optional<Employee> getEmployeeById(int id) throws InterruptedException {
        LOG.info("Find Employee by id : {}", id);
        Optional<Employee> emp;
        Thread.sleep(Constants.CUSTOM_DB_TAKE_TIME);
        emp = employeeRepository.findById(id);
        return emp;
    }

    public Optional<Employee> getEmployeeByPhone(String phone) throws InterruptedException {
        LOG.info("Find Employee by phone : {}", phone);
        Optional<Employee> emp;
        Thread.sleep(Constants.CUSTOM_DB_TAKE_TIME);
        emp = employeeRepository.findByPhone(phone);
        return emp;
    }

    public void deleteEmployeeById(int id){
        employeeRepository.deleteById(id);
    }
}
