package model;

import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CinemaSystemTest {
    CinemaSystem test = new CinemaSystem();
    private ObservableList<Show> showsData = FXCollections.observableArrayList();

    public void setupStage1(){
        showsData = FXCollections.observableArrayList();
        test.addShow("movieName", 40, LocalDate.of(2018, 10, 30), 11, 00, "Sala Mini");
    }

    @Test
    void test1() {
        setupStage1();
        assertEquals(false, test.theSpaceIsAvailable(LocalDate.of(2018, 10, 30),LocalTime.of(11, 00),LocalTime.of(11, 50),"Sala Mini"));
    }

    @Test
    void test2() {
        setupStage1();
        assertEquals(true, test.theSpaceIsAvailable(LocalDate.of(2018, 10, 30),LocalTime.of(12, 00),LocalTime.of(13, 50),"Sala Mini"));
    }

    @Test
    void test3() {
        setupStage1();
        assertEquals(true, test.theSpaceIsAvailable(LocalDate.of(2018, 10, 31),LocalTime.of(11, 00),LocalTime.of(13, 50),"Sala Mini"));
    }

    @Test
    void test4() {
        setupStage1();
        assertEquals(true, test.theSpaceIsAvailable(LocalDate.of(2018, 10, 30),LocalTime.of(11, 00),LocalTime.of(13, 50),"Sala Mediana"));
    }

    @Test
    void test5() {
        setupStage1();
        assertEquals(false, test.theSpaceIsAvailable(LocalDate.of(2018, 10, 30),LocalTime.of(9, 00),LocalTime.of(13, 50),"Sala Mini"));
    }
}