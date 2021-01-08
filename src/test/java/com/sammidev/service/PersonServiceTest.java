package com.sammidev.service;

import com.sammidev.config.DataSourceConfig;
import com.sammidev.entity.Person;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PersonServiceTest extends TestCase {
    private DataSource dataSource;
    private PersonService personService;

    @Override
    public void setUp() throws Exception {
        this.dataSource = new DataSourceConfig().getDataSource();
    }

    @Test
    public void testFindAllData() throws SQLException {
        try (var connection = this.dataSource.getConnection()) {
            this.personService = new PersonService(connection);

            List<Person> list = this.personService.findAll();
            assertEquals("status", list.size(), 5);

            list.forEach(person -> System.out.println(person.toString() + "\n"));

        } catch (SQLException ex) {
            log.error("can't fetch data", ex);
        }
    }

    @Test
    public void testFindById() {
        try (var connection = this.dataSource.getConnection()) {
            this.personService = new PersonService(connection);

            Optional<Person> existId = this.personService.findById("001");
            assertTrue("find by id 001 data exist", existId.isPresent());

            Person id001 = existId.get();
            System.out.println(id001.toString());

            assertEquals("name with id 001 is Sammi Aldhi Yanto actualy",
                    id001.getName(),
                    "Sammi Aldhi Yanto");

            Optional<Person> notExistId = this.personService.findById("006");
            assertFalse("find by id 006 data not exist actualy", notExistId.isPresent());
        } catch (SQLException ex) {
            log.error("can't fetch data", ex);
        }
    }

    @Test
    public void testFindByEmail() {
        try (var connection = this.dataSource.getConnection()) {
            this.personService = new PersonService(connection);

            Optional<Person> existId = this.personService.findByEmail("ayat@gmail.com");
            Person ayat = existId.get();
            System.out.println(ayat.toString());

        } catch (SQLException ex) {
            log.error("can't fetch data", ex);
        }
    }

    @Test
    public void testPagingList() {
        try (var connection = this.dataSource.getConnection()) {
            this.personService = new PersonService(connection);

            List<Person> showMoreThanStored = this.personService.findAll(
                            0l,
                            10l,
                            null,
                            null,
                            null);
            System.out.println("--------------------------------------");
            System.out.println("jumlah person table when limit 10 and offset 0");
            showMoreThanStored.forEach(System.out::println);
            System.out.println("--------------------------------------");
            assertEquals("jumlah person table when limit 10 and offset 0", showMoreThanStored.size(), 5);

            List<Person> showOnly3 = this.personService.findAll(
                    0l,
                    3l,
                    null,
                    null,
                    null);

            System.out.println("--------------------------------------");
            System.out.println("jumlah person table when limit 3 and offset 0");
            showOnly3.forEach(System.out::println);
            System.out.println("--------------------------------------");
            assertEquals("jumlah person table when limit 3 and offset 0", showOnly3.size(), 3);

            List<Person> showOnly2 = this.personService.findAll(
                    3l,
                    3l,
                    null,
                    null,
                    null);
            System.out.println("--------------------------------------");
            System.out.println("jumlah person table when limit 3 and offset 3");
            showOnly2.forEach(System.out::println);
            System.out.println("--------------------------------------");
            assertEquals("jumlah person table when limit 3 and offset 3", showOnly2.size(), 2);
        } catch (SQLException ex) {
            log.error("can't fetch data", ex);
        }
    }

    @Test
    public void testSavingData() {
        try (var connection = this.dataSource.getConnection()) {
            this.personService = new PersonService(connection);

            Person returnValue = this.personService.save(
                    new Person(
                            null,
                            "Sam",
                            "Sam@yahoo.co.id",
                            true,
                            new BigDecimal(100000),
                            Date.valueOf(LocalDate.now()),
                            Timestamp.valueOf(LocalDateTime.now()))
            );

            assertNotNull("person id not null", returnValue.getId());
            log.info("person: {}", returnValue);

            /*this.personService.removeById(returnValue.getId());

            List<Person> list = this.personService.findAll();
            assertEquals("jumlah data example table", list.size(), 5);*/

        } catch (SQLException ex) {
            log.error("can't fetch data", ex);
        }
    }


    @Test
    public void testSavingDataWithTransactional() {
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            this.personService = new PersonService(connection);

            Person sammi = this.personService.save(
                    new Person(
                            null,
                            "Sam",
                            "Sam@yahoo.co.id",
                            true,
                            new BigDecimal(100000),
                            Date.valueOf(LocalDate.now()),
                            Timestamp.valueOf(LocalDateTime.now()))
            );

            Person save1 = this.personService.save(sammi);

            assertNotNull("person id not null", save1.getId());
            log.info("person: {}", save1);

            List<Person> list = this.personService.findAll();
            assertEquals("jumlah data example table", list.size(), 6);

            this.personService.removeById(save1.getId());

            sammi.setEmail(null);
            Person save2 = this.personService.save(sammi);

            list = this.personService.findAll();
            assertEquals("jumlah data example table", list.size(), 6);

            log.info("person: {}", save2);
            this.personService.removeById(save2.getId());

            list = this.personService.findAll();
            assertEquals("jumlah data example table", list.size(), 5);
            connection.commit();
        } catch (SQLException ex) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    log.error("can't rollback");
                }
            }
            log.error("can't fetch data", ex);
        }
    }


    @Test
    public void testSaveBatchData() {
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            this.personService = new PersonService(connection);
            List<Person> list = Arrays.asList(
                    new Person(
                            null,
                            "Sam11",
                            "Sam11@yahoo.co.id",
                            true,
                            new BigDecimal(100000),
                            Date.valueOf(LocalDate.now()),
                            Timestamp.valueOf(LocalDateTime.now())),
                    new Person(
                            null,
                            "Sam2",
                            "Sam2@yahoo.co.id",
                            true,
                            new BigDecimal(100000),
                            Date.valueOf(LocalDate.now()),
                            Timestamp.valueOf(LocalDateTime.now()))
            );

            List<String> listIds = this.personService.save(list);
            log.info("{}", listIds);

            for (String id : listIds) {
                this.personService.removeById(id);
            }

            connection.commit();


        } catch (SQLException ex) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    log.error("can't rollback");
                }
            }
            log.error("can't fetch data", ex);
        }
    }
}