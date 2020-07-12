package br.com.leolimaf.minesweeper.view;

import br.com.leolimaf.minesweeper.model.EventField;
import br.com.leolimaf.minesweeper.model.Field;
import br.com.leolimaf.minesweeper.model.ObserverField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonField extends JButton implements ObserverField, MouseListener {

    private final Field FIELD;
    private final Color BG_DEFAULT = new Color(201, 201, 201);
    private final Color BG_MARK = new Color(8, 179, 247);
    private final Color BG_EXPLODE = new Color(234, 19, 20);
    private final Color GREEN_TEXT = new Color(0, 121, 0);

    public ButtonField(Field FIELD) {
        FIELD.registerObserver(this);
        this.FIELD = FIELD;
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        FIELD.registerObserver(this);
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
        SwingUtilities.invokeLater(() -> {
            repaint();
            validate();
        });
    }

    private void applyOpenStyle() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (FIELD.isUndermined()) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/mine.png"));
            Image imgScale = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(imgScale));
            return;
        }
        setBackground(BG_DEFAULT);
        switch (FIELD.minesInTheNeighborhood()) {
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
        String value = !FIELD.safeNeighborhood() ? FIELD.minesInTheNeighborhood() + "" : "";
        setText(value);
    }

    private void applyMarkStyle() {
        setBackground(BG_MARK);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/flag.png"));
        Image imgScale = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(imgScale));
    }

    private void applyExplodeStyle() {
        setBackground(BG_EXPLODE);
        setText("");
        ImageIcon icon = new ImageIcon((getClass().getResource("/images/mine.png")));
        Image imgScale = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(imgScale));
    }

    private void applyDefaultStyle() {
        setBackground(BG_DEFAULT);
        setText("");
        setIcon(null);
        setBorder(BorderFactory.createBevelBorder(0));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            FIELD.open();
        }
        if (e.getButton() == 3) {
            FIELD.changeMarkup();
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
