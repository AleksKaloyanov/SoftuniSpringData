import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        System.out.println("Select ex number:");

        try {
            int exNum = Integer.parseInt(bufferedReader.readLine());

            switch (exNum) {
                case 2:
                    exTwo();
                case 3:
                    exThree();
                case 4:
                    exFour();
                case 5:
                    exFive();
                case 6:
                    exSix();
                case 7:
                    exSeven();
                case 8:
                    exEight();
                case 9:
                    exNine();
                case 10:
                    exTen();
                case 11:
                    exEleven();
                case 12:
                    exTwelve();
                    break;
                case 13:
                    exThirteen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private void exThirteen() throws IOException {
        System.out.println("Enter town: ");
        String townName = bufferedReader.readLine();

        Town town = entityManager
                .createQuery("SELECT t FROM Town t WHERE t.name=:p_town", Town.class)
                .setParameter("p_town", townName)
                .getSingleResult();

        setEmployeeAddressToNull(townName);
        entityManager.getTransaction().begin();

        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a WHERE a.town.id=:p_id", Address.class)
                .setParameter("p_id", town.getId())
                .getResultList();
        addresses.forEach(entityManager::remove);

        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.printf("%d address in %s deleted", addresses.size(), townName);
    }

    private void setEmployeeAddressToNull(String townName) {

        String query = "SELECT e FROM Employee AS e WHERE e.address.town.name = '" + townName + "'";

        List<Employee> employees = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().begin();
        for (Employee employee : employees) {
            employee.setAddress(null);
            entityManager.persist(employee);
        }
        entityManager.getTransaction().commit();
    }

    private void exTwelve() {
        List<Object[]> resultList = entityManager
                .createQuery("SELECT e.department.name, MAX (e.salary)  " +
                        "FROM Employee e " +
                        "GROUP BY e.department.name " +
                        "HAVING MAX (e.salary)  " +
                        "NOT BETWEEN 30000 AND 70000")
                .getResultList();
        resultList.forEach(r -> {
            System.out.println(r[0] + " " + r[1]);
        });
    }

    private void exEleven() throws IOException {
        System.out.println("Please enter pattern: ");
        String pattern = bufferedReader.readLine();
        entityManager.createQuery("select e from Employee e " +
                        "where e.firstName like :name", Employee.class)
                .setParameter("name", pattern + "%")
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s - %s - (%.2f)%n",
                            e.getFirstName(),
                            e.getLastName(),
                            e.getJobTitle(),
                            e.getSalary());
                });


    }

    private void exTen() {
        entityManager.getTransaction().begin();

        entityManager.createQuery("update Employee e " +
                        "set e.salary = (e.salary * 1.12) " +
                        "where e.department.id in :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.createQuery("select e from Employee e " +
                        "where e.department.id in :ids", Employee.class)
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s ($%.2f)%n",
                            e.getFirstName(),
                            e.getLastName(),
                            e.getSalary());
                });
    }

    private void exNine() {
        entityManager.createQuery("SELECT p from Project p " +
                        "order by p.startDate desc,p.name ", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(project -> {
                    System.out.printf("Project name: %s%n" +
                                    "\tProject Description: %s%n" +
                                    "\tProject Start Date:%s%n" +
                                    "\tProject End Date: %s%n",
                            project.getName(),
                            project.getDescription(),
                            project.getStartDate(),
                            project.getEndDate());
                });
    }

    private void exEight() throws IOException {
        System.out.println("Enter employee's id: ");
        int id = Integer.parseInt(bufferedReader.readLine());
        Employee employee = entityManager.find(Employee.class, id);
        System.out.printf("%s %s - %s %n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());
        for (Project project : employee.getProjects()) {
            System.out.printf("\t %s%n", project.getName());
        }
    }

    private void exSeven() {
        entityManager.createQuery("select a from Address a " +
                        "order by a.employees.size desc ", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a ->
                        System.out.printf("%s, %s - %d employees%n",
                                a.getText(),
                                a.getTown() == null ? "Unknown" : a.getTown().getName(),
                                a.getEmployees().size()));
    }

    private void exSix() throws IOException {
        System.out.println("Please enter last name: ");
        String lastName = bufferedReader.readLine();
        String address = "Vitoshka 15";

        Employee employee = entityManager.createQuery("select e from Employee e " +
                        "where e.lastName = :l_Name", Employee.class)
                .setParameter("l_Name", lastName)
                .getSingleResult();

        Address addressObj = createAddress(address);

        entityManager.getTransaction().begin();
        employee.setAddress(addressObj);
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void exFive() {
        entityManager.createQuery("select e from Employee e " +
                        "where e.department.name = :department " +
                        "order by e.salary,e.id", Employee.class)
                .setParameter("department", "Research and Development")
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s from %s - $%.2f%n",
                            e.getFirstName(),
                            e.getLastName(),
                            e.getDepartment(),
                            e.getSalary());
                });
    }

    private void exFour() {

        BigDecimal salary = BigDecimal.valueOf(50000L);
        entityManager.createQuery("select e from Employee e " +
                        "where e.salary> :salary", Employee.class)
                .setParameter("salary", salary)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);

    }

    private void exThree() throws IOException {
        System.out.println("Enter employee full name:");
        String[] fullName = bufferedReader.readLine().split("\\s+");
        String fName = fullName[0];
        String lName = fullName[1];

        Long singleResult = entityManager.createQuery("select count(e) from Employee e where " +
                        "e.firstName = :f_name and e.lastName = :l_name", Long.class)
                .setParameter("f_name", fName)
                .setParameter("l_name", lName)
                .getSingleResult();

        System.out.println(singleResult == 0 ? "No" : "Yes");
    }

    private void exTwo() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Town t set t.name = upper(t.name) " +
                "where length(t.name)<=5");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}
