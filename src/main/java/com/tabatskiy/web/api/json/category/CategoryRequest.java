package com.tabatskiy.web.api.json.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NonNull
    @Positive
    private Integer id;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;

    @NonNull
    @Positive
    private Integer clientId;
}