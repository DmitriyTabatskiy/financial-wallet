package com.tabatskiy.web;

import com.tabatskiy.web.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class ConsoleApplication implements CommandLineRunner {

    private final AccountService accountService;
    private final ClientService clientService;
    private final CategoryService categoryService;
    private final CategoryReportService categoryReportService;
    private final TransactionService transactionService;

    @Override
    public void run(String... args) throws Exception {

        while (true) {

            System.out.println("-------Здраствуйте-------");
            System.out.println("Для регистрации нажмите 1");
            System.out.println("Для входа в систему нажмите 2");
            System.out.println("Для выхода нажмите 0");

            int choice = requestValue();
            if (choice == 1) {
                String emailRegis = requestTitle("Введите email");
                String passwordRegis = requestTitle("Введите password");
                ClientDTO clientDTO = clientService.registration(emailRegis, passwordRegis);
                if (clientDTO == null) {
                    System.out.println("Пользователь с email '" + emailRegis + "' уже зарегестрирован в базе");
                } else {
                    System.out.println(clientDTO);
                }
            } else if (choice == 2) {
                String emailAuth = requestTitle("Введите email");
                String passwordAuth = requestTitle("Введите password");
                ClientDTO clientDTO = clientService.auth(emailAuth, passwordAuth);
                if (clientDTO == null) {
                    System.out.println("Пользователь по email '" + emailAuth + "' не найден в базе");
                    return;
                } else {
                    System.out.println(clientDTO);
                }
                int clientId = clientDTO.getId();
                System.out.println("Для работы со счетами введите 1");
                System.out.println("Для работы с категориями введите 2");
                System.out.println("Для просмотра отчетов введите 3");
                System.out.println("Для работы с транзакциями введите 4");
                System.out.println("Для выхода нажмите 0");
                int value = requestValue();
                if (value == 1) {
                    System.out.println("Для просмотра счетов нажмите 1");
                    System.out.println("Для удаления счета нажмите 2");
                    System.out.println("Для добавления счета нажмите 3");
                    System.out.println("Для выхода нажмите 0");
                    int choiceAccount = requestValue();
                    if (choiceAccount == 1) {
                        List<AccountDTO> accountDTO = accountService.findAllByClientId(clientId);
                        if (accountDTO.isEmpty()) {
                            System.out.println("У вас пока нет счетов");
                        } else {
                            System.out.println(accountDTO);
                        }
                    } else if (choiceAccount == 2) {
                        System.out.println("Введите номер счета");
                        int accountId = requestValue();
                        AccountDTO accountDTO = accountService.delete(accountId, clientId);
                        if (accountDTO == null) {
                            System.out.println("Не удалось удалить запись");
                        } else {
                            System.out.println(accountDTO);
                        }
                    } else if (choiceAccount == 3) {
                        String nameAccount = requestTitle("Введите имя счета");
                        System.out.println("Введите баланс карты");
                        int balance = requestValue();
                        AccountDTO accountDTO = accountService.insert(nameAccount, balance, clientId);
                        if (accountDTO == null) {
                            System.out.println("Не удалось добавить запись");
                        } else {
                            System.out.println(accountDTO);
                        }
                    } else if (choiceAccount == 0) {
                        System.out.println("До свидания.");
                    }
                } else if (value == 2) {
                    System.out.println("Для просмотра категорий нажмите 1");
                    System.out.println("Для удаления категории нажмите 2");
                    System.out.println("Для добавления категории нажмите 3");
                    System.out.println("Для изменения категории нажмите 4");
                    System.out.println("Для выхода нажмите 0");
                    int choiceCategory = requestValue();
                    if (choiceCategory == 1) {
                        List<CategoryDTO> categoryDTOS = categoryService.findAllByClientId(clientId);
                        if (categoryDTOS.isEmpty()) {
                            System.out.println("У вас пока нет записей");
                        } else {
                            System.out.println(categoryDTOS);
                        }
                    } else if (choiceCategory == 2) {
                        System.out.println("Введите номер категории");
                        int categoryId = requestValue();
                        CategoryDTO categoryDTO = categoryService.delete(categoryId, clientId);
                        if (categoryDTO == null) {
                            System.out.println("Не удалось удалить запись");
                        } else {
                            System.out.println(categoryDTO);
                        }
                    } else if (choiceCategory == 3) {
                        String nameCategory = requestTitle("Введите имя категории");
                        CategoryDTO categoryDTO = categoryService.insert(nameCategory, clientId);
                        if (categoryDTO == null) {
                            System.out.println("Не удалось добавить запись");
                        } else {
                            System.out.println(categoryDTO);
                        }
                    } else if (choiceCategory == 4) {
                        String nameCategory = requestTitle("Введите новое имя категории");
                        System.out.println("Введите номер категории");
                        int categoryId = requestValue();
                        CategoryDTO categoryDTO = categoryService.update(nameCategory, categoryId, clientId);
                        if (categoryDTO != null) {
                            System.out.println("Изменение прошло успешно");
                        } else {
                            System.out.println("Ошибка при изменение");
                        }
                    } else if (choiceCategory == 0) {
                        System.out.println("До свидания.");
                    }
                } else if (value == 3) {
                    System.out.println("Для просмотра дохода по категориям нажмите 1");
                    System.out.println("Для просмотра расхода по категориям нажмите 2");
                    System.out.println("Для выхода нажмите 0");
                    LocalTime time = LocalTime.of(0, 0, 0);
                    int choiceTransactionReport = requestValue();
                    if (choiceTransactionReport == 1) {
                        String start = requestTitle("Введите начальную дату поиска в формате '2023-01-01' год месяц день");
                        String end = requestTitle("Введите конечную дату поиска в формате '2023-01-01' год месяц день");
                        try {
                            List<CategoryReportDTO> categoryReportDTOS = categoryReportService.findAllIncomeCategoryForReport(
                                    LocalDate.parse(start), LocalDate.parse(end), clientId);
                            if (categoryReportDTOS.isEmpty()) {
                                System.out.println("У вас нет записей");
                            } else {
                                System.out.println(categoryReportDTOS);
                            }
                        } catch (RuntimeException e) {
                            System.out.println("Проверте правильность введенной даты");
                        }
                    } else if (choiceTransactionReport == 2) {
                        String start = requestTitle("Введите начальную дату поиска в формате '2023-01-01' год месяц день");
                        String end = requestTitle("Введите конечную дату поиска в формате '2023-01-01' год месяц день");
                        try {
                            List<CategoryReportDTO> categoryReportDTOS = categoryReportService.findAllExpensesCategoryForReport(
                                    LocalDate.parse(start), LocalDate.parse(end), clientId);
                            if (categoryReportDTOS.isEmpty()) {
                                System.out.println("У вас нет записей");
                            } else {
                                System.out.println(categoryReportDTOS);
                            }
                        } catch (RuntimeException e) {
                            System.out.println("Проверте правильность введенной даты");
                        }
                    } else if (choiceTransactionReport == 0) {
                        System.out.println("До свидания.");
                    }
                } else if (value == 4) {
                    System.out.println("Для списания средст со счета нажмите 1");
                    System.out.println("Для зачисления средст на счет нажмите 2");
                    System.out.println("Для перевода средст со счета на счет нажмите 3");
                    int choiceTransaction = requestValue();
                    if (choiceTransaction == 1) {
                        System.out.println(accountService.findAllByClientId(clientId));
                        System.out.println("Введите номер счета");
                        int accountId = requestValue();
                        if (accountId == 0) {
                            return;
                        }
                        System.out.println(categoryService.findAllByClientId(clientId));
                        System.out.println("Введите номер категории");
                        List<Integer> listCategoryId = listCategory();
                        if (listCategoryId.isEmpty()) {
                            return;
                        }
                        System.out.println("Введите сумму");
                        int amount = requestValue();
                        TransactionDTO transactionDTO = transactionService.insertFromAccount(accountId, amount, clientId, listCategoryId);
                        if (transactionDTO == null) {
                            System.out.println("Не удалось добавить запись");
                        } else {
                            System.out.println(transactionDTO);
                        }
                    } else if (choiceTransaction == 2) {
                        System.out.println(accountService.findAllByClientId(clientId));
                        System.out.println("Введите номер счета");
                        int accountId = requestValue();
                        if (accountId == 0) {
                            return;
                        }
                        System.out.println(categoryService.findAllByClientId(clientId));
                        System.out.println("Введите номер категории");
                        List<Integer> listCategoryId = listCategory();
                        if (listCategoryId.isEmpty()) {
                            return;
                        }
                        System.out.println("Введите сумму");
                        int amount = requestValue();
                        TransactionDTO transactionDTO = transactionService.insertToAccount(accountId, amount, clientId, listCategoryId);
                        if (transactionDTO == null) {
                            System.out.println("Не удалось добавить запись");
                        } else {
                            System.out.println(transactionDTO);
                        }
                    } else if (choiceTransaction == 3) {
                        System.out.println(accountService.findAllByClientId(clientId));
                        System.out.println("Введите номер счета для списания средств");
                        int accountFromId = requestValue();
                        if (accountFromId == 0) {
                            return;
                        }
                        System.out.println("Введите номер счета для зачисления средств");
                        int accountToId = requestValue();
                        if (accountToId == 0) {
                            return;
                        }
                        System.out.println(categoryService.findAllByClientId(clientId));
                        System.out.println("Введите номер категории");
                        List<Integer> listCategoryId = listCategory();
                        if (listCategoryId.isEmpty()) {
                            return;
                        }
                        System.out.println("Введите сумму");
                        int amount = requestValue();
                        TransactionDTO transactionDTO = transactionService.insertAccountToAccount(accountFromId, accountToId, amount, clientId, listCategoryId);
                        if (transactionDTO == null) {
                            System.out.println("Не удалось добавить запись");
                        } else {
                            System.out.println(transactionDTO);
                        }
                    }
                }
            } else if (choice == 0) {
                System.out.println("До свидания.");
            }
        }
    }

    private static String requestTitle(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(title);
        return scanner.nextLine();
    }

    private static Integer requestValue() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (RuntimeException e) {
            System.out.println("Введены некорректные данные");
        }
        return 0;
    }

    private static List<Integer> listCategory() {
        List<Integer> integers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] numbers = str.trim().split("\\s+");
        try {
            for (String number : numbers) {
                integers.add(Integer.valueOf(number));
            }
        } catch (Exception e) {
            System.out.println("Введены некорректные данные");
        }
        return integers;
    }
}