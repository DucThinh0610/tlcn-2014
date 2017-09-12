package com.tlcn.mvpapplication.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tlcn.mvpapplication.R;

@SuppressLint("AppCompatCustomView")
public class EditTextCustom extends EditText {
    public EditTextCustom(Context context) {
        super(context);
        init();
    }

    public EditTextCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        final Drawable x = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_black_24dp);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if ((event.getX() > (getWidth() - getPaddingRight() - getPaddingLeft() - x.getIntrinsicWidth()))) {
                    setText("");
                }
                return false;
            }
        });

        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (hasFocus() && getText().length() > 0) {
                    setCompoundDrawables(null, null, x, null);
                } else {
                    setCompoundDrawables(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        final Drawable x = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_black_24dp);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        if (focused) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);

            if (getText().length() > 0) {
                setCompoundDrawables(null, null, x, null);
            }

        } else {
            if (getText().length() > 0) {
                setCompoundDrawables(null, null, x, null);
            } else {
                setCompoundDrawables(null, null, null, null);
            }
            if (listener != null) {
                listener.onDismissKeyBoard(this);
            }
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onKeyPreIme(int keyCode, @NonNull KeyEvent event) {
        if (isFocusable()) {
            if ((keyCode == KeyEvent.KEYCODE_BACK
                    || keyCode == KeyEvent.ACTION_DOWN
                    || keyCode == KeyEvent.KEYCODE_ENTER)
                    && event.getAction() == KeyEvent.ACTION_UP) {
                clearFocus();
                return true;
            }

        }

        return super.onKeyPreIme(keyCode, event);
    }


    @Override
    public void onEditorAction(int actionId) {
        super.onEditorAction(actionId);
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE) {
            clearFocus();
        }
    }

    KeyboardListener listener;

    public void setOnKeyboardListener(KeyboardListener listener) {
        this.listener = listener;
    }

    public interface KeyboardListener {
        void onDismissKeyBoard(EditTextCustom keyboardEditText);
    }

}
