package com.example.homeworkstreams;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService();

    @BeforeEach
    public void addEmployeesBeforeTest() {
        employeeService.addEmployee("Ivan", "Ivanov", 1, 100000);
        employeeService.addEmployee("Sidor", "Sidorov", 2, 150000);
        employeeService.addEmployee("Petr", "Petrov", 1, 200000);
    }

    @AfterEach
    public void removeEmployeesAfterTest() {
        employeeService.getAll()
                .forEach(employee -> employeeService.removeEmployee(employee.getFirstName(), employee.getLastName()));
    }

    @Test
    public void addEmployeeTest() {
        int count = employeeService.getAll().size();
        Employee expected = new Employee("Semen", "Semenov", 1, 100000);
        assertThat(employeeService.addEmployee("Semen", "Semenov", 1, 100000))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
        assertThat(employeeService.getAll()).hasSize(count + 1);
        assertThat(employeeService.findEmployee("Semen", "Semenov")).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectFirstNameParamsForAddingTest")
    public void validateFirstNameInputTest(String incorrectFirstName) {
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> employeeService.addEmployee(incorrectFirstName, "Semenov", 1, 100000));
    }

    public static Stream<Arguments> provideIncorrectFirstNameParamsForAddingTest() {
        return Stream.of(
                Arguments.of("Ivan2"),
                Arguments.of("Iva1n"),
                Arguments.of("Ivan%")
        );
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectLastNameParamsForAddingTest")
    public void validateLastNameInputTest(String incorrectLastName) {
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> employeeService.addEmployee("Semen", incorrectLastName, 1, 100000));
    }

    public static Stream<Arguments> provideIncorrectLastNameParamsForAddingTest() {
        return Stream.of(
                Arguments.of("Ivan2ov"),
                Arguments.of("Ivanov1"),
                Arguments.of("Ivanov%")
        );
    }

    @Test
    public void addWhenEmployeeExistsTest() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> employeeService.addEmployee("Ivan", "Ivanov", 1, 100000));
    }

    @Test
    public void addWhenLimitReachedTest() {
        Stream.iterate(1, i -> i + 1)
                .limit(7)
                .map(number -> new Employee("Ivan" + ((char) ('a' + number)),
                        "Ivanov" + ((char) ('a' + number)),
                        (int) (number / 2 + 1),
                        100000 + number * 10000))
                .forEach(employee -> employeeService.addEmployee(
                                employee.getFirstName(), employee.getLastName(),
                                employee.getDepartment(), employee.getSalary()
                        )
                );
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> employeeService.addEmployee("Semen", "Semenov", 1, 100000));
    }

    @Test
    public void removeEmployeeTest() {
        int count = employeeService.getAll().size();
        Employee expected = new Employee("Ivan", "Ivanov");
        assertThat(employeeService.removeEmployee("Ivan", "Ivanov"))
                .isEqualTo(expected)
                .isNotIn(employeeService.getAll());
        assertThat(employeeService.getAll()).hasSize(count - 1);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> employeeService.findEmployee("Ivan", "Ivanov"));
    }

    @Test
    public void removeEmployeeIfNotFoundTest() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> employeeService.findEmployee("Miron", "Mironov"));
    }

    @Test
    public void findEmployeeTest() {
        Employee expected = new Employee("Ivan", "Ivanov");
        assertThat(employeeService.findEmployee("Ivan", "Ivanov"))
                .isEqualTo(expected)
                .isIn(employeeService.getAll());
    }

    @Test
    public void findEmployeeIfNotFoundTest() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> employeeService.findEmployee("Semen", "Semenov"));
    }
    @Test
    public void getAllTest() {
        assertThat(employeeService.getAll())
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        new Employee("Ivan", "Ivanov", 1, 100000),
                        new Employee("Sidor", "Sidorov", 2, 150000),
                        new Employee("Petr", "Petrov", 1, 200000)
                );
    }
}
