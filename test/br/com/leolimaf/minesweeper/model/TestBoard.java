package br.com.leolimaf.minesweeper.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBoard {

    private Board board;

    @BeforeEach
    void InitiateBoard() {
        board = new Board(5, 5, 5);
    }

    @Test
    void testGenarateFields() {

        boolean qntFields = board.getFields().size() == 25;

        assertTrue(qntFields);
    }

    @Test
    void testAssociateNeighbors() {

        boolean qntNeighbors1 = board.getFields().get(12).getNeighbors().size() == 8;
        boolean qntNeighbors2 = board.getFields().get(10).getNeighbors().size() == 5;

        assertTrue(qntNeighbors1 && qntNeighbors2);
    }

    @Test
    void testDrawMines() {
        int qntMines = 0;
        for (Field field : board.getFields()) {
            if (field.isUndermined()) {
                qntMines++;
            }
        }
        assertEquals(5, qntMines);
    }

    @Test
    void testGoalAchieved() {
        for (Field field : board.getFields()) {
            if (!field.isOpen() && !field.isUndermined()) {
                field.open();
            }
            if (field.isUndermined() && !field.isMarked()){
                field.changeMarkup();
            }
        }
        assertTrue(board.goalAchieved());
    }

    @Test
    void testRestart(){
        for (Field field : board.getFields()) {
            if (field.isUndermined()){
                field.changeMarkup();
            } else {
                field.open();
            }
        }
        board.restart();
        assertTrue(board.getFields().stream().noneMatch(Field::goalAchieved));
    }

    @Test
    void testShowMines(){
        board.showMines();
        boolean openMinefields = board.getFields()
                .stream()
                .filter(Field::isUndermined)
                .allMatch(Field::isOpen);
        assertTrue(openMinefields);
    }

}
