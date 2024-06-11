package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository subj;

    @Test
    public void findByIdAndClientId() {
        Account account = subj.findByIdAndClientId(1, 1).orElse(null);

        assertNotNull(account);
        assertEquals(Integer.valueOf(1), account.getId());
        assertEquals("alfa", account.getName());
        assertEquals(Integer.valueOf(1000), account.getBalance());
    }

    @Test
    public void findByIdAndClientId_notFound() {
        assertFalse(subj.findByIdAndClientId(10, 1).isPresent());
    }

    @Test
    public void findAllByClientId() {
        List<Account> accounts = subj.findAllByClientId(1);

        assertEquals(3, accounts.size());
    }

    @Test
    public void findAllByClientId_notFound() {
        assertTrue(subj.findAllByClientId(10).isEmpty());
    }

    @Test
    public void deleteAccountByIdAndClientId() {
        subj.deleteAccountByIdAndClientId(3, 1);

        assertFalse(subj.findByIdAndClientId(3, 1).isPresent());
    }
}