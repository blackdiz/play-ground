package com.example.demo;

import lombok.Builder;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

@Getter
@Builder
public class TestDto {
    private String name;
     private String team;
}
