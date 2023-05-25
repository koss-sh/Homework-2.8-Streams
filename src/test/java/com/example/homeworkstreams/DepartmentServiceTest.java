package com.example.homeworkstreams;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;


    @BeforeEach
    public void beforeEach() {
        Mockito.when(employeeService.getAll()).thenReturn(
                List.of(
                        new Employee("Ivan", "Ivanov", 1, 100000),
                        new Employee("Sidor", "Sidorov", 2, 150000),
                        new Employee("Petr", "Petrov", 1, 200000),
                        new Employee("Sergei", "Sergeev", 3, 120000),
                        new Employee("Anna", "Anina", 2, 140000)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("findDeptMaxSalaryTestParams")
    public void findDeptMaxSalaryTest(int departmentId, double expected) {
        Assertions.assertThat(departmentService.findDeptMaxSalary(departmentId))
                .isEqualTo(expected);
    }
    public static Stream<Arguments> findDeptMaxSalaryTestParams() {
        return Stream.of(
                Arguments.of(1, 200000),
                Arguments.of(2, 150000),
                Arguments.of(3, 120000)
        );
    }

    @Test
    public void findDeptMaxSalaryIfNotFoundTest() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> departmentService.findDeptMaxSalary(4));
    }

    @ParameterizedTest
    @MethodSource("findDeptMinSalaryTestParams")
    public void findDeptMinSalaryTest(int departmentId, double expected) {
        Assertions.assertThat(departmentService.findDeptMinSalary(departmentId))
                .isEqualTo(expected);
    }
    public static Stream<Arguments> findDeptMinSalaryTestParams() {
        return Stream.of(
                Arguments.of(1, 100000),
                Arguments.of(2, 140000),
                Arguments.of(3, 120000)
        );
    }

    @Test
    public void findDeptMinSalaryIfNotFoundTest() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> departmentService.findDeptMinSalary(4));
    }

    @ParameterizedTest
    @MethodSource("countTotalDeptSalaryTestParams")
    public void countTotalDeptSalaryTest(int departmentId, double expected) {
        Assertions.assertThat(departmentService.countTotalDeptSalary(departmentId))
                .isEqualTo(expected);
    }
    public static Stream<Arguments> countTotalDeptSalaryTestParams() {
        return Stream.of(
                Arguments.of(1, 300000),
                Arguments.of(2, 290000),
                Arguments.of(3, 120000),
                Arguments.of(4, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getAllEmployeesOfDeptTestParams")
    public void getAllEmployeesOfDeptTest(int departmentId, List<Employee> expected) {
        Assertions.assertThat(departmentService.getAllEmployeesOfDept(departmentId))
                .containsExactlyInAnyOrderElementsOf(expected);
    }
    public static Stream<Arguments> getAllEmployeesOfDeptTestParams() {
        return Stream.of(
                Arguments.of(1,
                        List.of(
                                new Employee("Ivan", "Ivanov", 1, 100000),
                                new Employee("Petr", "Petrov", 1, 200000)
                        )
                ),
                Arguments.of(2,
                        List.of(
                                new Employee("Sidor", "Sidorov", 2, 150000),
                                new Employee("Anna", "Anina", 2, 140000)
                        )
                ),
                Arguments.of(3,
                        Collections.singletonList(new Employee("Sergei", "Sergeev", 3, 120000))
                ),
                Arguments.of(4,
                        Collections.emptyList()
                )
        );
    }

    @Test
    public void getAllEmployeesByDept() {
        Map<Integer, List<Employee>> expected = Map.of(
                1,
                List.of(
                                new Employee("Ivan", "Ivanov", 1, 100000),
                                new Employee("Petr", "Petrov", 1, 200000)
                ),
                2,
                List.of(
                                new Employee("Sidor", "Sidorov", 2, 150000),
                                new Employee("Anna", "Anina", 2, 140000)
                ),
                3,
                Collections.singletonList(new Employee("Sergei", "Sergeev", 3, 120000))
                );
        Assertions.assertThat(departmentService.getAllEmployeesByDept())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }

}
