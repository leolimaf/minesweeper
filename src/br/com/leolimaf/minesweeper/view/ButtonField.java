package br.com.leolimaf.minesweeper.view;

import br.com.leolimaf.minesweeper.model.EventField;
import br.com.leolimaf.minesweeper.model.Field;
import br.com.leolimaf.minesweeper.model.ObserverField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonField extends JButton implements ObserverField, MouseListener {

    private Field field;
    private final Color BG_DEFAULT = new Color(201, 201, 201);
    private final Color BG_MARK = new Color(8, 179,247);
    private final Color BG_EXPLODE = new Color(189, 66,68);
    private final Color GREEN_TEXT = new Color(0, 100,0);

    public ButtonField(Field field) {
        field.registerObserver(this);
        this.field = field;
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        field.registerObserver(this);
    }

    @Override
    public void eventOccurred(Field field, EventField eventField) {
        switch (eventField) {
            case OPEN:
                applyOpenStyle();
                break;
            case MARK:
                applyMarkStyle();
                break;
            case EXPLODE:
                applyExplodeStyle();
                break;
            default:
                applyDefaultStyle();
        }
    }

    private void applyOpenStyle() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        switch (field.minesInTheNeighborhood()){
            case 1:
                setForeground(GREEN_TEXT);
                break;
            case 2:
            case 3:
                setForeground(Color.BLUE);
                break;
            default:
                setForeground(Color.RED);
        }
        setFont(new Font("Arial", Font.PLAIN, 18));
        String value = !field.safeNeighborhood() ? field.minesInTheNeighborhood() + "" : "";
        setText(value);
    }

    private void applyMarkStyle() {

    }

    private void applyExplodeStyle() {

    }

    private void applyDefaultStyle() {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1){
            field.open();
        }
        if (e.getButton() == 3){
            field.changeMarkup();
        }
    }

    public void mouseClicked(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {
    }
}
