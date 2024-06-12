package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pass enum values to our test method")
class StudentContainerTest {

    private StudentContainer studentContainer;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    public StudentContainerTest() {
        studentContainer = new StudentContainer();
    }

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @DisplayName("Adding students to the container")
    @ParameterizedTest(name = "{index} => entry={0}")
    @CsvSource({"John Doe johnd@email.net,The student has been added.",
                "Jane Spark jspark@yahoo.com,The student has been added.",
                "John Deer johnd@email.net,The student has been added."})
    void addStudents(String entry, String expOutput) {
        studentContainer.addStudent(entry);
        assertEquals(expOutput, outputStreamCaptor.toString().trim());
    }
}