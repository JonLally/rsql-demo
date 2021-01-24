package com.jonl.rsql.rsqldemo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class RsqlDemoApplicationTests {

  @Autowired
  private MockMvc mvc;

	@Test
	void findAll_withNoParams_returnsExpectedResults() throws Exception {
    mvc.perform(get("/customer"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$._embedded.customers").isArray())
      .andExpect(jsonPath("$._embedded.customers", hasSize(6)))
      .andExpect(jsonPath("$.page").doesNotExist());
  }
  
  @Test
	void findAll_withPageable_returnsExpectedPagedResults() throws Exception {
    mvc.perform(get("/customer").param("page", "0").param("size", "3"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$._embedded.customers").isArray())
      .andExpect(jsonPath("$._embedded.customers", hasSize(3)))
      .andExpect(jsonPath("$.page.number", is(0)))
      .andExpect(jsonPath("$.page.size", is(3)))
      .andExpect(jsonPath("$.page.totalElements", is(6)))
      .andExpect(jsonPath("$.page.totalPages", is(2)));
  }
  
  @Test
	void findAll_withPageableAndSort_returnsExpectedPagedAndSortedResults() throws Exception {
    mvc.perform(get("/customer").param("page", "0").param("size", "3").param("sort", "lastName,desc"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$._embedded.customers").isArray())
      .andExpect(jsonPath("$._embedded.customers", hasSize(3)))
      .andExpect(jsonPath("$._embedded.customers[0].lastName", is("Trotter")))
      .andExpect(jsonPath("$.page.number", is(0)))
      .andExpect(jsonPath("$.page.size", is(3)))
      .andExpect(jsonPath("$.page.totalElements", is(6)))
      .andExpect(jsonPath("$.page.totalPages", is(2)));
  }
  
  @Test
	void findAll_withSort_returnsExpectedSortedResults() throws Exception {
    mvc.perform(get("/customer").param("sort", "lastName,desc"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$._embedded.customers").isArray())
      .andExpect(jsonPath("$._embedded.customers", hasSize(6)))
      .andExpect(jsonPath("$._embedded.customers[0].lastName", is("Trotter")))
      .andExpect(jsonPath("$.page").doesNotExist());
  }
  
  @Test
	void findAll_withQuery_returnsExpectedResults() throws Exception {
    mvc.perform(get("/customer").param("spec", "firstName==Dave"))
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$._embedded.customers").isArray())
      .andExpect(jsonPath("$._embedded.customers", hasSize(1)))
      .andExpect(jsonPath("$._embedded.customers[0].firstName", is("Dave")))
      .andExpect(jsonPath("$._embedded.customers[0].lastName", is("Trotter")))
      .andExpect(jsonPath("$.page").doesNotExist());
	}
}
