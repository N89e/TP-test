import org.example.Book;
import org.example.ExternalBookService;
import org.example.Library;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryTest {

    @Mock
    private ExternalBookService externalService;

    @InjectMocks
    private Library library;

    @BeforeAll
    static void initAll() {
        System.out.println("=== Début de la campagne de tests ===");
    }

    @BeforeEach
    public void setUp() {
        library = new Library(externalService);
        System.out.println("Fin du test en cours.");
    }
    @AfterEach
    void tearDown() {
        System.out.println("Fin du test en cours.");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("=== Fin de la campagne de tests ===");
    }

    @Test
    void testGetAuteurs() {
        Book book = new Book("Titre1", "Auteur1");
        assertEquals("Auteur1", book.getAuteur());
    }

    @Test
    void testEmprunteur() {
        Book book = new Book("Titre1", "Auteur1");
        assertTrue(book.isDisponible());
        book.emprunter();
        assertFalse(book.isDisponible());
    }

    @Test
    void testReourner() {
        Book book = new Book("Titre1", "Auteur1");
        book.emprunter();
        assertFalse(book.isDisponible());
        book.retourner();
        assertTrue(book.isDisponible());
    }

    @Test
    void testAddBook() {
        Book book = new Book("Titre1", "Auteur1");

        library.addBook(book);

        assertNotNull(library.getLivres());
        assertTrue(library.getLivres().contains(book));
    }

    @Test
    void testBorrowAvailableBook() {
        Book book = new Book("Titre1", "Auteur1");

        library.addBook(book);
        boolean result = library.borrowBook("Titre1");

        assertTrue(result);
        assertFalse(book.isDisponible());
    }

    @Test
    void testBorrowNonExistingBook() {
        boolean result = library.borrowBook("NonExistingTitle");

        assertFalse(result);
    }

    @Test
    void testBorrowAlreadyBorrowedBook() {
        Book book = new Book("Titre1", "Auteur1");

        library.addBook(book);
        library.borrowBook("Titre1");
        boolean result = library.borrowBook("Titre1");

        assertFalse(result);
    }

    @Test
    public void testReturnBook() {
        Book book = new Book("Titre1", "Auteur1");

        library.addBook(book);
        library.borrowBook("Titre1");
        boolean result = library.returnBook("Titre1");

        assertTrue(result);
        assertTrue(book.isDisponible());
    }

    @ParameterizedTest
    @CsvSource({
            "3,1,2",
            "5,2,3",
            "4,4,0",
            "10,5,5"
    })
    public void testCountAvailableBooks(int totalBooks, int borrowedBooks, int expectedAvailable) {
        for (int i = 0; i < totalBooks; i++) {
            library.addBook(new Book("Titre"+i, "Auteur"+i));
        }
        for (int i = 0; i < borrowedBooks; i++) {
            library.borrowBook("Titre"+i);
        }

        assertEquals(expectedAvailable, library.countAvailableBooks());
    }


    @Test
    public void testCheckExternalAvailabilityTrue() {
        when(externalService.isBookAvailable("TitreExterne")).thenReturn(true);

        boolean available = library.checkExternalAvailability("TitreExterne");

        assertTrue(available);
        verify(externalService, times(1)).isBookAvailable("TitreExterne");
    }

    @Test
    public void testCheckExternalAvailabilityWithoutServiceConfigured() {
        Library libSansService = new Library(); // pas de service externe injecté
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            libSansService.checkExternalAvailability("Titre");
        });
        assertEquals("External service not configured", thrown.getMessage());
    }

    @Test
    public void testImportBookFromExternalSuccess() {
        Book externalBook = new Book("TitreExterne", "AuteurExterne");

        when(externalService.fetchBookDetails("TitreExterne")).thenReturn(externalBook);
        library.importBookFromExternal("TitreExterne");

        assertTrue(library.getLivres().contains(externalBook));
        verify(externalService, times(1)).fetchBookDetails("TitreExterne");
    }

    @Test
    public void testImportBookFromExternalFail() {
        when(externalService.fetchBookDetails("TitreInexistant")).thenReturn(null);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            library.importBookFromExternal("TitreInexistant");
        });

        assertEquals("Book not found in external service: TitreInexistant", thrown.getMessage());
        verify(externalService, times(1)).fetchBookDetails("TitreInexistant");
    }

    @Test
    public void testImportBookFromExternalWithoutServiceConfigured() {
        Library libSansService = new Library(); // pas de service externe injecté
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            libSansService.importBookFromExternal("TitreQuelconque");
        });
        assertEquals("External service not configured", thrown.getMessage());
    }

    @Test
    public void testMultipleIsBookAvailableCalls() {
        when(externalService.isBookAvailable("Titre1")).thenReturn(true);
        when(externalService.isBookAvailable("Titre2")).thenReturn(false);
        when(externalService.isBookAvailable("Titre3")).thenReturn(true);

        assertTrue(library.checkExternalAvailability("Titre1"));
        assertFalse(library.checkExternalAvailability("Titre2"));
        assertTrue(library.checkExternalAvailability("Titre3"));

        InOrder inOrder = inOrder(externalService);
        inOrder.verify(externalService).isBookAvailable("Titre1");
        inOrder.verify(externalService).isBookAvailable("Titre2");
        inOrder.verify(externalService).isBookAvailable("Titre3");

        verify(externalService, times(3)).isBookAvailable(anyString());
    }
}
