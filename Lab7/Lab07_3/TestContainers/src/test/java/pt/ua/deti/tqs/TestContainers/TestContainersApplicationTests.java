package pt.ua.deti.tqs.TestContainers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pt.ua.deti.tqs.TestContainers.Data.BookRepository;
import pt.ua.deti.tqs.TestContainers.Data.Book;

import java.util.Date;

@SpringBootTest
@Testcontainers
class TestContainersApplicationTests {

	private Book book;

	@Container
	public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest")
			.withDatabaseName("testcontainers")
			.withUsername("testcontainers")
			.withPassword("testcontainers");

	@Autowired
	private BookRepository bookRepository;

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
	}

	@BeforeAll
	static void startContainers() {
		postgresContainer.start();
	}

	@Test
	void contextLoads() {
		Book book =  new Book(1L,  "The Great Gatsby", "F. Scott Fitzgerald", "Publisher C", new Date(), "A classic novel about the Roaring Twenties", "Fiction");
		bookRepository.save(book);

		System.out.println("Context loads!");

	}




}
