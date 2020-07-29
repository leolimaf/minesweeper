package br.com.leolimaf.minesweeper.view;

import br.com.leolimaf.minesweeper.model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public BoardPanel(Board board) {

        setLayout(new GridLayout(board.getLINES(), board.getCOLUMNS()));

        board.forEachField(buttonField -> add(new ButtonField(buttonField)));

        board.registerObserver(e -> SwingUtilities.invokeLater(() -> {
            if (e) {
                JOptionPane.showMessageDialog(this, "Parabéns!\nVocê venceu :)");
            } else {
                JOptionPane.showMessageDialog(this, "Que pena!\nVocê perdeu :(");
            }
            board.restart();
        }));

    }

}
