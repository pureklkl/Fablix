package com.example.pengyuanfan.fablix.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by pengyuanfan on 5/12/2016.
 */
public class SoftKeyBoard {

    /**
     * Hides the soft keyboard
     */
    static public void hideSoftKeyboard(Activity a) {
        if(a.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    static public void showSoftKeyboard(Activity a, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }
}
