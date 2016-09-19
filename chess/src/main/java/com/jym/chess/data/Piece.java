package com.jym.chess.data;

import com.jym.chess.view.PieceView;

/**
 * Created by Jimmy on 2016/9/19 0019.
 */
public class Piece {

    public int x;
    public int y;
    public PieceView.PIECE_TYPE type;
    public String name;

    public Piece(int x, int y, PieceView.PIECE_TYPE type, String name) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.name = name;
    }
}
