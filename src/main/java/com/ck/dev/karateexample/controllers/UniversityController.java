package com.ck.dev.karateexample.controllers;

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
@RequestMapping("/universities")
public class UniversityController {

    private final List<University> universities = Arrays.asList(
            new University(101, "IIT","India",100000,new String[]{}),
            new University(201, "MIT","USA",200000,new String[]{}),
            new University(301, "SU","USA",300000,new String[]{}),
            new University(401, "NIT","India",400000,new String[]{}),
            new University(501, "BITS","India",500000,new String[]{})
    );

    @GetMapping("/")
    public ResponseEntity<List<University>> getUniversities()
    {
        return ResponseEntity.status(HttpStatus.OK).body(universities);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUniversity(@RequestBody University university) {
        if(university==null)   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        boolean isIdPresent = universities.stream().anyMatch(i -> university.getId() == i.getId());
        if(isIdPresent){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("University with id already present");
        }
        universities.add(university);
        return ResponseEntity.status(HttpStatus.CREATED).body("University added");
    }

    @GetMapping("/{location}")
    public ResponseEntity<List<University>> getUniversityById(@PathVariable String location) {
        List<University> ans = new ArrayList<>();
        if(location==null || location.isEmpty())   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ans);
        universities.stream().filter(i-> Objects.equals(i.getLocation(), location)).forEach(ans::add);
        if(ans.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ans);
        return ResponseEntity.status(HttpStatus.CREATED).body(ans);
    }



    @DeleteMapping("/remove")
    public ResponseEntity<String> removeUniversity(@PathVariable int id) {
        if(id==0)   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        AtomicReference<University> university = new AtomicReference<>();
        boolean isIdPresent = universities.stream().anyMatch(i ->{
            if(id==i.getId()) university.set(i);
            return id == i.getId();
        } );
        if(!isIdPresent){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("University with id not present");
        }
        universities.remove(university.get());
        return ResponseEntity.status(HttpStatus.OK).body("University deleted");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUniversity(@RequestBody University university) {
        if(university==null)   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
        boolean isPresent = false;
        int index = -1;
        for(int i=0;i<universities.size();i++){
            University univ = universities.get(i);
            if(univ.getId()==university.getId()){
                isPresent=true;
                index=i;
                break;
            }
        }
        if(!isPresent) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("University with id not present");
        universities.set(index,university);
        return ResponseEntity.status(HttpStatus.OK).body("University updated");
    }

}
