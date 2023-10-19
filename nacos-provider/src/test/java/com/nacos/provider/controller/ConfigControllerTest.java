package com.nacos.provider.controller;

import com.nacos.provider.SpringBootBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

public class ConfigControllerTest extends SpringBootBaseTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void group() {
        MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.get("/config/group");
        mhr.accept("application/json;charset=UTF-8");
        mhr.contentType("application/json;charset=UTF-8");
        mhr.header(Authorization, authorization);
        mhr.session(session);
        try {

            ResultActions actions = mvc.perform(mhr);
            actions.andExpect(MockMvcResultMatchers.status().isOk());
            actions.andDo(MockMvcResultHandlers.print());
            //actions.andDo(document("{class-name}/create"));
            actions.andReturn();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("config/group");
        }
    }
}