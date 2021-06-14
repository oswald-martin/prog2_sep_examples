package SEP;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZeugnisTest {
    @Test
    public void calculateAverageTest() {
        // Erstellen eine Instanz der Klasse die Sie testen wollen. [0.5P]
        
        Student student = new Student("1");

        // Erstellen Sie ein Mock-Objekt [0.5 P]

        ZeugnisSystem sZeugnisSystem = mock(ZeugnisSystem.class);

        // Spezifizieren Sie das Mock-Objekt so, dass die Notenliste die Werte [4.0, 5.0] enthält [1 P]

        //when(sZeugnisSystem.getGrades("1")).thenReturn(List.of(4.0,5.0));
        when(sZeugnisSystem.getGrades("1")).thenThrow(IllegalArgumentException.class);
        
        // Berechnen sie den Durchschnitts Note mit Hilfe der Testinstanz
        // und verifizieren Sie den korrekten Wert. [1P]
        double average = student.calculateAverage(sZeugnisSystem);
        double expected = 4.5;
        assertEquals(expected, average);
        // Verifizieren Sie das Verhalten Ihres Systems
        // Die Funktion getGrades() muss genau ein Mal aufgerufen werden worden sein. [1 P]

        verify(sZeugnisSystem,times(1)).getGrades("1");

        // Die Methode getCurrentSemester() darf nicht aufgerufen worden sein [1P]

        verify(sZeugnisSystem,times(0)).getCurrentSemester("1");

        assertThrows(IllegalArgumentException.class, () -> student.calculateAverage(sZeugnisSystem));
    }
}

class Student {
    String id;

    public Student (String id) { this.id = id; }

    public double calculateAverage(ZeugnisSystem system) throws IllegalStateException {
        return system.getGrades(id).stream()
            .mapToDouble(Double::doubleValue)
            .average().orElseThrow(() -> new IllegalStateException("No grades"));
    }
}

interface ZeugnisSystem {
    List<Double> getGrades(String studentId);
    int getCurrentSemester(String studentId);
}
