package com.puk.compiler.pojo;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
@ApplicationScoped
@Getter
public class CompilerRequest{
    private String code;
    private String language;
    private String[] input;
}
