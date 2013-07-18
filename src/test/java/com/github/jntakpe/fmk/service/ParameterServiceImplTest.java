package com.github.jntakpe.fmk.service;

import com.github.jntakpe.fmk.domain.Parameter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test des services associés à l'entité {@link Parameter}
 *
 * @author jntakpe
 */
@ContextConfiguration("classpath*:applicationContext-test.xml")
public class ParameterServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ParameterService parameterService;

    private static final String EXISTING_KEY = "smtp.port";

    @Test
    public void findByKeyTest() {
        assertThat(parameterService.findByKey("testkey")).isNull();
        assertThat(parameterService.findByKey(EXISTING_KEY).getValue()).isEqualTo("25");
    }

    @Test
    public void saveTest() {
        //New param
        Parameter test = new Parameter();
        test.setKey("test");
        test.setValue("test-value");
        parameterService.save(test);
        Parameter managedTest = parameterService.findByKey("test");
        assertThat(managedTest).isEqualTo(test);
        assertThat(managedTest.getValue()).isEqualTo("test-value");


        //Update param
        managedTest.setKey("updated-test");
        managedTest.setValue("updated-value");
        assertThat(managedTest.getKey()).isEqualTo("updated-test");
        assertThat(managedTest.getValue()).isEqualTo("updated-value");
        assertThat(parameterService.findByKey("test")).isNull();
    }

    @Test
    public void deleteTest() {
        long nbParams = parameterService.count();
        Parameter existingParam = parameterService.findByKey(EXISTING_KEY);
        parameterService.delete(existingParam);
        assertThat(parameterService.findByKey(EXISTING_KEY)).isNull();
        assertThat(nbParams - 1).isEqualTo(parameterService.count());
    }

    @Test
    public void isAvaillableTest() {
        Parameter existingParam = parameterService.findByKey(EXISTING_KEY);
        assertThat(parameterService.isAvaillable("key", null, EXISTING_KEY)).isFalse();
        assertThat(parameterService.isAvaillable("key", null, "newkey")).isTrue();
        assertThat(parameterService.isAvaillable("key", existingParam.getId(), EXISTING_KEY)).isTrue();
        assertThat(parameterService.isAvaillable("key", existingParam.getId(), "newkey")).isTrue();
        assertThat(parameterService.isAvaillable("key", 999L, EXISTING_KEY)).isFalse();
    }
}
