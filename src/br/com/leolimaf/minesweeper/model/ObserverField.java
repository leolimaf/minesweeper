package br.com.leolimaf.minesweeper.model;

@FunctionalInterface
public interface ObserverField {

    void eventOccurred(Field field, EventField eventField);

}
