package com.tabatskiy.web.repository;

import com.tabatskiy.web.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository subj;

    @Test
    public void findByIdAndClientId() {
        Category category = subj.findByIdAndClientId(1, 1).orElse(null);

        assertNotNull(category);
        assertEquals(Integer.valueOf(1), category.getId());
        assertEquals("бензин", category.getName());
    }

    @Test
    public void findByIdAndClientId_notFound() {
        assertFalse(subj.findByIdAndClientId(10, 1).isPresent());
    }

    @Test
    public void findAllByClientId() {
        List<Category> categories = subj.findAllByClientId(1);

        assertEquals(4, categories.size());
    }

    @Test
    public void findAllByClientId_notFound() {
        assertTrue(subj.findAllByClientId(10).isEmpty());
    }

    @Test
    public void deleteByIdAndClientId() {
        subj.deleteByIdAndClientId(4, 1);

        assertFalse(subj.findByIdAndClientId(4, 1).isPresent());
    }
}