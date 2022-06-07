package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{

    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
//        NO NEED TO UNWRAP THE ENTITY MANAGER TO SESSIONS
        Query theQuery = entityManager.createQuery("from Employee");
        List<Employee> employees = theQuery.getResultList();
        return employees;
    }

    @Override
    public Employee findById(Long theId) {
        Employee theEmployee = entityManager.find(Employee.class, theId);
        return theEmployee;
    }

    @Override
    public void save(Employee theEmployee) {
//      ID == 0 => INSERT
//      ID > 0 => UPDATE
        Employee dbEmployee = entityManager.merge(theEmployee);
        theEmployee.setId(dbEmployee.getId());
    }

    @Override
    public void deleteById(Long theId) {
        Query theQuery = entityManager.createQuery("delete from Employee where id=:employeeId");
        theQuery.setParameter("employeeId", theId);
        theQuery.executeUpdate();
    }
}
