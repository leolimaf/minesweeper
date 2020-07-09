package br.com.leolimaf.minesweeper.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestField {

    private Field field;

    @BeforeEach
    void InitiateField() {
        field = new Field(3, 3);
    }

    @Test
    void TestNeighborDistance1Left() {
        Field leftNeighbor = new Field(3, 2);
        boolean result = field.addNeighbor(leftNeighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborDistance1Right() {
        Field rightNeighbor = new Field(3, 4);
        boolean result = field.addNeighbor(rightNeighbor);

        assertTrue(result);
    }

    @Test
    void TestNeighborDistance1Above() {
        Field neighborAbove = new Field(2, 3);
        boolean result = field.addNeighbor(neighborAbove);

        assertTrue(result);
    }

    @Test
    void TestNeighborDistance1Below() {
        Field neighborBelow = new Field(4, 3);
        boolean result = field.addNeighbor(neighborBelow);

        assertTrue(result);
    }

    @Test
    void TestNeighborDistance2() {
        Field diagonalNeighbor = new Field(2, 2);
        boolean result = field.addNeighbor(diagonalNeighbor);

        assertTrue(result);
    }

    @Test
    void TestNotNeighborDistance2() {
        Field diagonalNeighbor = new Field(1, 1);
        boolean result = field.addNeighbor(diagonalNeighbor);

        assertFalse(result);
    }

    @Test
    void testChangeMarkup() {
        assertFalse(field.isMarked());
    }

    @Test
    void testDefaultValueMarkup() {
        field.changeMarkup();
        assertTrue(field.isMarked());
    }

    @Test
    void testGoalAchieved1() {
        field.undermine();
        field.changeMarkup();

        assertTrue(field.goalAchieved());
    }

    @Test
    void testGoalAchieved2() {
        field.open();

        assertTrue(field.goalAchieved());
    }

    @Test
    void testOpeningWithNeighbors1() {
        Field field11 = new Field(1, 1);
        Field field22 = new Field(2, 2);

        field22.addNeighbor(field11);
        field.addNeighbor(field22);
        field.open();

        assertTrue(field22.isOpen() && field11.isOpen());
    }

    @Test
    void testOpeningWithNeighbors2() {
        Field field11 = new Field(1, 1);
        Field field12 = new Field(1, 2);

        field12.undermine();

        Field field22 = new Field(2, 2);
        field22.addNeighbor(field11);
        field22.addNeighbor(field12);
        field.addNeighbor(field22);
        field.open();

        assertTrue(field22.isOpen() && !field12.isOpen());
    }

    @Test
    void testMinesInTheNeighborhood() {
        Field field2 = new Field(2, 2);
        field2.undermine();
        field.addNeighbor(field2);
        assertTrue(field.minesInTheNeighborhood() > 0);
    }

}
