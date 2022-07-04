package com.ck.dev.karateexample.controllers;

import com.ck.dev.karateexample.models.Student;
import com.ck.dev.karateexample.models.University;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<Student> students = Arrays.asList(
            new Student(101, "IIT",19,"India"),
            new Student(201, "MIT",21,"USA"),
            new Student(301, "SU",20,"USA"),
            new Student(401, "NIT",21,"India"),
            new Student(501, "BITS",19,"India")
    );

    @GetMapping("/")
    public ResponseEntity<List<Student>> getStudents()
    {
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        if(student==null)   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        boolean isIdPresent = students.stream().anyMatch(i -> student.getId() == i.getId());
        if(isIdPresent){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student with id already present");
        }
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student added");
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Student>> getStudentByName(@PathVariable String name) {
        List<Student> ans = new ArrayList<>();
        if(name==null||name.isEmpty())   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ans);
        students.stream().filter(i-> Objects.equals(i.getName(), name)).forEach(ans::add);
        if(ans.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ans);
        return ResponseEntity.status(HttpStatus.CREATED).body(ans);
    }



    @DeleteMapping("/remove")
    public ResponseEntity<String> removeStudent(@PathVariable int id) {
        if(id==0)   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        AtomicReference<Student> student = new AtomicReference<>();
        boolean isIdPresent = students.stream().anyMatch(i ->{
            if(id==i.getId()) student.set(i);
            return id == i.getId();
        } );
        if(!isIdPresent){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with id not present");
        }
        students.remove(student.get());
        return ResponseEntity.status(HttpStatus.OK).body("Student deleted");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUniversity(@RequestBody Student student) {
        if(student==null)   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        boolean isPresent = false;
        int index = -1;
        for(int i = 0; i< students.size(); i++){
            Student univ = students.get(i);
            if(univ.getId()==student.getId()){
                isPresent=true;
                index=i;
                break;
            }
        }
        if(!isPresent) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with id not present");
        students.set(index,student);
        return ResponseEntity.status(HttpStatus.OK).body("Student details updated");
    }

}
