package com.alok.spring.rest.test.demo;

/*
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration*/
public class DemoApplicationTests {


/*    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account;

    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);
    	this.mappingJackson2HttpMessageConverter = null;
    	
       //Alok TBD 	

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.bookmarkRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();

        this.account = accountRepository.save(new Account(userName, "password"));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + userName, "A description")));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + userName, "A description")));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/george/bookmarks/")
                .content(this.json(new Bookmark()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleBookmark() throws Exception {
        mockMvc.perform(get("/" + userName + "/bookmarks/"
                + this.bookmarkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(contentType))
                .andExpect((ResultMatcher) jsonPath("$.id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect((ResultMatcher) jsonPath("$.uri", is("http://bookmark.com/1/" + userName)))
                .andExpect((ResultMatcher) jsonPath("$.description", is("A description")));
    }

    @Test
    public void readBookmarks() throws Exception {
        mockMvc.perform(get("/" + userName + "/bookmarks"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(contentType))
                //.andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect((ResultMatcher) jsonPath("$[0].uri", is("http://bookmark.com/1/" + userName)))
                .andExpect((ResultMatcher) jsonPath("$[0].description", is("A description")))
                .andExpect((ResultMatcher) jsonPath("$[1].id", is(this.bookmarkList.get(1).getId().intValue())))
                .andExpect((ResultMatcher) jsonPath("$[1].uri", is("http://bookmark.com/2/" + userName)))
                .andExpect((ResultMatcher) jsonPath("$[1].description", is("A description")));
    }

    @Test
    public void createBookmark() throws Exception {
        String bookmarkJson = json(new Bookmark(
                this.account, "http://spring.io", "a bookmark to the best resource for Spring news and information"));

        this.mockMvc.perform(post("/" + userName + "/bookmarks")
                .contentType(contentType)
                .content(bookmarkJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }*/
}
