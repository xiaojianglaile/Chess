package com.jym.chess.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.jym.chess.R;

/**
 * Created by Jimmy on 2016/9/18 0018.
 */
public class PieceView extends TextView {

    private PIECE_TYPE mType = PIECE_TYPE.RED;

    public PieceView(Context context) {
        this(context, null);
    }

    public PieceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PieceView);
        mType = array.getInteger(R.styleable.PieceView_type, 0) == 0 ? PIECE_TYPE.RED : PIECE_TYPE.BLACK;
    }

    private void initView() {
        setPieceTypeWhenNormal();
        setGravity(Gravity.CENTER);
    }

    public void setPieceType(PIECE_TYPE type) {
        mType = type;
        setPieceTypeWhenNormal();
    }

    public PIECE_TYPE getPieceType() {
        return mType;
    }

    private void setPieceTypeWhenNormal() {
        if (mType == PIECE_TYPE.RED) {
            setBackgroundResource(R.drawable.bg_red_piece_normal);
            setTextColor(Color.RED);
        } else {
            setBackgroundResource(R.drawable.bg_black_piece_normal);
            setTextColor(Color.BLACK);
        }
    }

    private void setPieceTypeWhenSelected() {
        if (mType == PIECE_TYPE.RED) {
            setBackgroundResource(R.drawable.bg_red_piece_selected);
            setTextColor(Color.parseColor("#008AD4"));
        } else {
            setBackgroundResource(R.drawable.bg_black_piece_selected);
            setTextColor(Color.parseColor("#9AEB63"));
        }
    }

    public void changeState(boolean isSelected) {
        if (isSelected) {
            setPieceTypeWhenSelected();
        } else {
            setPieceTypeWhenNormal();
        }
    }

    public enum PIECE_TYPE {
        RED,
        BLACK
    }

}
