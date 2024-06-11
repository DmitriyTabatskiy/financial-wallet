package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ClientRepositoryTest {

    @Autowired
    ClientRepository subj;

    @Test
    public void findByEmailAndPassword() {
        Client client = subj.findByEmailAndPassword(
                "yana@gmail.com",
                "$2a$12$36u2w7onH2qmrIevlrGIMOmVB4flgNv8oIBFLyT3GNAcL.ZBamQTq").orElse(null);

        assertNotNull(client);
        assertEquals(Integer.valueOf(1), client.getId());
        assertEquals("yana@gmail.com", client.getEmail());
        assertEquals("$2a$12$36u2w7onH2qmrIevlrGIMOmVB4flgNv8oIBFLyT3GNAcL.ZBamQTq", client.getPassword());
    }

    @Test
    public void findByEmailAndPassword_notFound() {
        assertFalse(subj.findByEmailAndPassword(
                        "yana@gmail1.com",
                        "$2a$12$36u2w7onH2qmrIevlrGIMOmVB4flgNv8oIBFLyT3GNAcL.ZBamQTq")
                .isPresent());
    }

    @Test
    public void findByEmail() {
        Client client = subj.findByEmail("yana@gmail.com").orElse(null);

        assertNotNull(client);
        assertEquals(Integer.valueOf(1), client.getId());
        assertEquals("yana@gmail.com", client.getEmail());
        assertEquals("$2a$12$36u2w7onH2qmrIevlrGIMOmVB4flgNv8oIBFLyT3GNAcL.ZBamQTq", client.getPassword());
    }

    @Test
    public void findByEmail_notFound() {
        assertFalse(subj.findByEmail("yana@gmail11.com").isPresent());
    }
}