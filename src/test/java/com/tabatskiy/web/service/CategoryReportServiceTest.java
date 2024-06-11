package com.tabatskiy.web.service;

import com.tabatskiy.web.converter.CategoryReportModelToReportDtoConverter;
import com.tabatskiy.web.entity.CategoryReportModel;
import com.tabatskiy.web.repository.CategoryReportModelRepository;
import com.tabatskiy.web.service.CategoryReportDTO;
import com.tabatskiy.web.service.CategoryReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryReportServiceTest {

    @Mock
    CategoryReportModelRepository categoryReportDao;

    @Mock
    CategoryReportModelToReportDtoConverter toReportDtoConverter;

    @InjectMocks
    CategoryReportService subj;

    @Test
    public void findAllIncomeCategoryForReport_isNotEmpty() {
        CategoryReportModel categoryReportModel = new CategoryReportModel();
        categoryReportModel.setName("Alfa");
        categoryReportModel.setAmount(4700);
        List<CategoryReportModel> categoryReportModels = new ArrayList<>();
        categoryReportModels.add(categoryReportModel);

        when(categoryReportDao.findAllIncomeCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1))
                .thenReturn(categoryReportModels);

        CategoryReportDTO categoryReportDTO = new CategoryReportDTO();
        categoryReportDTO.setName("Alfa");
        categoryReportDTO.setAmount(4700);
        List<CategoryReportDTO> categoryReportDTOS = new ArrayList<>();
        categoryReportDTOS.add(categoryReportDTO);

        when(toReportDtoConverter.convert(categoryReportModel)).thenReturn(categoryReportDTO);

        List<CategoryReportDTO> categoryReportDTOList = subj.findAllIncomeCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);
        assertEquals(categoryReportDTOS, categoryReportDTOList);

        verify(categoryReportDao, times(1)).findAllIncomeCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);
        verify(toReportDtoConverter, times(1)).convert(categoryReportModel);
    }

    @Test
    public void findAllIncomeCategoryForReport_isEmpty() {
        List<CategoryReportModel> categoryReportModels = new ArrayList<>();

        when(categoryReportDao.findAllIncomeCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1))
                .thenReturn(categoryReportModels);

        List<CategoryReportDTO> categoryReportDTOS = new ArrayList<>();

        List<CategoryReportDTO> categoryReportDTOList = subj.findAllIncomeCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);

        assertEquals(categoryReportDTOS, categoryReportDTOList);

        verify(categoryReportDao, times(1)).findAllIncomeCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);
        verifyZeroInteractions(toReportDtoConverter);
    }

    @Test
    public void findAllExpensesCategoryForReport_isNotEmpty() {
        CategoryReportModel categoryReportModel = new CategoryReportModel();
        categoryReportModel.setName("Alfa");
        categoryReportModel.setAmount(4700);
        List<CategoryReportModel> categoryReportModels = new ArrayList<>();
        categoryReportModels.add(categoryReportModel);

        when(categoryReportDao.findAllExpensesCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1))
                .thenReturn(categoryReportModels);

        CategoryReportDTO categoryReportDTO = new CategoryReportDTO();
        categoryReportDTO.setName("Alfa");
        categoryReportDTO.setAmount(4700);
        List<CategoryReportDTO> categoryReportDTOS = new ArrayList<>();
        categoryReportDTOS.add(categoryReportDTO);

        when(toReportDtoConverter.convert(categoryReportModel)).thenReturn(categoryReportDTO);

        List<CategoryReportDTO> categoryReportDTOList = subj.findAllExpensesCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);
        assertEquals(categoryReportDTOS, categoryReportDTOList);

        verify(categoryReportDao, times(1)).findAllExpensesCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);
        verify(toReportDtoConverter, times(1)).convert(categoryReportModel);
    }

    @Test
    public void findAllExpensesCategoryForReport_isEmpty() {
        List<CategoryReportModel> categoryReportModels = new ArrayList<>();

        when(categoryReportDao.findAllExpensesCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1))
                .thenReturn(categoryReportModels);

        List<CategoryReportDTO> categoryReportDTOS = new ArrayList<>();

        List<CategoryReportDTO> categoryReportDTOList = subj.findAllExpensesCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);

        assertEquals(categoryReportDTOS, categoryReportDTOList);

        verify(categoryReportDao, times(1)).findAllExpensesCategoryForReport(
                LocalDate.of(2023, 1, 3),
                LocalDate.of(2023, 1, 7), 1);
        verifyZeroInteractions(toReportDtoConverter);
    }
}