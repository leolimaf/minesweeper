package br.com.leolimaf.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int lines;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        gerateFields();
        associateNeighbors();
        drawMines();
    }

    private void gerateFields() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                fields.add(new Field(i, j));
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
            fields.get(random);
            armedMines = fields.stream().filter(Field::isUndermined).count();
        } while (armedMines < mines);
    }

    public boolean goalAchieved(){
        return fields.stream().allMatch(Field::goalAchieved);
    }

    public void restart(){
        fields.forEach(Field::restart);
    }


}
