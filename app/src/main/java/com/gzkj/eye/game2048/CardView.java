package com.gzkj.eye.game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Hasee on 2017/12/1.
 */

public class CardView extends FrameLayout {

    private int num = 0;
    private TextView lable;


    public CardView(@NonNull Context context) {
        super(context);
        lable = new TextView(context);
        lable.setTextSize(35);
        lable.setGravity(Gravity.CENTER);
        lable.setBackgroundColor(0x33FFFFFF);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(10,10,0,0);
        addView(lable, layoutParams);

        setNum(0);

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            lable.setText("");
        }else{
            lable.setText(num + "");
        }
    }

    public boolean equals(CardView cardView){
        return cardView.getNum() == getNum();
    }

}
