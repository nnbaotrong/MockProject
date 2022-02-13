package r2s.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static r2s.edu.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import r2s.edu.IntegrationTest;
import r2s.edu.domain.Book;
import r2s.edu.repository.BookRepository;
import r2s.edu.service.BookService;
import r2s.edu.service.dto.BookDTO;
import r2s.edu.service.mapper.BookMapper;

/**
 * Integration tests for the {@link BookResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BookResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BOOK_IMAGE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BOOK_IMAGE_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BOOK_IMAGE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BOOK_IMAGE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BOOK_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BOOK_IMAGE_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BOOK_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BOOK_IMAGE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BOOK_IMAGE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BOOK_IMAGE_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BOOK_IMAGE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BOOK_IMAGE_3_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COST = "AAAAAAAAAA";
    private static final String UPDATED_COST = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PUB_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PUB_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_INVENTORY_NUMBER = 1;
    private static final Integer UPDATED_INVENTORY_NUMBER = 2;

    private static final ZonedDateTime DEFAULT_PRODUCT_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PRODUCT_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_DISCOUNT = 1;
    private static final Integer UPDATED_DISCOUNT = 2;

    private static final String ENTITY_API_URL = "/api/books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookRepository bookRepository;

    @Mock
    private BookRepository bookRepositoryMock;

    @Autowired
    private BookMapper bookMapper;

    @Mock
    private BookService bookServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMockMvc;

    private Book book;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .title(DEFAULT_TITLE)
            .bookImage1(DEFAULT_BOOK_IMAGE_1)
            .bookImage1ContentType(DEFAULT_BOOK_IMAGE_1_CONTENT_TYPE)
            .bookImage2(DEFAULT_BOOK_IMAGE_2)
            .bookImage2ContentType(DEFAULT_BOOK_IMAGE_2_CONTENT_TYPE)
            .bookImage3(DEFAULT_BOOK_IMAGE_3)
            .bookImage3ContentType(DEFAULT_BOOK_IMAGE_3_CONTENT_TYPE)
            .cost(DEFAULT_COST)
            .pubDate(DEFAULT_PUB_DATE)
            .inventoryNumber(DEFAULT_INVENTORY_NUMBER)
            .productUpdate(DEFAULT_PRODUCT_UPDATE)
            .discount(DEFAULT_DISCOUNT);
        return book;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .title(UPDATED_TITLE)
            .bookImage1(UPDATED_BOOK_IMAGE_1)
            .bookImage1ContentType(UPDATED_BOOK_IMAGE_1_CONTENT_TYPE)
            .bookImage2(UPDATED_BOOK_IMAGE_2)
            .bookImage2ContentType(UPDATED_BOOK_IMAGE_2_CONTENT_TYPE)
            .bookImage3(UPDATED_BOOK_IMAGE_3)
            .bookImage3ContentType(UPDATED_BOOK_IMAGE_3_CONTENT_TYPE)
            .cost(UPDATED_COST)
            .pubDate(UPDATED_PUB_DATE)
            .inventoryNumber(UPDATED_INVENTORY_NUMBER)
            .productUpdate(UPDATED_PRODUCT_UPDATE)
            .discount(UPDATED_DISCOUNT);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();
        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getBookImage1()).isEqualTo(DEFAULT_BOOK_IMAGE_1);
        assertThat(testBook.getBookImage1ContentType()).isEqualTo(DEFAULT_BOOK_IMAGE_1_CONTENT_TYPE);
        assertThat(testBook.getBookImage2()).isEqualTo(DEFAULT_BOOK_IMAGE_2);
        assertThat(testBook.getBookImage2ContentType()).isEqualTo(DEFAULT_BOOK_IMAGE_2_CONTENT_TYPE);
        assertThat(testBook.getBookImage3()).isEqualTo(DEFAULT_BOOK_IMAGE_3);
        assertThat(testBook.getBookImage3ContentType()).isEqualTo(DEFAULT_BOOK_IMAGE_3_CONTENT_TYPE);
        assertThat(testBook.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testBook.getPubDate()).isEqualTo(DEFAULT_PUB_DATE);
        assertThat(testBook.getInventoryNumber()).isEqualTo(DEFAULT_INVENTORY_NUMBER);
        assertThat(testBook.getProductUpdate()).isEqualTo(DEFAULT_PRODUCT_UPDATE);
        assertThat(testBook.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void createBookWithExistingId() throws Exception {
        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].bookImage1ContentType").value(hasItem(DEFAULT_BOOK_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bookImage1").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE_1))))
            .andExpect(jsonPath("$.[*].bookImage2ContentType").value(hasItem(DEFAULT_BOOK_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bookImage2").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE_2))))
            .andExpect(jsonPath("$.[*].bookImage3ContentType").value(hasItem(DEFAULT_BOOK_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bookImage3").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE_3))))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)))
            .andExpect(jsonPath("$.[*].pubDate").value(hasItem(sameInstant(DEFAULT_PUB_DATE))))
            .andExpect(jsonPath("$.[*].inventoryNumber").value(hasItem(DEFAULT_INVENTORY_NUMBER)))
            .andExpect(jsonPath("$.[*].productUpdate").value(hasItem(sameInstant(DEFAULT_PRODUCT_UPDATE))))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBooksWithEagerRelationshipsIsEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBooksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc
            .perform(get(ENTITY_API_URL_ID, book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.bookImage1ContentType").value(DEFAULT_BOOK_IMAGE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.bookImage1").value(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE_1)))
            .andExpect(jsonPath("$.bookImage2ContentType").value(DEFAULT_BOOK_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.bookImage2").value(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE_2)))
            .andExpect(jsonPath("$.bookImage3ContentType").value(DEFAULT_BOOK_IMAGE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.bookImage3").value(Base64Utils.encodeToString(DEFAULT_BOOK_IMAGE_3)))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST))
            .andExpect(jsonPath("$.pubDate").value(sameInstant(DEFAULT_PUB_DATE)))
            .andExpect(jsonPath("$.inventoryNumber").value(DEFAULT_INVENTORY_NUMBER))
            .andExpect(jsonPath("$.productUpdate").value(sameInstant(DEFAULT_PRODUCT_UPDATE)))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT));
    }

    @Test
    @Transactional
    void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .title(UPDATED_TITLE)
            .bookImage1(UPDATED_BOOK_IMAGE_1)
            .bookImage1ContentType(UPDATED_BOOK_IMAGE_1_CONTENT_TYPE)
            .bookImage2(UPDATED_BOOK_IMAGE_2)
            .bookImage2ContentType(UPDATED_BOOK_IMAGE_2_CONTENT_TYPE)
            .bookImage3(UPDATED_BOOK_IMAGE_3)
            .bookImage3ContentType(UPDATED_BOOK_IMAGE_3_CONTENT_TYPE)
            .cost(UPDATED_COST)
            .pubDate(UPDATED_PUB_DATE)
            .inventoryNumber(UPDATED_INVENTORY_NUMBER)
            .productUpdate(UPDATED_PRODUCT_UPDATE)
            .discount(UPDATED_DISCOUNT);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getBookImage1()).isEqualTo(UPDATED_BOOK_IMAGE_1);
        assertThat(testBook.getBookImage1ContentType()).isEqualTo(UPDATED_BOOK_IMAGE_1_CONTENT_TYPE);
        assertThat(testBook.getBookImage2()).isEqualTo(UPDATED_BOOK_IMAGE_2);
        assertThat(testBook.getBookImage2ContentType()).isEqualTo(UPDATED_BOOK_IMAGE_2_CONTENT_TYPE);
        assertThat(testBook.getBookImage3()).isEqualTo(UPDATED_BOOK_IMAGE_3);
        assertThat(testBook.getBookImage3ContentType()).isEqualTo(UPDATED_BOOK_IMAGE_3_CONTENT_TYPE);
        assertThat(testBook.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBook.getPubDate()).isEqualTo(UPDATED_PUB_DATE);
        assertThat(testBook.getInventoryNumber()).isEqualTo(UPDATED_INVENTORY_NUMBER);
        assertThat(testBook.getProductUpdate()).isEqualTo(UPDATED_PRODUCT_UPDATE);
        assertThat(testBook.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookWithPatch() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book using partial update
        Book partialUpdatedBook = new Book();
        partialUpdatedBook.setId(book.getId());

        partialUpdatedBook.cost(UPDATED_COST).productUpdate(UPDATED_PRODUCT_UPDATE);

        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBook))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getBookImage1()).isEqualTo(DEFAULT_BOOK_IMAGE_1);
        assertThat(testBook.getBookImage1ContentType()).isEqualTo(DEFAULT_BOOK_IMAGE_1_CONTENT_TYPE);
        assertThat(testBook.getBookImage2()).isEqualTo(DEFAULT_BOOK_IMAGE_2);
        assertThat(testBook.getBookImage2ContentType()).isEqualTo(DEFAULT_BOOK_IMAGE_2_CONTENT_TYPE);
        assertThat(testBook.getBookImage3()).isEqualTo(DEFAULT_BOOK_IMAGE_3);
        assertThat(testBook.getBookImage3ContentType()).isEqualTo(DEFAULT_BOOK_IMAGE_3_CONTENT_TYPE);
        assertThat(testBook.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBook.getPubDate()).isEqualTo(DEFAULT_PUB_DATE);
        assertThat(testBook.getInventoryNumber()).isEqualTo(DEFAULT_INVENTORY_NUMBER);
        assertThat(testBook.getProductUpdate()).isEqualTo(UPDATED_PRODUCT_UPDATE);
        assertThat(testBook.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateBookWithPatch() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book using partial update
        Book partialUpdatedBook = new Book();
        partialUpdatedBook.setId(book.getId());

        partialUpdatedBook
            .title(UPDATED_TITLE)
            .bookImage1(UPDATED_BOOK_IMAGE_1)
            .bookImage1ContentType(UPDATED_BOOK_IMAGE_1_CONTENT_TYPE)
            .bookImage2(UPDATED_BOOK_IMAGE_2)
            .bookImage2ContentType(UPDATED_BOOK_IMAGE_2_CONTENT_TYPE)
            .bookImage3(UPDATED_BOOK_IMAGE_3)
            .bookImage3ContentType(UPDATED_BOOK_IMAGE_3_CONTENT_TYPE)
            .cost(UPDATED_COST)
            .pubDate(UPDATED_PUB_DATE)
            .inventoryNumber(UPDATED_INVENTORY_NUMBER)
            .productUpdate(UPDATED_PRODUCT_UPDATE)
            .discount(UPDATED_DISCOUNT);

        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBook))
            )
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getBookImage1()).isEqualTo(UPDATED_BOOK_IMAGE_1);
        assertThat(testBook.getBookImage1ContentType()).isEqualTo(UPDATED_BOOK_IMAGE_1_CONTENT_TYPE);
        assertThat(testBook.getBookImage2()).isEqualTo(UPDATED_BOOK_IMAGE_2);
        assertThat(testBook.getBookImage2ContentType()).isEqualTo(UPDATED_BOOK_IMAGE_2_CONTENT_TYPE);
        assertThat(testBook.getBookImage3()).isEqualTo(UPDATED_BOOK_IMAGE_3);
        assertThat(testBook.getBookImage3ContentType()).isEqualTo(UPDATED_BOOK_IMAGE_3_CONTENT_TYPE);
        assertThat(testBook.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testBook.getPubDate()).isEqualTo(UPDATED_PUB_DATE);
        assertThat(testBook.getInventoryNumber()).isEqualTo(UPDATED_INVENTORY_NUMBER);
        assertThat(testBook.getProductUpdate()).isEqualTo(UPDATED_PRODUCT_UPDATE);
        assertThat(testBook.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();
        book.setId(count.incrementAndGet());

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc
            .perform(delete(ENTITY_API_URL_ID, book.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
