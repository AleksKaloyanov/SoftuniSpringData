package core;

import entities.Address;
import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader br;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.print("Enter ex. number: ");

        try {
            int number = Integer.parseInt(br.readLine());
            switch (number) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exTwelve() {
        List resultList = entityManager.createQuery("select d.name ,max(Employee.salary) " +
                        "from Department d " +
                        "group by d.id " +
                        "having Employee .salary not between 30000 and 70000")
                .getResultList();

        for (int i = 0; i < resultList.size() - 1; i += 2) {
            System.out.println(resultList.get(i) + " " + resultList.get(i + 1));

        }
    }

    private void exEleven() throws IOException {
        System.out.print("Please enter pattern: ");
        String pattern = br.readLine();

        entityManager.createQuery("select e from Employee e " +
                        "where e.firstName like :pattern", Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s - %s - ($%.2f)%n",
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
        entityManager.createQuery("select p from Project p " +
                        "order by p.startDate desc ", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s,%n\tProject Description:%s%n" +
                                    "\tProject Start Date:%s%n" +
                                    "\tProject End Date: %s%n"
                            , p.getName(),
                            p.getDescription(),
                            p.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss")),
                            p.getEndDate() == null ? "null" :
                                    p.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss")));
                });
    }

    private void exEight() throws IOException {
        System.out.print("Enter employee id: ");
        int id = Integer.parseInt(br.readLine());

        Employee employee = entityManager.find(Employee.class, id);
        System.out.printf("%s %s - %s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());


        Set<Project> projects = employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (Project project : projects) {
            System.out.printf("\t%s%n", project.getName());
        }
    }

    private void exSeven() {
        entityManager.createQuery("select a from Address a " +
                        "order by a.employees.size desc", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a -> {
                    System.out.printf("%s, %s - %d employees%n",
                            a.getText(),
                            a.getTown().getName(),
                            a.getEmployees().size());
                });
    }

    private void exSix() throws IOException {
        System.out.println("Enter last name: ");
        String lastName = br.readLine();

        Address address = createNewAddress("Vitoshka 15");

        entityManager.createQuery("select e from Employee e " +
                        "where e.lastName = :l_name", Employee.class)
                .setParameter("l_name", lastName)
                .getResultStream()
                .forEach(e -> {
                    entityManager.getTransaction().begin();
                    e.setAddress(address);
                    entityManager.getTransaction().commit();
                });
    }

    private Address createNewAddress(String s) {
        Address address = new Address();
        address.setText(s);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void exFive() {
        entityManager
                .createQuery("select e from Employee e " +
                        "where e.department.name = 'Research and Development' " +
                        "order by salary, id", Employee.class)
                .getResultStream()
                .forEach(e -> {
                    System.out.printf("%s %s from %s - $%.2f%n",
                            e.getFirstName(),
                            e.getLastName(),
                            e.getDepartment().getName(),
                            e.getSalary());
                });
    }

    private void exFour() {
        entityManager.createQuery("select e from Employee e " +
                        "where e.salary>50000", Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
    }

    private void exThree() throws IOException {
        System.out.print("Enter full name: ");
        String[] name = br.readLine().split("\\s+");
        String firstName = name[0];
        String lastName = name[1];

        long firstResult = entityManager
                .createQuery("select count (e) from Employee e " +
                        "where e.firstName = :f_name and e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(firstResult == 0 ? "No" : "Yes");

    }

    private void exTwo() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Town t SET t.name = upper(t.name) " +
                "where length(t.name) <=5");

        System.out.println(query.executeUpdate());

        entityManager.getTransaction().commit();
    }
}
