package com.jym.chess.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jym.chess.data.InitData;
import com.jym.chess.data.Piece;

import java.util.List;

/**
 * Created by Jimmy on 2016/9/19 0019.
 */
public class PieceSeat extends LinearLayout {

    private final int mRow = 10;
    private final int mCol = 9;

    private LinearLayout[][] mSeats = new LinearLayout[mRow][mCol];
    private List<Piece> mBlackPiece;
    private List<Piece> mRedPiece;
    private PieceView mCurrentSelectedPiece;

    private PieceView.PIECE_TYPE mCurrentPlayer = PieceView.PIECE_TYPE.RED; // 0:红,1:黑

    public PieceSeat(Context context) {
        this(context, null);
    }

    public PieceSeat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieceSeat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mBlackPiece = InitData.getAllBlackPiece();
        mRedPiece = InitData.getAllRedPiece();
    }

    private void initPiece(int size) {
        for (Piece piece : mBlackPiece) {
            PieceView pieceV = new PieceView(getContext());
            pieceV.setWidth(size);
            pieceV.setHeight(size);
            pieceV.setPieceType(piece.type);
            pieceV.setTextColor(Color.BLACK);
            pieceV.setText(piece.name);
            pieceV.setRotation(180);
            pieceV.setTag(piece);
            mSeats[piece.x][piece.y].addView(pieceV);
        }
        for (Piece piece : mRedPiece) {
            PieceView pieceV = new PieceView(getContext());
            pieceV.setWidth(size);
            pieceV.setHeight(size);
            pieceV.setPieceType(piece.type);
            pieceV.setTextColor(Color.RED);
            pieceV.setText(piece.name);
            pieceV.setTag(piece);
            mSeats[piece.x][piece.y].addView(pieceV);
        }
    }

    private void initSeat(int width, int height) {
        setOrientation(VERTICAL);
        int padding = Math.abs(height - width) / 2;
        for (int i = 0; i < mRow; i++) {
            LinearLayout horizontalChildLl = new LinearLayout(getContext());
            horizontalChildLl.setOrientation(HORIZONTAL);
            horizontalChildLl.setMinimumHeight(height);
            for (int j = 0; j < mCol; j++) {
                LinearLayout verticalLl = new LinearLayout(getContext());
                verticalLl.setMinimumWidth(width);
                verticalLl.setMinimumHeight(height);
                if (height > width) {
                    verticalLl.setPadding(1, padding, 1, padding);
                } else {
                    verticalLl.setPadding(padding, 0, padding, 0);
                }
                final int finalI = i;
                final int finalJ = j;
                verticalLl.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickPosition(finalI, finalJ);
                    }
                });
                horizontalChildLl.addView(verticalLl);
                mSeats[i][j] = verticalLl;
            }
            if (i == 5) {
                LinearLayout centerLl = new LinearLayout(getContext());
                centerLl.setMinimumHeight(height / 2);
                addView(centerLl);
            }
            addView(horizontalChildLl);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int gridW = w / 10;
        int gridH = (int) (h / 11.5);
        int size = Math.min(gridW, gridH);
        setPadding(gridW / 2, gridH / 2, gridW / 2, gridH / 2);
        initSeat(gridW, gridH);
        initPiece(size - 2);
    }

    private void clickPosition(int x, int y) {
        if (mCurrentSelectedPiece == null) { // 当前未选中任何棋子
            if (mSeats[x][y].getChildCount() == 1) { // 当前点击的地方有棋子
                PieceView clickPiece = (PieceView) mSeats[x][y].getChildAt(0);
                if (mCurrentPlayer == clickPiece.getPieceType()) {
                    mCurrentSelectedPiece = clickPiece;
                    mCurrentSelectedPiece.changeState(true);
                }
            }
        } else { // 已有选中的棋子
            if (mSeats[x][y].getChildCount() == 1) { // 当前点击的地方有棋子
                PieceView targetPiece = (PieceView) mSeats[x][y].getChildAt(0);
                if (mCurrentSelectedPiece.getPieceType() == targetPiece.getPieceType()) { // 当前点击的地方跟已选择的棋子同类型
                    mCurrentSelectedPiece.changeState(false);
                    mCurrentSelectedPiece = targetPiece;
                    mCurrentSelectedPiece.changeState(true);
                } else {
                    movePiece(targetPiece, x, y); // 试图移动棋子去吃对方棋子
                }
            } else {
                movePiece(null, x, y); // 试图移动棋子
            }
        }
    }

    private void movePiece(PieceView targetPiece, int x, int y) {
        Piece piece = (Piece) mCurrentSelectedPiece.getTag();
        if (targetPiece == null) { // 移动棋子
            mSeats[piece.x][piece.y].removeAllViews();
            mSeats[x][y].addView(mCurrentSelectedPiece);
            piece.x = x;
            piece.y = y;
            mCurrentSelectedPiece.changeState(false);
            changePlayer();
        } else { // 移动棋子去吃对方棋子

        }
    }

    private void changePlayer() {
        mCurrentSelectedPiece = null;
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            mCurrentPlayer = PieceView.PIECE_TYPE.BLACK;
        } else {
            mCurrentPlayer = PieceView.PIECE_TYPE.RED;
        }
    }

}
