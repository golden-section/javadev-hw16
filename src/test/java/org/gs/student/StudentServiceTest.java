package org.gs.student;

import org.gs.student.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    private StudentService underTest;
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }


    @Test
    void testListAll() {
        // when
        underTest.listAll();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void testAdd() {
        // given
        Student student = new Student(
                "Miles Cline",
                "MilesCline@mail.com"
        );

        // when
        underTest.add(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        Assertions.assertEquals(capturedStudent, student);
    }

    @Test
    void testAddWhenEmailIsTaken() {
        // given
        Student student = new Student(
                "Miles Cline",
                "MilesCline@mail.com"
        );
        given(studentRepository.selectExistingEmail(student.getEmail())).willReturn(true);

        // when/then
        BadRequestException badRequestException = Assertions.assertThrows(BadRequestException.class, () -> underTest.add(student));
        Assertions.assertEquals("Email " + student.getEmail() + " is already taken", badRequestException.getMessage());

        verify(studentRepository, never()).save(student);
    }

    // todo write test for the delete method
    @Test
    @Disabled
    void delete() {
    }
}