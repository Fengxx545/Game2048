package com.gzkj.eye.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hasee on 2017/12/1.
 */

public class MyGridVIew extends GridLayout {

    private int rowX = 4;
    private int rowY = 4;
    private CardView[][] cardViews = new CardView[rowX][rowY];
    private List<Point> emptyPoints = new ArrayList<>();

    public MyGridVIew(Context context) {
        this(context, null);
    }

    public MyGridVIew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGridVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniGridView();
    }

    private void iniGridView() {
        //指明有多少列
        setColumnCount(rowY);
        setBackgroundColor(0xffabbbbb);

        setOnTouchListener(new OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startY;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            //水平方向
                            if (offsetX < -10) {
                                //向左
                                swipeLeft();
                            } else if (offsetX > 10) {
                                //向右
                                swipeRigth();
                            }
                        } else {
                            if (offsetY < -10) {
                                //向下
                                swipeUp();
                            } else if (offsetY > 10) {
                                //向下
                                swipeDown();
                            }
                        }
                        break;

                }
                return true;
            }
        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / rowX;

        addCard(cardWidth, cardWidth);
        startGame();
    }

    public void startGame() {
        MainActivity.getMainActivity().clearScore();
        for (int y = 0; y < rowY; y++) {
            for (int x = 0; x < rowX; x++) {
                cardViews[x][y].setNum(0);
            }
        }
        addRondomNum();
        addRondomNum();
    }


    public void checkStop() {

        boolean complate = true;

        All:
        for (int y = 0; y < rowY; y++) {
            for (int x = 0; x < rowX; x++) {
                if (cardViews[x][y].getNum() == 0 ||
                        (x > 0 && cardViews[x][y].equals(cardViews[x - 1][y])) ||
                        (x < rowX-1 && cardViews[x][y].equals(cardViews[x + 1][y])) ||
                        (y > 0 && cardViews[x][y].equals(cardViews[x][y - 1])) ||
                        (y < rowY-1 && cardViews[x][y].equals(cardViews[x][y + 1]))) {
                    complate =false;
                    break All;
                }
            }

        }
        if (complate){
            new AlertDialog.Builder(getContext())
                    .setTitle("您好")
                    .setMessage("游戏结束")
                    .setCancelable(false)
                    .setPositiveButton("重玩", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();
        }


    }


    private void addCard(int cardWidth, int cardHeight) {
        CardView card;
        for (int y = 0; y < rowY; y++) {
            for (int x = 0; x < rowX; x++) {
                card = new CardView(getContext());
                card.setNum(0);
                addView(card, cardWidth, cardHeight);
                cardViews[x][y] = card;
            }
        }

    }

    private void addRondomNum() {
        emptyPoints.clear();
        for (int y = 0; y < rowY; y++) {
            for (int x = 0; x < rowX; x++) {
                if (cardViews[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardViews[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

    }

    private void swipeLeft() {

        boolean marge = false;

        for (int y = 0; y < rowY; y++) {
            for (int x = 0; x < rowX; x++) {
                for (int x1 = x + 1; x1 < rowX; x1++) {
                    if (cardViews[x1][y].getNum() > 0) {

                        if (cardViews[x][y].getNum() <= 0) {
                            cardViews[x][y].setNum(cardViews[x1][y].getNum());
                            cardViews[x1][y].setNum(0);
                            x--;
                            marge = true;
                        } else if (cardViews[x][y].equals(cardViews[x1][y])) {
                            cardViews[x][y].setNum(cardViews[x][y].getNum() * 2);
                            cardViews[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardViews[x][y].getNum());
                            marge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (marge) {
            addRondomNum();
            checkStop();
        }

    }

    private void swipeRigth() {
        boolean marge = false;
        for (int y = 0; y < rowY; y++) {
            for (int x = rowX-1; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardViews[x1][y].getNum() > 0) {

                        if (cardViews[x][y].getNum() <= 0) {
                            cardViews[x][y].setNum(cardViews[x1][y].getNum());
                            cardViews[x1][y].setNum(0);
                            x++;
                            marge = true;
                        } else if (cardViews[x][y].equals(cardViews[x1][y])) {
                            cardViews[x][y].setNum(cardViews[x][y].getNum() * 2);
                            cardViews[x1][y].setNum(0);
                            marge = true;
                            MainActivity.getMainActivity().addScore(cardViews[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if (marge) {
            addRondomNum();
            checkStop();
        }
    }

    private void swipeUp() {
        boolean marge = false;
        for (int x = 0; x < rowX; x++) {
            for (int y = 0; y < rowY; y++) {
                for (int y1 = y + 1; y1 < rowY; y1++) {
                    if (cardViews[x][y1].getNum() > 0) {

                        if (cardViews[x][y].getNum() <= 0) {
                            cardViews[x][y].setNum(cardViews[x][y1].getNum());
                            cardViews[x][y1].setNum(0);
                            y--;
                            marge = true;
                        } else if (cardViews[x][y].equals(cardViews[x][y1])) {
                            cardViews[x][y].setNum(cardViews[x][y].getNum() * 2);
                            cardViews[x][y1].setNum(0);
                            marge = true;
                            MainActivity.getMainActivity().addScore(cardViews[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if (marge) {
            addRondomNum();
            checkStop();
        }
    }

    private void swipeDown() {
        boolean marge = false;
        for (int x = 0; x < rowX; x++) {
            for (int y = rowY-1; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardViews[x][y1].getNum() > 0) {

                        if (cardViews[x][y].getNum() <= 0) {
                            cardViews[x][y].setNum(cardViews[x][y1].getNum());
                            cardViews[x][y1].setNum(0);
                            y++;
                            marge = true;
                        } else if (cardViews[x][y].equals(cardViews[x][y1])) {
                            cardViews[x][y].setNum(cardViews[x][y].getNum() * 2);
                            cardViews[x][y1].setNum(0);
                            marge = true;
                            MainActivity.getMainActivity().addScore(cardViews[x][y].getNum());
                        }
                        break;
                    }
                }
            }
        }
        if (marge) {
            addRondomNum();
            checkStop();
        }
    }

}
