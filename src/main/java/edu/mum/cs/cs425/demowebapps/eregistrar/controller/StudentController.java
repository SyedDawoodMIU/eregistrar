package edu.mum.cs.cs425.demowebapps.eregistrar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mum.cs.cs425.demowebapps.eregistrar.model.Student;
import edu.mum.cs.cs425.demowebapps.eregistrar.repository.StudentRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/eregistrar/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/list")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "student/list";
    }

    @GetMapping("/new")
    public String showNewStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/new";
    }

     @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            
            return "student/new";
        }
        studentRepository.save(student);
        return "redirect:/eregistrar/student/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Long studentId, Model model) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + studentId));
        model.addAttribute("student", student);
        return "student/edit";
    }

    @PostMapping("/update")
    public String updateStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            
            return "student/edit";
        }
        studentRepository.save(student);
        return "redirect:/eregistrar/student/list"; 
    }

   
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long studentId) {
        studentRepository.deleteById(studentId);
        return "redirect:/eregistrar/student/list";
    }

    @GetMapping("/search")
    public String searchStudents(@RequestParam("keyword") String keyword, Model model) {
        List<Student> students = studentRepository.findByStudentNumberContainingOrFirstNameContainingOrLastNameContaining(keyword, keyword, keyword);
        model.addAttribute("students", students);
        return "student/list";
    }
}
