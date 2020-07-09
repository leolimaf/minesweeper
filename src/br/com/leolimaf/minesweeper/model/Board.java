package br.com.leolimaf.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Board implements ObserverField {

    private final int lines;
    private final int columns;
    private final int mines;

    private final List<Field> fields = new ArrayList<>();
    private final List<Consumer<Boolean>> observers = new ArrayList<>();

    public Board(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        genarateFields();
        associateNeighbors();
        drawMines();
    }

    private void genarateFields() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                Field field = new Field(i, j);
                field.registerObserver(this);
                fields.add(field);
            }
        }
    }

    private void associateNeighbors() {
        for (Field field1 : fields) {
            for (Field field2 : fields) {
                field1.addNeighbor(field2);
            }
        }
    }

    private void drawMines() {
        long armedMines = 0;
        do {
            int random = (int) (Math.random() * fields.size());
            fields.get(random).undermine();
            armedMines = fields.stream().filter(Field::isUndermined).count();
        } while (armedMines < mines);
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public void registerObserver(Consumer<Boolean> observer) {
        observers.add(observer);
    }

    private void notifyObservers(boolean result) {
        observers.forEach(o -> o.accept(result));
    }

    public void open(int line, int column) {
        fields.parallelStream()
                .filter(field -> field.getLine() == line && field.getColumn() == column)
                .findFirst()
                .ifPresent(Field::open);
    }

    private void showMines() {
        fields.stream()
                .filter(Field::isUndermined)
                .forEach(field -> field.setOpen(true));
    }

    public void changeMarkup(int line, int column) {
        fields.parallelStream()
                .filter(field -> field.getLine() == line && field.getColumn() == column)
                .findFirst()
                .ifPresent(Field::changeMarkup);
    }

    public boolean goalAchieved() {
        return fields.stream().allMatch(Field::goalAchieved);
    }

    public void restart() {
        fields.forEach(Field::restart);
    }

    @Override
    public void eventOccurred(Field field, EventField eventField) {
        if (eventField == EventField.EXPLODE) {
            System.out.println("you lose");
            notifyObservers(false);
        } else if (goalAchieved()) {
            System.out.println("you win");
            notifyObservers(true);
        }
    }

    public void forEachField(Consumer<Field> function){
        fields.forEach(function);
    }
}
