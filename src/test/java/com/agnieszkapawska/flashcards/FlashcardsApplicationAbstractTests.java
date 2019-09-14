package com.agnieszkapawska.flashcards;

import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveDto;
import com.agnieszkapawska.flashcards.domain.dtos.FlashcardSaveResponseDto;
import com.agnieszkapawska.flashcards.domain.facades.FlashcardFacade;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FlashcardsApplication.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
public abstract class FlashcardsApplicationAbstractTests {
	@LocalServerPort
	private int serverPort;
	protected String baseUrl;
	protected FlashcardSaveDto flashcardSaveDto;
	protected FlashcardSaveResponseDto flashcardSaveResponseDto;
	@MockBean
	protected FlashcardFacade flashcardFacade;
	@Autowired
	protected TestRestTemplate testRestTemplate;

	@Before
	public void setUp() {
		baseUrl = "http://localhost:" + serverPort;
		flashcardSaveDto = new FlashcardSaveDto();
		flashcardSaveResponseDto = new FlashcardSaveResponseDto();
		flashcardSaveResponseDto.setId(3L);
	}
}
