package br.com.leolimaf.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int LINE;
    private final int COLUMN;

    private boolean open;
    private boolean undermined;
    private boolean marked;

    private final List<Field> neighbors = new ArrayList<>();
    private final List<ObserverField> observers = new ArrayList<>();

    public Field(int LINE, int COLUMN) {
        this.LINE = LINE;
        this.COLUMN = COLUMN;
    }

    public void registerObserver(ObserverField observer){
        observers.add(observer);
    }

    private void notifyObservers(EventField event){
        observers.forEach(observerField -> observerField.eventOccurred(this, event));
    }

    public void changeMarkup() {
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

    boolean addNeighbor(Field neighbor) {
        if (neighbor == null) return false;

        boolean differentLine = LINE != neighbor.LINE;
        boolean differentColumn = COLUMN != neighbor.COLUMN;
        boolean isDiagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(LINE - neighbor.LINE);
        int deltaColumn = Math.abs(COLUMN - neighbor.COLUMN);
        int generalDelta = deltaLine + deltaColumn;

        if (generalDelta == 1) {
            neighbors.add(neighbor);
            return true;
        }
        if (generalDelta == 2 && isDiagonal) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }

    public void open() {
        if (!open && !marked) {

            if (undermined) {
                notifyObservers(EventField.EXPLODE);
                return;
            }
            setOpen(true);
            if (safeNeighborhood()) {
                neighbors.forEach(Field::open);
            }
        }

    }

    public boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(Field::isUndermined);
    }

    boolean goalAchieved() {
        boolean unveiled = open && !undermined;
        boolean protected_ = marked && undermined;

        return unveiled || protected_;
    }

    public int minesInTheNeighborhood() {
        return (int) neighbors.stream().filter(Field::isUndermined).count();
    }

    void restart() {
        open = false;
        undermined = false;
        marked = false;
        notifyObservers(EventField.RESTART);
    }

    public void setOpen(boolean open) {
        this.open = open;
        if (open){
            notifyObservers(EventField.OPEN);
        }
    }

    public boolean isOpen() {
        return open;
    }

    public List<Field> getNeighbors() {
        return neighbors;
    }
}
