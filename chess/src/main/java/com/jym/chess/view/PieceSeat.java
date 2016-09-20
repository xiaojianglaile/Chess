package com.jym.chess.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jym.chess.data.InitData;
import com.jym.chess.data.Piece;

import java.util.List;

/**
 * Created by Jimmy on 2016/9/19 0019.
 */
public class PieceSeat extends LinearLayout {

    private final int mRow = 10;
    private final int mCol = 9;

    private LinearLayout[][] mSeats;
    private List<Piece> mBlackPiece;
    private List<Piece> mRedPiece;
    private PieceView mCurrentSelectedPiece;

    private PieceView.PIECE_TYPE mCurrentPlayer = PieceView.PIECE_TYPE.RED; // 0:红,1:黑
    private int mGridW;
    private int mGridH;
    private int mPieceSize;

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
        removeAllViews();
        mSeats = new LinearLayout[mRow][mCol];
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
        mGridW = w / 10;
        mGridH = (int) (h / 11.5);
        mPieceSize = Math.min(mGridW, mGridH) - 2;
        setPadding(mGridW / 2, mGridH / 2, mGridW / 2, mGridH / 2);
        initSeat(mGridW, mGridH);
        initPiece(mPieceSize);
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
            switch (piece.name) {
                case "卒":
                case "兵":
                    moveSoldier(piece, x, y);
                    break;
                case "炮":
                case "车":
                case "車":
                    moveGunOrCar(piece, x, y);
                    break;
                case "马":
                case "馬":
                    moveHorse(piece, x, y);
                    break;
                case "象":
                case "相":
                    moveElephant(piece, x, y);
                    break;
                case "士":
                case "仕":
                    moveScholar(piece, x, y);
                    break;
                case "帅":
                case "将":
                    moveKing(piece, x, y);
                    break;
            }
        } else { // 移动棋子去吃对方棋子
            switch (piece.name) {
                case "卒":
                case "兵":
                    moveSoldierToKillPiece(piece, targetPiece, x, y);
                    break;
                case "炮":
                    moveGunToKillPiece(piece, targetPiece, x, y);
                    break;
                case "车":
                case "車":
                    moveCarToKillPiece(piece, targetPiece, x, y);
                    break;
                case "马":
                case "馬":
                    moveHorseToKillPiece(piece, targetPiece, x, y);
                    break;
                case "象":
                case "相":
                    moveElephantToKillPiece(piece, targetPiece, x, y);
                    break;
                case "士":
                case "仕":
                    moveScholarToKillPiece(piece, targetPiece, x, y);
                    break;
                case "帅":
                case "将":
                    moveKingToKillPiece(piece, targetPiece, x, y);
                    break;
            }
        }
    }

    // 移动士兵
    private void moveSoldier(Piece piece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 6) {
                if (piece.x - x == 1) {
                    changePiecePosition(piece, x, y);
                }
            } else {
                if ((piece.x - x == 1 && piece.y - y == 0) || (Math.abs(piece.y - y) == 1 && Math.abs(piece.x - x) == 0)) {
                    changePiecePosition(piece, x, y);
                }
            }
        } else {
            if (x >= 4) {
                if ((piece.x - x == -1 && piece.y - y == 0) || (Math.abs(piece.y - y) == 1 && Math.abs(piece.x - x) == 0)) {
                    changePiecePosition(piece, x, y);
                }
            } else {
                if (piece.x - x == -1) {
                    changePiecePosition(piece, x, y);
                }
            }
        }
    }

    // 移动士兵去杀人
    private void moveSoldierToKillPiece(Piece piece, PieceView targetPiece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 6) {
                if (piece.x - x == 1) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            } else {
                if ((piece.x - x == 1 && piece.y - y == 0) || (Math.abs(piece.y - y) == 1 && Math.abs(piece.x - x) == 0)) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            }
        } else {
            if (x >= 4) {
                if ((piece.x - x == -1 && piece.y - y == 0) || (Math.abs(piece.y - y) == 1 && Math.abs(piece.x - x) == 0)) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            } else {
                if (piece.x - x == -1) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            }
        }
    }

    // 移动炮或车
    private void moveGunOrCar(Piece piece, int x, int y) {
        if (piece.x == x && piece.y != y) { // 横线移动
            int max = Math.max(piece.y, y);
            int min = Math.min(piece.y, y);
            if (getHorizontalHavePieceNum(x, min, max) == 0) {
                changePiecePosition(piece, x, y);
            }
        }
        if (piece.x != x && piece.y == y) { // 竖向移动
            int max = Math.max(piece.x, x);
            int min = Math.min(piece.x, x);
            if (getVerticalHavePieceNum(y, min, max) == 0) {
                changePiecePosition(piece, x, y);
            }
        }
    }

    // 移动炮去杀人
    private void moveGunToKillPiece(Piece piece, PieceView targetPiece, int x, int y) {
        if (piece.x == x && piece.y != y) { // 横线移动
            int max = Math.max(piece.y, y);
            int min = Math.min(piece.y, y);
            if (getHorizontalHavePieceNum(x, min, max) == 1) {
                changePiecePositionAndKillPiece(piece, targetPiece, x, y);
            }
        }
        if (piece.x != x && piece.y == y) { // 竖向移动
            int max = Math.max(piece.x, x);
            int min = Math.min(piece.x, x);
            if (getVerticalHavePieceNum(y, min, max) == 1) {
                changePiecePositionAndKillPiece(piece, targetPiece, x, y);
            }
        }
    }

    // 移动车去杀人
    private void moveCarToKillPiece(Piece piece, PieceView targetPiece, int x, int y) {
        if (piece.x == x && piece.y != y) { // 横线移动
            int max = Math.max(piece.y, y);
            int min = Math.min(piece.y, y);
            if (getHorizontalHavePieceNum(x, min, max) == 0) {
                changePiecePositionAndKillPiece(piece, targetPiece, x, y);
            }
        }
        if (piece.x != x && piece.y == y) { // 竖向移动
            int max = Math.max(piece.x, x);
            int min = Math.min(piece.x, x);
            if (getVerticalHavePieceNum(y, min, max) == 0) {
                changePiecePositionAndKillPiece(piece, targetPiece, x, y);
            }
        }
    }

    // 移动马
    private void moveHorse(Piece piece, int x, int y) {
        if (piece.x - x == 2 && Math.abs(piece.y - y) == 1) { // 竖直左上走或右上走
            if (mSeats[piece.x - 1][piece.y].getChildCount() == 0) { // 有无棋子阻拦
                changePiecePosition(piece, x, y);
            }
        }
        if (piece.x - x == -2 && Math.abs(piece.y - y) == 1) { // 竖直左下走或右下走
            if (mSeats[piece.x + 1][piece.y].getChildCount() == 0) {
                changePiecePosition(piece, x, y);
            }
        }
        if (Math.abs(piece.x - x) == 1 && piece.y - y == 2) { // 横向左上走或左下走
            if (mSeats[piece.x][piece.y - 1].getChildCount() == 0) {
                changePiecePosition(piece, x, y);
            }
        }
        if (Math.abs(piece.x - x) == 1 && piece.y - y == -2) { // 横向右上走或右下走
            if (mSeats[piece.x][piece.y + 1].getChildCount() == 0) {
                changePiecePosition(piece, x, y);
            }
        }
    }

    // 移动马去杀人
    private void moveHorseToKillPiece(Piece piece, PieceView target, int x, int y) {
        if (piece.x - x == 2 && Math.abs(piece.y - y) == 1) { // 竖直左上走或右上走
            if (mSeats[piece.x - 1][piece.y].getChildCount() == 0) { // 有无棋子阻拦
                changePiecePositionAndKillPiece(piece, target, x, y);
            }
        }
        if (piece.x - x == -2 && Math.abs(piece.y - y) == 1) { // 竖直左下走或右下走
            if (mSeats[piece.x + 1][piece.y].getChildCount() == 0) {
                changePiecePositionAndKillPiece(piece, target, x, y);
            }
        }
        if (Math.abs(piece.x - x) == 1 && piece.y - y == 2) { // 横向左上走或左下走
            if (mSeats[piece.x][piece.y - 1].getChildCount() == 0) {
                changePiecePositionAndKillPiece(piece, target, x, y);
            }
        }
        if (Math.abs(piece.x - x) == 1 && piece.y - y == -2) { // 横向右上走或右下走
            if (mSeats[piece.x][piece.y + 1].getChildCount() == 0) {
                changePiecePositionAndKillPiece(piece, target, x, y);
            }
        }
    }

    // 移动象
    private void moveElephant(Piece piece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 5) {
                if (Math.abs(piece.x - x) == 2 && Math.abs(piece.y - y) == 2) { // 象22行走
                    if (mSeats[(piece.x + x) / 2][(piece.y + y) / 2].getChildCount() == 0) { // 象路上无棋子阻拦
                        changePiecePosition(piece, x, y);
                    }
                }
            }
        } else {
            if (x <= 4) {
                if (Math.abs(piece.x - x) == 2 && Math.abs(piece.y - y) == 2) {
                    if (mSeats[(piece.x + x) / 2][(piece.y + y) / 2].getChildCount() == 0) {
                        changePiecePosition(piece, x, y);
                    }
                }
            }
        }
    }

    // 移动象去杀人
    private void moveElephantToKillPiece(Piece piece, PieceView targetPiece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 5) {
                if (Math.abs(piece.x - x) == 2 && Math.abs(piece.y - y) == 2) { // 象22行走
                    if (mSeats[(piece.x + x) / 2][(piece.y + y) / 2].getChildCount() == 0) { // 象路上无棋子阻拦
                        changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                    }
                }
            }
        } else {
            if (x <= 4) {
                if (Math.abs(piece.x - x) == 2 && Math.abs(piece.y - y) == 2) {
                    if (mSeats[(piece.x + x) / 2][(piece.y + y) / 2].getChildCount() == 0) {
                        changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                    }
                }
            }
        }
    }

    // 移动士
    private void moveScholar(Piece piece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 7 && y >= 3 && y <= 5) { // 判断是否在移动范围内
                if (Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 1) {
                    changePiecePosition(piece, x, y);
                }
            }
        } else {
            if (x <= 2 && y >= 3 && y <= 5) {
                if (Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 1) {
                    changePiecePosition(piece, x, y);
                }
            }
        }
    }

    // 移动士去杀人
    private void moveScholarToKillPiece(Piece piece, PieceView targetPiece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 7 && y >= 3 && y <= 5) { // 判断是否在移动范围内
                if (Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 1) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            }
        } else {
            if (x <= 2 && y >= 3 && y <= 5) {
                if (Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 1) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            }
        }
    }

    // 移动将军
    private void moveKing(Piece piece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 7 && y >= 3 && y <= 5) { // 判断是否在移动范围内
                if ((Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 0) || (Math.abs(piece.x - x) == 0 && Math.abs(piece.y - y) == 1)) {
                    changePiecePosition(piece, x, y);
                }
            }
        } else {
            if (x <= 2 && y >= 3 && y <= 5) {
                if ((Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 0) || (Math.abs(piece.x - x) == 0 && Math.abs(piece.y - y) == 1)) {
                    changePiecePosition(piece, x, y);
                }
            }
        }
    }

    // 移动将军去杀人
    private void moveKingToKillPiece(Piece piece, PieceView targetPiece, int x, int y) {
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            if (x >= 7 && y >= 3 && y <= 5) { // 判断是否在移动范围内
                if ((Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 0) || (Math.abs(piece.x - x) == 0 && Math.abs(piece.y - y) == 1)) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            } else {
                Piece targetPieceTag = (Piece) targetPiece.getTag();
                if (targetPieceTag.name.equals("将")) {
                    if (getVerticalHavePieceNum(y, x, piece.x) == 0) {
                        changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                    }
                }
            }
        } else {
            if (x <= 2 && y >= 3 && y <= 5) {
                if ((Math.abs(piece.x - x) == 1 && Math.abs(piece.y - y) == 0) || (Math.abs(piece.x - x) == 0 && Math.abs(piece.y - y) == 1)) {
                    changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                }
            } else {
                Piece targetPieceTag = (Piece) targetPiece.getTag();
                if (targetPieceTag.name.equals("帅")) {
                    if (getVerticalHavePieceNum(y, piece.x, x) == 0) {
                        changePiecePositionAndKillPiece(piece, targetPiece, x, y);
                    }
                }
            }
        }
    }

    // 检查横向有无棋子
    private int getHorizontalHavePieceNum(int x, int min, int max) {
        int count = 0;
        for (int i = min + 1; i < max; i++) {
            if (mSeats[x][i].getChildCount() == 1) {
                count++;
            }
        }
        return count;
    }

    // 检查纵向有无棋子
    private int getVerticalHavePieceNum(int y, int min, int max) {
        int count = 0;
        for (int i = min + 1; i < max; i++) {
            if (mSeats[i][y].getChildCount() == 1) {
                count++;
            }
        }
        return count;
    }

    // 移动棋子
    private void changePiecePosition(Piece piece, int x, int y) {
        mSeats[piece.x][piece.y].removeAllViews();
        mSeats[x][y].addView(mCurrentSelectedPiece);
        piece.x = x;
        piece.y = y;
        mCurrentSelectedPiece.changeState(false);
        changePlayer();
    }

    // 移动棋子并杀死原有棋子
    private void changePiecePositionAndKillPiece(Piece piece, PieceView targetPiece, int x, int y) {
        mSeats[x][y].removeAllViews();
        Piece targetPieceTag = (Piece) targetPiece.getTag();
        if (targetPieceTag.type == PieceView.PIECE_TYPE.RED) {
            mRedPiece.remove(targetPieceTag);
        } else {
            mBlackPiece.remove(targetPieceTag);
        }
        changePiecePosition(piece, x, y);
        if (targetPieceTag.name.equals("将")) {
            Toast.makeText(getContext(), "红方获胜", Toast.LENGTH_SHORT).show();
            restartGame();
        }
        if (targetPieceTag.name.equals("帅")) {
            Toast.makeText(getContext(), "黑方获胜", Toast.LENGTH_SHORT).show();
            restartGame();
        }
    }

    // 更换玩家
    private void changePlayer() {
        mCurrentSelectedPiece = null;
        if (mCurrentPlayer == PieceView.PIECE_TYPE.RED) {
            mCurrentPlayer = PieceView.PIECE_TYPE.BLACK;
        } else {
            mCurrentPlayer = PieceView.PIECE_TYPE.RED;
        }
    }

    private void restartGame() {
        initData();
        initSeat(mGridW, mGridH);
        initPiece(mPieceSize);
    }

}
