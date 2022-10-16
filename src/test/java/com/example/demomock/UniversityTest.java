package com.example.demomock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class UniversityTest {

    private final Student student = new Student("Владимир", true);

    @Mock
    private StudentValueGenerator studentValueGenerator;
    @InjectMocks
    private University university;

//    @BeforeEach
//    public void setUp() {
//        university = new University(studentValueGenerator);
//    }

    @Test
    public void getAllStudents() {
        assertNotNull(studentValueGenerator);

        Mockito.when(studentValueGenerator.generateAge()).thenReturn(50);
//        Mockito.doReturn(50).when(studentValueGenerator).generateAge();

        university.addStudent(student);
        List<Student> expected = university.getAllStudent();
        assertEquals(expected.get(0).getAge(), 50);
    }

    @Test
    public void getAllStudentsOver50Years() {
        Mockito.when(studentValueGenerator.generateAgeInRange(anyInt(), anyInt())).thenReturn(55);
//        Mockito.doReturn(50).when(studentValueGenerator).generateAge();

        university.addStudentInRange(student, 50, 100);
        List<Student> expected = university.getAllStudent();
        assertEquals(expected.get(0).getAge(), 55);
    }

    @Test
    public void getAllStudentsWithCountAgeGenerate() {
        Mockito.when(studentValueGenerator.generateAge()).thenReturn(50);

        university.addStudent(student);
        List<Student> expected = university.getAllStudent();
        assertEquals(expected.get(0).getAge(), 50);

        Mockito.verify(studentValueGenerator, Mockito.times(2)).generateAge();
    }

    @Test
    public void getAllStudentsInOrder() {
        Mockito.when(studentValueGenerator.generateAgeInRange(anyInt(), anyInt())).thenReturn(55);

        university.addStudentInRange(student, 50, 100);

        InOrder inOrder = Mockito.inOrder(studentValueGenerator);

        List<Student> expected = university.getAllStudent();

        inOrder.verify(studentValueGenerator, times(2)).generateAge();
        inOrder.verify(studentValueGenerator).generateAgeInRange(anyInt(), anyInt());
        assertEquals(expected.get(0).getAge(), 55);
    }

}