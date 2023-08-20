package org.gs.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void testSelectExistingEmail() {
        // given
        String email = "MilesCline@mail.com";
        Student student = new Student(
                "Miles Cline",
                email
        );
        underTest.save(student);

        // when
        boolean expected = underTest.selectExistingEmail(email);

        // then
        Assertions.assertTrue(expected);
    }

    @Test
    void testSelectUnexistingEmail() {
        // given
        String email = "MilesCline@mail.com";

        // when
        boolean expected = underTest.selectExistingEmail(email);

        // then
        Assertions.assertFalse(expected);
    }
}