package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDAO{

//  DEFINE FIELD FOR ENTITYMANAGE
    private EntityManager entityManager;

    //  SET UP CONSTRUCTOR INJECTION
    @Autowired
    public EmployeeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);
        List<Employee> employees = theQuery.getResultList();
        return employees;
    }

    @Override
    public Employee findById(Long theId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Employee employee= currentSession.get(Employee.class, theId);
        return employee;
    }

    @Override
    public void save(Employee theEmployee) {
        Session currentSession = entityManager.unwrap(Session.class);
//        ID = 0 -> INSERT
//        ID > 0 -> UPDATE
        currentSession.saveOrUpdate(theEmployee);
    }

    @Override
    public void deleteById(Long theId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Employee> theQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
        theQuery.setParameter("employeeId", theId);
        theQuery.executeUpdate();
    }
}
