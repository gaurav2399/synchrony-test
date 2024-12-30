package org.synchrony;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
@EnableAsync
public class EmployeeSmartManager {

    @Autowired
    private Executor myTaskExecutor;

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeManager.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeSmartManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public boolean addEmployees(List<Employee> employeeList) throws InterruptedException {
        LOG.info("Adding employees parallel size : " + employeeList.size());
        CountDownLatch countDownLatch = new CountDownLatch(employeeList.size());
        for (Employee employee : employeeList) {
            CompletableFuture<Employee> future1 = addEmployee(employee);
            future1.thenApply(result -> {
                        LOG.info("Employee : " + result.getName() + " added to DB");
                        countDownLatch.countDown();
                        return null;
                    })
                    .exceptionally(e -> {
                        LOG.error("Error while adding " + employee.getName() +
                                " error : " + e.getMessage());
                        countDownLatch.countDown();
                        return null;
                    });
        }
        return countDownLatch.await(1, TimeUnit.MINUTES);
    }

    private CompletableFuture<Employee> addEmployee(Employee employee) {
        return CompletableFuture.supplyAsync(() -> {
            Employee employee1;
            LOG.info("Adding Employee -> name : {}, phone {}, thread : {}", employee.getName(),
                    employee.getPhone(), Thread.currentThread());
            try {
                Thread.sleep(10000);
                employee1 = employeeRepository.save(employee);
                LOG.info("Sleep completed for " + employee.getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return employee1;
        }, myTaskExecutor);
    }

}
