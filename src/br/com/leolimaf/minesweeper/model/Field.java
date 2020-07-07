package br.com.leolimaf.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int line;
    private final int column;

    private boolean open;
    private boolean undermined;
    private boolean marked;

    private List<Field> neighbors = new ArrayList<>();
    private List<ObserverField> observers = new ArrayList<>();

    public Field(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void registerObserver(ObserverField observer){
        observers.add(observer);
    }

    private void notifyObservers(EventField event){
        observers.forEach(observerField -> observerField.eventOccurred(this, event));
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    void changeMarkup() {
        if (!open){
            marked = !marked;
            if (marked){
                notifyObservers(EventField.MARK);
            } else {
                notifyObservers(EventField.MARKOFF);
            }
        }
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
        if (generalDelta == 2 && isDiagonal) {
            neighbors.add(neighbor);
        }
    }

    boolean open() {
        if (!open && !marked) {

            if (undermined) {
                notifyObservers(EventField.EXPLODE);
                return true;
            }
            setOpen(true);
            if (safeNeighborhood()) {
                neighbors.forEach(Field::open);
            }
            return true;
        } else {
            return false;
        }

    }

    boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(field -> field.undermined);
    }

    boolean goalAchieved() {
        boolean unveiled = open && !undermined;
        boolean protected_ = marked && undermined;

        return unveiled || protected_;
    }

    long minesInTheNeighborhood() {
        return neighbors.stream().filter(field -> field.undermined).count();
    }

    void restart() {
        open = false;
        undermined = false;
        marked = false;
    }

    public void setOpen(boolean open) {
        this.open = open;
        if (open){
            notifyObservers(EventField.OPEN);
        }
    }
}
