package r2s.edu.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import r2s.edu.IntegrationTest;
import r2s.edu.domain.Publisher;
import r2s.edu.repository.PublisherRepository;
import r2s.edu.service.dto.PublisherDTO;
import r2s.edu.service.mapper.PublisherMapper;

/**
 * Integration tests for the {@link PublisherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PublisherResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHONE_NUMBER = 1;
    private static final Integer UPDATED_PHONE_NUMBER = 2;

    private static final String ENTITY_API_URL = "/api/publishers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherMapper publisherMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublisherMockMvc;

    private Publisher publisher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publisher createEntity(EntityManager em) {
        Publisher publisher = new Publisher().name(DEFAULT_NAME).address(DEFAULT_ADDRESS).phoneNumber(DEFAULT_PHONE_NUMBER);
        return publisher;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publisher createUpdatedEntity(EntityManager em) {
        Publisher publisher = new Publisher().name(UPDATED_NAME).address(UPDATED_ADDRESS).phoneNumber(UPDATED_PHONE_NUMBER);
        return publisher;
    }

    @BeforeEach
    public void initTest() {
        publisher = createEntity(em);
    }

    @Test
    @Transactional
    void createPublisher() throws Exception {
        int databaseSizeBeforeCreate = publisherRepository.findAll().size();
        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);
        restPublisherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isCreated());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeCreate + 1);
        Publisher testPublisher = publisherList.get(publisherList.size() - 1);
        assertThat(testPublisher.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublisher.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPublisher.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void createPublisherWithExistingId() throws Exception {
        // Create the Publisher with an existing ID
        publisher.setId(1L);
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        int databaseSizeBeforeCreate = publisherRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublisherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPublishers() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        // Get all the publisherList
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publisher.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getPublisher() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        // Get the publisher
        restPublisherMockMvc
            .perform(get(ENTITY_API_URL_ID, publisher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publisher.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingPublisher() throws Exception {
        // Get the publisher
        restPublisherMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPublisher() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();

        // Update the publisher
        Publisher updatedPublisher = publisherRepository.findById(publisher.getId()).get();
        // Disconnect from session so that the updates on updatedPublisher are not directly saved in db
        em.detach(updatedPublisher);
        updatedPublisher.name(UPDATED_NAME).address(UPDATED_ADDRESS).phoneNumber(UPDATED_PHONE_NUMBER);
        PublisherDTO publisherDTO = publisherMapper.toDto(updatedPublisher);

        restPublisherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publisherDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publisherDTO))
            )
            .andExpect(status().isOk());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
        Publisher testPublisher = publisherList.get(publisherList.size() - 1);
        assertThat(testPublisher.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublisher.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPublisher.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingPublisher() throws Exception {
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();
        publisher.setId(count.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, publisherDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPublisher() throws Exception {
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();
        publisher.setId(count.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPublisher() throws Exception {
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();
        publisher.setId(count.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(publisherDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePublisherWithPatch() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();

        // Update the publisher using partial update
        Publisher partialUpdatedPublisher = new Publisher();
        partialUpdatedPublisher.setId(publisher.getId());

        partialUpdatedPublisher.phoneNumber(UPDATED_PHONE_NUMBER);

        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublisher.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublisher))
            )
            .andExpect(status().isOk());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
        Publisher testPublisher = publisherList.get(publisherList.size() - 1);
        assertThat(testPublisher.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPublisher.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPublisher.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdatePublisherWithPatch() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();

        // Update the publisher using partial update
        Publisher partialUpdatedPublisher = new Publisher();
        partialUpdatedPublisher.setId(publisher.getId());

        partialUpdatedPublisher.name(UPDATED_NAME).address(UPDATED_ADDRESS).phoneNumber(UPDATED_PHONE_NUMBER);

        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPublisher.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPublisher))
            )
            .andExpect(status().isOk());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
        Publisher testPublisher = publisherList.get(publisherList.size() - 1);
        assertThat(testPublisher.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPublisher.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPublisher.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingPublisher() throws Exception {
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();
        publisher.setId(count.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, publisherDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPublisher() throws Exception {
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();
        publisher.setId(count.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(publisherDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPublisher() throws Exception {
        int databaseSizeBeforeUpdate = publisherRepository.findAll().size();
        publisher.setId(count.incrementAndGet());

        // Create the Publisher
        PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPublisherMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(publisherDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Publisher in the database
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePublisher() throws Exception {
        // Initialize the database
        publisherRepository.saveAndFlush(publisher);

        int databaseSizeBeforeDelete = publisherRepository.findAll().size();

        // Delete the publisher
        restPublisherMockMvc
            .perform(delete(ENTITY_API_URL_ID, publisher.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Publisher> publisherList = publisherRepository.findAll();
        assertThat(publisherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
