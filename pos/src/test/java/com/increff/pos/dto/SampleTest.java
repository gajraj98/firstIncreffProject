package com.increff.pos.dto;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class SampleTest {

    @Test
    public void testFiles() {
        InputStream is = null;
        is = SampleTest.class.getResourceAsStream("/com/increff/pos/pos.tsv");
        assertNotNull(is);
    }

}
