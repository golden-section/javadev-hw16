package org.gs.student;

import lombok.AllArgsConstructor;
import org.gs.student.exception.BadRequestException;
import org.gs.student.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    @Autowired StudentRepository studentRepository;

    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    public void add(Student student) {
        boolean existingEmail = studentRepository.selectExistingEmail(student.getEmail());
        if (existingEmail) {
            throw new BadRequestException(
                    "Email " + student.getEmail() + " is already taken");
        }
        studentRepository.save(student);
    }

    public void delete(Long id) {
        if(!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(
                    "Student with id [" + id + "] doesn't exist");
        }
        studentRepository.deleteById(id);
    }
}