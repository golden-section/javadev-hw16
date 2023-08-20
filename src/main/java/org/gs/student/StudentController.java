package org.gs.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping(value = "/list")
    public ModelAndView getAllStudents() {
        ModelAndView result = new ModelAndView("students");
        result.addObject("studentList", studentService.listAll());
        return result;
    }

    @PostMapping(value = "/add")
    public RedirectView addStudent(@ModelAttribute Student student) {
        studentService.add(student);
        return new RedirectView("/student/list");
    }

    @PostMapping(value = "/delete")
    public RedirectView deleteStudent(@RequestParam Long id) {
        studentService.delete(id);
        return new RedirectView("/student/list");
    }
}