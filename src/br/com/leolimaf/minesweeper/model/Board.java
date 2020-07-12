package br.com.leolimaf.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Board implements ObserverField {

    private final int LINES;
    private final int COLUMNS;
    private final int MINES;

    private final List<Field> FIELDS = new ArrayList<>();
    private final List<Consumer<Boolean>> OBSERVERS = new ArrayList<>();

    public Board(int LINES, int COLUMNS, int MINES) {
        this.LINES = LINES;
        this.COLUMNS = COLUMNS;
        this.MINES = MINES;

        genarateFields();
        associateNeighbors();
        drawMines();
    }

    private void genarateFields() {
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Field field = new Field(i, j);
                field.registerObserver(this);
                FIELDS.add(field);
            }
        }
    }

    private void associateNeighbors() {
        for (Field field1 : FIELDS) {
            for (Field field2 : FIELDS) {
                field1.addNeighbor(field2);
            }
        }
    }

    private void drawMines() {
        long armedMines = 0;
        do {
            int random = (int) (Math.random() * FIELDS.size());
            FIELDS.get(random).undermine();
            armedMines = FIELDS.stream().filter(Field::isUndermined).count();
        } while (armedMines < MINES);
    }

    public int getLINES() {
        return LINES;
    }

    public int getCOLUMNS() {
        return COLUMNS;
    }

    public void registerObserver(Consumer<Boolean> observer) {
        OBSERVERS.add(observer);
    }

    public void notifyObservers(boolean result) {
        OBSERVERS.forEach(o -> o.accept(result));
    }

    public void showMines() {
        FIELDS.stream()
                .filter(Field::isUndermined)
                .forEach(field -> field.setOpen(true));
    }

    public boolean goalAchieved() {
        return FIELDS.stream().allMatch(Field::goalAchieved);
    }

    public void restart() {
        FIELDS.forEach(Field::restart);
        drawMines();
    }

    @Override
    public void eventOccurred(Field field, EventField eventField) {
        if (eventField == EventField.EXPLODE) {
            showMines();
            notifyObservers(false);
            return;
        }
        if (goalAchieved()) {
            notifyObservers(true);
        }
    }

    public void forEachField(Consumer<Field> function){
        FIELDS.forEach(function);
    }

    public List<Field> getFIELDS() {
        return FIELDS;
    }
}
