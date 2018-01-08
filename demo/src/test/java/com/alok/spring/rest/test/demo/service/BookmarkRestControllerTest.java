package com.alok.spring.rest.test.demo.service;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alok.spring.rest.test.demo.DemoApplication;
import com.alok.spring.rest.test.demo.entity.Account;
import com.alok.spring.rest.test.demo.entity.Bookmark;
import com.alok.spring.rest.test.demo.jpa.AccountRepository;
import com.alok.spring.rest.test.demo.jpa.BookmarkRepository;

//@RunWith(AbstractJUnit4SpringContextTests.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class BookmarkRestControllerTest extends AbstractJUnit4SpringContextTests {

	private MockMvc mockMvc;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private BookmarkRepository bookmarkRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private Account account;
	private List<Bookmark> bookmarkList = new ArrayList<>();

	private String username = "alok_test";

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		/*
		 * Java 1.8 this.mappingJackson2HttpMessageConverter =
		 * Arrays.asList(converters).stream() .filter(hmc -> hmc instanceof
		 * MappingJackson2HttpMessageConverter) .findAny() .orElse(null);
		 */
		this.mappingJackson2HttpMessageConverter = null;
		for (HttpMessageConverter<?> hmc : Arrays.asList(converters)) {
			if (hmc instanceof MappingJackson2HttpMessageConverter) {
				this.mappingJackson2HttpMessageConverter = hmc;
				break;
			}
		}

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		// remove/add in DB
		bookmarkRepository.deleteAllInBatch();
		accountRepository.deleteAllInBatch();
		account = accountRepository.save(new Account(username, "password"));
		bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "www.mail.goolgle.com", "Gmail")));
		bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "www.facebook.com", "Facebook")));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		//bookmarkRepository.deleteAllInBatch();
		//accountRepository.deleteAllInBatch();
	}

	@Test
	public void testUserNotFoundPostMenthod() throws Exception {
		mockMvc.perform(post("/george/bookmarks/")
				.content(this.json(new Bookmark(this.account, "www.twiter,com", "Twiter")))
				.contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testUserNotFoundGetMethod() throws Exception {
		mockMvc.perform(get("/george/bookmarks/").contentType(contentType)).andExpect(status().isNotFound());
	}

	/**
	 * Test method for
	 * {@link com.alok.spring.rest.test.demo.service.BookmarkRestController#readBookmarks(java.lang.String)}.
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	@Test
	public final void testReadBookmarks() throws IOException, Exception {
		mockMvc.perform(
				get("/" + username + "/bookmarks/").content(this.json(new Bookmark())).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public final void testReadBookmark() throws IOException, Exception {
		mockMvc.perform(get("/" + username + "/bookmarks/" + bookmarkList.get(0).getId())
				.content(this.json(new Bookmark())).contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public final void testReadBookmarkNotFound() throws IOException, Exception {
		mockMvc.perform(get("/" + username + "/bookmarks/" + 99).content(this.json(new Bookmark()))
				.contentType(contentType)).andExpect(status().isNotFound());
	}

	/**
	 * Test method for
	 * {@link com.alok.spring.rest.test.demo.service.BookmarkRestController#add(java.lang.String, com.alok.spring.rest.test.demo.entity.Bookmark)}.
	 * @throws Exception 
	 */
	@Test
	public final void testAdd() throws Exception {
		//String bookmarkJson = json(new Bookmark(this.account, "www.twiter,com", "Twiter"));
		String bookmarkJson = json(new Bookmark(this.account, "www.twiter,com", "Twiter"));
		
		this.mockMvc.perform(post("/" + username + "/bookmarks")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isCreated());
	}
	
	@Test
	public final void testAddUriIsNull() throws Exception {
		String bookmarkJson = json(new Bookmark(this.account, null, "Twiter"));
		
		this.mockMvc.perform(post("/" + username + "/bookmarks")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public final void testAddDescriptionIsNull() throws Exception {
		String bookmarkJson = json(new Bookmark(this.account, "www.twiter,com", ""));
		
		this.mockMvc.perform(post("/" + username + "/bookmarks")
				.contentType(contentType)
				.content(bookmarkJson))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test method for
	 * {@link com.alok.spring.rest.test.demo.service.BookmarkRestController#readBookmark(java.lang.String, java.lang.Long)}.
	 */

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();

		mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
