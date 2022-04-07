package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AdminTest {

    Admin test;

    public void setupStage1(){
        test=new Admin("1107835251","EstaIntegradoraSeGana");
    }

    @Test
    void test1(){
        setupStage1();
        assertEquals("1107835251",test.getId());
    }

    @Test
    void test2(){
        setupStage1();
        assertEquals("EstaIntegradoraSeGana",test.getPassword());
    }


}