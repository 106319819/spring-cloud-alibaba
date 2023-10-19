package com.nacos.consumer.controller;

import com.nacos.consumer.SpringBootBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.fail;

public class HelloControllerTest extends SpringBootBaseTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void hello() {

        MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.get("/hello");
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
            fail("/hello/echo/{message}");
        }
    }


    @Test
    public void isGrayByRestTemplate() {

        MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.get("/is-gray");
        mhr.accept("application/json;charset=UTF-8");
        mhr.contentType("application/json;charset=UTF-8");
        mhr.header(Authorization, authorization);
        mhr.header("Gray", "true");
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
            fail("/is-gray");
        }
    }

    @Test
    public void isGrayByFeign() {

        MockHttpServletRequestBuilder mhr = MockMvcRequestBuilders.get("/feign/is-gray");
        mhr.accept("application/json;charset=UTF-8");
        mhr.contentType("application/json;charset=UTF-8");
        mhr.header(Authorization, authorization);
        mhr.header("Gray", "false");
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
            fail("/feign/is-gray");
        }
    }
}