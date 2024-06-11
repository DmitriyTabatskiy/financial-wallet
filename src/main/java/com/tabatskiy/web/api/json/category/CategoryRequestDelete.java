package com.tabatskiy.web.api.json.category;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Positive;

@Data
public class CategoryRequestDelete {

    @NonNull
    @Positive
    private Integer id;

    @NonNull
    @Positive
    private Integer clientId;
}