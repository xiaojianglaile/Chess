package com.jym.chess.data;

import com.jym.chess.view.PieceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 2016/9/19 0019.
 */
public class InitData {

    public static List<Piece> getAllBlackPiece() {
        Piece leftCar = new Piece(0, 0, PieceView.PIECE_TYPE.BLACK, "車");
        Piece rightCar = new Piece(0, 8, PieceView.PIECE_TYPE.BLACK, "車");
        Piece leftHorse = new Piece(0, 1, PieceView.PIECE_TYPE.BLACK, "馬");
        Piece rightHorse = new Piece(0, 7, PieceView.PIECE_TYPE.BLACK, "馬");
        Piece leftElephant = new Piece(0, 2, PieceView.PIECE_TYPE.BLACK, "象");
        Piece rightElephant = new Piece(0, 6, PieceView.PIECE_TYPE.BLACK, "象");
        Piece leftScholar = new Piece(0, 3, PieceView.PIECE_TYPE.BLACK, "士");
        Piece rightScholar = new Piece(0, 5, PieceView.PIECE_TYPE.BLACK, "士");
        Piece king = new Piece(0, 4, PieceView.PIECE_TYPE.BLACK, "将");
        Piece leftGun = new Piece(2, 1, PieceView.PIECE_TYPE.BLACK, "炮");
        Piece rightGun = new Piece(2, 7, PieceView.PIECE_TYPE.BLACK, "炮");
        Piece soldier1 = new Piece(3, 0, PieceView.PIECE_TYPE.BLACK, "卒");
        Piece soldier2 = new Piece(3, 2, PieceView.PIECE_TYPE.BLACK, "卒");
        Piece soldier3 = new Piece(3, 4, PieceView.PIECE_TYPE.BLACK, "卒");
        Piece soldier4 = new Piece(3, 6, PieceView.PIECE_TYPE.BLACK, "卒");
        Piece soldier5 = new Piece(3, 8, PieceView.PIECE_TYPE.BLACK, "卒");
        List<Piece> blackPiece = new ArrayList<>();
        blackPiece.add(leftCar);
        blackPiece.add(rightCar);
        blackPiece.add(leftHorse);
        blackPiece.add(rightHorse);
        blackPiece.add(leftElephant);
        blackPiece.add(rightElephant);
        blackPiece.add(leftScholar);
        blackPiece.add(rightScholar);
        blackPiece.add(king);
        blackPiece.add(leftGun);
        blackPiece.add(rightGun);
        blackPiece.add(soldier1);
        blackPiece.add(soldier2);
        blackPiece.add(soldier3);
        blackPiece.add(soldier4);
        blackPiece.add(soldier5);
        return blackPiece;
    }

    public static List<Piece> getAllRedPiece() {
        Piece leftCar = new Piece(9, 0, PieceView.PIECE_TYPE.RED, "车");
        Piece rightCar = new Piece(9, 8, PieceView.PIECE_TYPE.RED, "车");
        Piece leftHorse = new Piece(9, 1, PieceView.PIECE_TYPE.RED, "马");
        Piece rightHorse = new Piece(9, 7, PieceView.PIECE_TYPE.RED, "马");
        Piece leftElephant = new Piece(9, 2, PieceView.PIECE_TYPE.RED, "相");
        Piece rightElephant = new Piece(9, 6, PieceView.PIECE_TYPE.RED, "相");
        Piece leftScholar = new Piece(9, 3, PieceView.PIECE_TYPE.RED, "仕");
        Piece rightScholar = new Piece(9, 5, PieceView.PIECE_TYPE.RED, "仕");
        Piece king = new Piece(9, 4, PieceView.PIECE_TYPE.RED, "帅");
        Piece leftGun = new Piece(7, 1, PieceView.PIECE_TYPE.RED, "炮");
        Piece rightGun = new Piece(7, 7, PieceView.PIECE_TYPE.RED, "炮");
        Piece soldier1 = new Piece(6, 0, PieceView.PIECE_TYPE.RED, "兵");
        Piece soldier2 = new Piece(6, 2, PieceView.PIECE_TYPE.RED, "兵");
        Piece soldier3 = new Piece(6, 4, PieceView.PIECE_TYPE.RED, "兵");
        Piece soldier4 = new Piece(6, 6, PieceView.PIECE_TYPE.RED, "兵");
        Piece soldier5 = new Piece(6, 8, PieceView.PIECE_TYPE.RED, "兵");
        List<Piece> blackPiece = new ArrayList<>();
        blackPiece.add(leftCar);
        blackPiece.add(rightCar);
        blackPiece.add(leftHorse);
        blackPiece.add(rightHorse);
        blackPiece.add(leftElephant);
        blackPiece.add(rightElephant);
        blackPiece.add(leftScholar);
        blackPiece.add(rightScholar);
        blackPiece.add(king);
        blackPiece.add(leftGun);
        blackPiece.add(rightGun);
        blackPiece.add(soldier1);
        blackPiece.add(soldier2);
        blackPiece.add(soldier3);
        blackPiece.add(soldier4);
        blackPiece.add(soldier5);
        return blackPiece;
    }

}
