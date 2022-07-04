package com.ck.dev.karateexample.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class University {

    private int id;

    private String name;

    private String location;

    private long capacity;

    private String[] courses;
}
