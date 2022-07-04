package com.ck.dev.karateexample.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {

    private int id;

    private String name;

    private int age;

    private String courseEnrolled;
}
