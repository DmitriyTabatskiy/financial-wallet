package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.CategoryReportModel;
import com.tabatskiy.web.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryReportModelRepository {
    private final DataSource dataSource;

    public List<CategoryReportModel> findAllIncomeCategoryForReport(LocalDate withDate, LocalDate onDate, Integer clientId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT c.name, sum(t.amount) amount" +
                            " FROM category AS c " +
                            "join transaction_to_category ttc on c.id = ttc.category_id " +
                            "         join transaction t on t.id = ttc.transaction_id " +
                            "WHERE t.created_date BETWEEN ? AND ? " +
                            "    AND c.client_id = ? " +
                            "    AND (t.from_account_id is null " +
                            "   OR (t.from_account_id is not null and t.to_account_id is not null)) " +
                            "group by c.name;");
            preparedStatement.setTimestamp(1, Timestamp.valueOf(withDate.atStartOfDay()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(onDate.atStartOfDay()));
            preparedStatement.setInt(3, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CategoryReportModel> categoryReportModels = new ArrayList<>();
            while (resultSet.next()) {
                CategoryReportModel categoryReportModel = new CategoryReportModel();
                categoryReportModel.setName(resultSet.getString("name"));
                categoryReportModel.setAmount(resultSet.getInt("amount"));
                categoryReportModels.add(categoryReportModel);
            }
            return categoryReportModels;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public List<CategoryReportModel> findAllExpensesCategoryForReport(LocalDate withDate, LocalDate onDate, Integer clientId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT c.name, sum(t.amount) amount" +
                            " FROM category AS c " +
                            "join transaction_to_category ttc on c.id = ttc.category_id " +
                            "         join transaction t on t.id = ttc.transaction_id " +
                            "WHERE t.created_date BETWEEN ? AND ? " +
                            "    AND c.client_id = ? " +
                            "    AND (t.to_account_id is null " +
                            "   OR (t.from_account_id is not null and t.to_account_id is not null)) " +
                            "group by c.name;");
            preparedStatement.setTimestamp(1, Timestamp.valueOf(withDate.atStartOfDay()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(onDate.atStartOfDay()));
            preparedStatement.setInt(3, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CategoryReportModel> categoryReportModels = new ArrayList<>();
            while (resultSet.next()) {
                CategoryReportModel categoryReportModel = new CategoryReportModel();
                categoryReportModel.setName(resultSet.getString("name"));
                categoryReportModel.setAmount(resultSet.getInt("amount"));
                categoryReportModels.add(categoryReportModel);
            }
            return categoryReportModels;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}