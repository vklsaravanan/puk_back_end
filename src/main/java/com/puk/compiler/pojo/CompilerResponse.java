package com.puk.compiler.pojo;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@ApplicationScoped
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompilerResponse {

    private String output;
    private String error;
    private String exitCode;
}
