package com.sammidev.service;

import com.sammidev.entity.Person;
import com.sammidev.repository.PersonRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonService implements PersonRepository<Person, String> {

    private Connection connection;


    /** NOTES
     *
     * `PreparedStatement` biasanya digunakan untuk melakukan Insert, Update, Delete, FindByid, atau query2 yang membutuhkan parameter.
     * `Statement` biasanya digunakan untuk query yang tidak membutuhkan parameter / argument.
     * `executeUpdate()` biasanya digunakan untuk meng-eksekusi perintah
     *         insert, update, delete, alter, create initinya query yang melakukan penambahan, perubahan pada struktur atau data.
     * `executeQuery()` biasanya digunakan untuk meng-eksekusi perintah select, function atau perintah sql yang mengembalikan data.
     * Ketika preparedStatement.executeQuery() di eksekusi maka menghasilkan class API ResultSet. API Class ResultSet adalah sebuah
     *        Pointer / Nodes / Array dimana data ditampung pada Class tersebut.
     *
     */

    public PersonService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person save(Person value) throws SQLException {
        String query = "insert into person " +
                "(name, email, is_active, salary, created_date, created_time)\n" +
                "values (?, ?, ?, ?, ?, ?)";

        var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, value.getName());
        preparedStatement.setString(2, value.getEmail());
        preparedStatement.setBoolean(3, value.getActive());
        preparedStatement.setBigDecimal(4, value.getSalary());
        preparedStatement.setDate(5, value.getCreatedDate());
        preparedStatement.setTimestamp(6, value.getCreatedTime());

        preparedStatement.executeUpdate();

        var resultSetGeneratedKeys = preparedStatement.getGeneratedKeys();
        if (resultSetGeneratedKeys.next())
            value.setId(resultSetGeneratedKeys.getString(1));

        preparedStatement.close();
        return value;
    }

    @Override
    public Person update(Person value) throws SQLException {
        String query = "update person\n" +
                "set name    = ?,\n" +
                "    email   = ?,\n" +
                "    active  = ?,\n" +
                "    salary  = ?\n" +
                "where id = ?";

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value.getName());
        preparedStatement.setString(2, value.getEmail());
        preparedStatement.setBoolean(3, value.getActive());
        preparedStatement.setBigDecimal(4, value.getSalary());
        preparedStatement.setString(7, value.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return value;
    }

    @Override
    public Boolean removeById(String value) throws SQLException {
        String query = "delete from person where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
    }

    @Override
    public Boolean removeByEmail(String email) throws SQLException {
        String query = "delete from person where email = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
    }

    @Override
    public Optional<Person> findById(String value) throws SQLException {
        String query = "select id    as id,\n" +
                "       name         as name,\n" +
                "       email        as email,\n" +
                "       is_active    as active,\n" +
                "       salary       as salary,\n" +
                "       created_date as createdDate,\n" +
                "       created_time as createdTime\n" +
                "from person\n" +
                "where id = ?";

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        var resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            preparedStatement.close();
            return Optional.empty();
        }

        var person = Person.builder()
                .id(resultSet.getString("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .active(resultSet.getObject("active", Boolean.class))
                .salary(resultSet.getBigDecimal("salary"))
                .createdDate(resultSet.getDate("createdDate"))
                .createdTime(resultSet.getTimestamp("createdTime"))
                .build();

        resultSet.close();
        preparedStatement.close();
        return Optional.of(person);
    }

    @Override
    public Optional<Person> findByEmail(String value) throws SQLException {
        String query = "select id    as id,\n" +
                "       name         as name,\n" +
                "       email        as email,\n" +
                "       is_active    as active,\n" +
                "       salary       as salary,\n" +
                "       created_date as createdDate,\n" +
                "       created_time as createdTime\n" +
                "from person\n" +
                "where email = ?";

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        var resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            preparedStatement.close();
            return Optional.empty();
        }

        var person = Person.builder()
                .id(resultSet.getString("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .active(resultSet.getObject("active", Boolean.class))
                .salary(resultSet.getBigDecimal("salary"))
                .createdDate(resultSet.getDate("createdDate"))
                .createdTime(resultSet.getTimestamp("createdTime"))
                .build();

        resultSet.close();
        preparedStatement.close();
        return Optional.of(person);
    }

    @Override
    public List<Person> findAll() throws SQLException {
        List<Person> personList = new ArrayList<>();

        String query = "select id    as id,\n" +
                "       name         as name,\n" +
                "       email        as email,\n" +
                "       is_active    as active,\n" +
                "       salary       as salary,\n" +
                "       created_date as createdDate,\n" +
                "       created_time as createdTime \n" +
                "       from person";

        var statement = connection.createStatement();
        var resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            var persons = Person.builder()
                    .id(resultSet.getString("id"))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .active(resultSet.getObject("active", Boolean.class))
                    .salary(resultSet.getBigDecimal("salary"))
                    .createdDate(resultSet.getDate("createdDate"))
                    .createdTime(resultSet.getTimestamp("createdTime"))
                    .build();

            personList.add(persons);
        }
        resultSet.close();
        statement.close();
        return personList;
    }

    @Override
    public List<Person> findAll(
            Long start,
            Long limit,
            Long orderIndex,
            String orderDirection,
            Person param) throws SQLException {

        List<Person> list = new ArrayList<>();
        String baseQuery = "select id   as id,\n" +
                "       name         as name,\n" +
                "       email        as email,\n" +
                "       is_active       as active,\n" +
                "       salary       as salary,\n" +
                "       created_date as createdDate,\n" +
                "       created_time as createdTime\n" +
                "from person\n" +
                "where 1 = 1 \n" +
                "limit ? offset ?";

        var preparedStatement = connection.prepareStatement(baseQuery);
        preparedStatement.setLong(1, limit);
        preparedStatement.setLong(2, start);

        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var person = Person.builder()
                    .id(resultSet.getString("id"))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .active(resultSet.getObject("active", Boolean.class))
                    .salary(resultSet.getBigDecimal("salary"))
                    .createdDate(resultSet.getDate("createdDate"))
                    .createdTime(resultSet.getTimestamp("createdTime"))
                    .build();

            list.add(person);
        }

        resultSet.close();
        preparedStatement.close();
        return list;
    }


    public List<String> save(List<Person> values) throws SQLException {
        List<String> newList = new ArrayList<>();
        String query = "insert into person " +
                "(name, email, is_active, salary, created_date, created_time)\n" +
                "values (?, ?, ?, ?, ?, ?)";
        var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        for (Person value : values) {

            preparedStatement.setString(1, value.getName());
            preparedStatement.setString(2, value.getEmail());
            preparedStatement.setBoolean(3, value.getActive());
            preparedStatement.setBigDecimal(4, value.getSalary());
            preparedStatement.setDate(5, value.getCreatedDate());
            preparedStatement.setTimestamp(6, value.getCreatedTime());

            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
        var generatedKeys = preparedStatement.getGeneratedKeys();
        while (generatedKeys.next())
            newList.add(generatedKeys.getString(1));
        preparedStatement.close();
        return newList;
    }
}