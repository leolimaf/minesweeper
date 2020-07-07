package br.com.leolimaf.minesweeper.model;

import br.com.leolimaf.minesweeper.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int line;
    private final int column;

    private boolean open;
    private boolean undermined;
    private boolean marked;

    private List<Field> neighbors = new ArrayList<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    void changeMarkup() {
        if (!open) marked = !marked;
    }

    public boolean isMarked() {
        return marked;
    }

    void undermine() {
        if (!undermined) undermined = true;
    }

    public boolean isUndermined() {
        return undermined;
    }

    void addNeighbor(Field neighbor) {
        if (neighbor == null) return;

        boolean differentLine = line != neighbor.line;
        boolean differentColumn = column != neighbor.column;
        boolean isDiagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(column - neighbor.column);
        int generalDelta = deltaLine + deltaColumn;

        if (generalDelta == 1 && !isDiagonal) {
            neighbors.add(neighbor);
        }
        if (generalDelta == 2 && isDiagonal){
            neighbors.add(neighbor);
        }
    }

    boolean open(){
        if (!open && !marked){
            open = true;

            if (undermined){
                throw new ExplosionException();
            }
            if (safeNeighborhood()){
                neighbors.forEach(Field::open);
            }
            return true;
        } else {
            return false;
        }

    }

    boolean safeNeighborhood(){
        return neighbors.stream().noneMatch(field -> field.undermined);
    }

    boolean goalAchieved(){
        boolean unveiled = open && !undermined;
        boolean protected_ = marked && undermined;

        return unveiled || protected_;
    }

    long minesInTheNeighborhood(){
        return neighbors.stream().filter(field -> field.undermined).count();
    }

    void restart(){
        open = false;
        undermined = false;
        marked = false;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
