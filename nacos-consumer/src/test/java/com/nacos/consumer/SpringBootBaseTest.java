package com.nacos.consumer;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@RunWith(SpringRunner.class)
//@SpringBootTest//(classes = ErmtApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebAppConfiguration
//@EnableCaching
//@ContextConfiguration
//@WithMockCustomUser(token="JFB {\"mobile\":\"18288964383\",\"appKey\":\"BOX\"}")
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class SpringBootBaseTest {

	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mvc;
	protected static MockHttpSession session;// 1.定义一个变量保存session
	protected static JSONObject user = JSONObject.parseObject("{" +
			"\"userId\": \"2ffc84e090634942a925cba7b711161d\"," +
			"\"userName\": \"鲁婷\"," +
			"\"deptId\": \"202\"," +
			"\"deptName\": \"罗德岛\"," +
			"\"mobile\": \"14787463583\"," +
			"\"tenantName\": \"广东省江门市江海公证处\"," +
			"\"tenantId\": \"60c1937a9da64515902329283e77a366\"" +
			"}");
	protected static final String Authorization="Authorization";
	protected static String authorization = "NmIzNTdlYzNmZTYzNDE4MWIzZDA2N2FhM2IwODVhZjFlYmFlODZiNzI1ZDI0NWIzYTU1ZDBmYmQ4M2I2NGVjNWFkZGFkNTY1MTZlYTQ4NGJiNmQ0NTVmZWVmZTQ4Yjhi";

//	protected static JSONObject user = JSONObject.parseObject(
//			"{" +
//			"        \"userId\": \"38fcde92999a4e379ae348999d9acfc2\"," +
//			"        \"userName\": \"鲁婷\"," +
//			"        \"deptId\": \"8\"," +
//			"        \"deptName\": \"业务中心\"," +
//			"        \"tenantId\": \"ea6938f3f0024ab2922773514f019bbd\"," +
//			"        \"tenantName\": \"明信公证处\"," +
//			"        \"mobile\": \"14787463583\"," +
//			"        \"depts\": \"[{\\\"empId\\\":\\\"8\\\",\\\"deptName\\\":\\\"业务中心\\\"}]\"," +
//			"        \"token\": \"MzAzMWM2ZjAyNjI0NGZlOGE5MjRiMTFlMmI2NjYxZDJmMDU3YTRlMDk1OGY0Njg0OWViMjdjNmE4YzEwMzAwNjEwZTcwNGMyOGQ5NjRiODk5NTFjNTY4NmYwYTJjZmRm\"" +
//			"    }");

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
		session = new MockHttpSession(); //2.初始化
		String[] keys = {"userId","userName","deptId","deptName","tenantId","tenantName","mobile"};
		for(String key : keys){
			session.setAttribute(key, user.get(key));
		}
		if(null == session.getAttribute(Authorization)) {
			session.setAttribute(Authorization, authorization);
		}

		mvc = MockMvcBuilders.webAppContextSetup(wac).defaultRequest(
				//统一设置Sesstion上header
				MockMvcRequestBuilders.get("/").session(session).header(Authorization,authorization)

		).build();

//		org.springframework.core.io.Resource smart = wac.getResource("classpath:smart-doc.json");
//		JSONObject docs = JSONObject.parseObject(smart.getInputStream(), StandardCharsets.UTF_8,JSONObject.class);
//		log.info(docs.toJSONString());
//
//		// 登陆
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/shusi/auth/loginbypwd").content("{\"mobile\":\"15858394868\",\"pwd\":\"970706Meng\"}").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON).session(session);//3.当某个请求需要session时，直接在构造器中绑定需要的session
//		ResultActions actions = mvc.perform(builder);
//		actions.andExpect(MockMvcResultMatchers.status().isOk());
//		actions.andDo(MockMvcResultHandlers.print());
//		actions.andReturn();
//		//.andExpect(jsonPath("$.code").value("200"))//登陆成功
	}



}
