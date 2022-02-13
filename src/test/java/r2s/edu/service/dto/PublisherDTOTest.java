package r2s.edu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import r2s.edu.web.rest.TestUtil;

class PublisherDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublisherDTO.class);
        PublisherDTO publisherDTO1 = new PublisherDTO();
        publisherDTO1.setId(1L);
        PublisherDTO publisherDTO2 = new PublisherDTO();
        assertThat(publisherDTO1).isNotEqualTo(publisherDTO2);
        publisherDTO2.setId(publisherDTO1.getId());
        assertThat(publisherDTO1).isEqualTo(publisherDTO2);
        publisherDTO2.setId(2L);
        assertThat(publisherDTO1).isNotEqualTo(publisherDTO2);
        publisherDTO1.setId(null);
        assertThat(publisherDTO1).isNotEqualTo(publisherDTO2);
    }
}
