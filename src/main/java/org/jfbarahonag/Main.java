package org.jfbarahonag;

import jakarta.persistence.EntityManager;
import org.jfbarahonag.entity.Employee;
import org.jfbarahonag.util.UtilEntity;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = UtilEntity.getEntityManager();
        System.out.println("------ Get all ------");
        List<Employee> employees = entityManager.createQuery("SELECT e from Employee e").getResultList();
        employees.forEach(System.out::println);

        System.out.println("------ Find by Id ------");
        int id = 49;
        Employee employee = entityManager.find(Employee.class, id);
        System.out.println(employee != null ? employee.toString():"Employee not found");

        if (id != 49) {
            System.out.println("------ Create new employee using transactions ------");
            boolean rollback = false;
            Employee newEmployee = new Employee(
                    "1017252709",
                    "Juan Pepito",
                    "Pombo Lizcano",
                    1200000F
            );
            entityManager.getTransaction().begin();
            entityManager.persist(newEmployee);
            System.out.println("Employee created" + newEmployee.toString());

            if (rollback == true) {
                Employee newEmployee2 = new Employee(
                        "1017252709",
                        "Perruno",
                        "Pano Sosa",
                        4500000F
                );
                entityManager.persist(newEmployee2);
                System.out.println("Employee created: " + newEmployee2.toString());
            }
            entityManager.getTransaction().commit();
        }

        System.out.println("------ Update ------");
        id = 11;
        Employee employeeToUpdate = entityManager.find(Employee.class, id);
        if (employeeToUpdate == null) {
            System.out.println("Employee not found to be updated");
            return;
        }
        System.out.println("Employee before: " + employeeToUpdate.toString());
        employeeToUpdate.setDocument("1053848987");
        employeeToUpdate.setSalary(3600000F);
        entityManager.getTransaction().begin();
        entityManager.merge(employeeToUpdate);
        entityManager.getTransaction().commit();
        System.out.println("Employe after: " + employeeToUpdate);

        System.out.println("------ Delete ------");
        id = 3;
        Employee employeeToRemove = entityManager.find(Employee.class, id);
        if (employeeToRemove == null) {
            System.out.println("Employee not found to be removed");
            return;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(employeeToRemove);
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println("Employee removed: " + employeeToRemove);
    }
}