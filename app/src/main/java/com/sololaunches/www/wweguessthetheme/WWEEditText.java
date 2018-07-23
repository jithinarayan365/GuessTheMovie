package com.sololaunches.www.wweguessthetheme;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class WWEEditText extends AppCompatEditText {



    public WWEEditText(Context context) {
        super(context);
    }

    public WWEEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WWEEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            clearFocus();
        }
        return super.dispatchKeyEventPreIme(event);
    }
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
