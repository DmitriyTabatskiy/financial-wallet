package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.CategoryReportModelToReportDtoConverter;
import com.tabatskiy.web.repository.CategoryReportModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryReportService {

    private final CategoryReportModelRepository categoryReportDao;
    private final CategoryReportModelToReportDtoConverter toReportDtoConverter;

    public List<CategoryReportDTO> findAllIncomeCategoryForReport(LocalDate withDate, LocalDate onDate, Integer clientId) {
        return categoryReportDao.findAllIncomeCategoryForReport(withDate, onDate, clientId).stream()
                .map(toReportDtoConverter::convert)
                .collect(toList());
    }

    public List<CategoryReportDTO> findAllExpensesCategoryForReport(LocalDate withDate, LocalDate onDate, Integer clientId) {
        return categoryReportDao.findAllExpensesCategoryForReport(withDate, onDate, clientId).stream()
                .map(toReportDtoConverter::convert)
                .collect(toList());
    }
}