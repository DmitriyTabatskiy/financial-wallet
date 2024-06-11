package com.tabatskiy.web.web.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryForm {

    @NonNull
    @Positive
    private Integer id;

    @NotEmpty
    private String name;
}