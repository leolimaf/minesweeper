package br.com.leolimaf.minesweeper.view;

import br.com.leolimaf.minesweeper.model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public BoardPanel(Board board){

        setLayout(new GridLayout(board.getLines(), board.getColumns()));

        board.forEachField(buttonField -> add(new ButtonField(buttonField)));

        board.registerObserver(e -> {
            //TODO show the result to the user
        });



    }

}
