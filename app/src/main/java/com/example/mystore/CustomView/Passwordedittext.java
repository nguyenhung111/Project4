package com.example.mystore.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.mystore.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("AppCompatCustomView")
public class Passwordedittext extends EditText {
    Drawable eye,eyeStrike;
    Boolean visible = false;
    Boolean useStrike = false;
    Boolean useVaile = false;
    Drawable drawable;
    int Alpha = (int) (255 * .55f);
    String kytu = "((?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{6,20})";
    Pattern pattern;
    Matcher matcher;
    public Passwordedittext(Context context) {
        super(context);
        khoitao(null);
    }

    public Passwordedittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao(null);
    }

    public Passwordedittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Passwordedittext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao(null);
    }
    private void khoitao(AttributeSet attrs){
        this.pattern = Pattern.compile(kytu);
        if(attrs != null){
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs,R.styleable.Passwordedittext,0,0);
            this.useStrike = array.getBoolean(R.styleable.Passwordedittext_useStrike,false);
            this.useVaile = array.getBoolean(R.styleable.Passwordedittext_useVaile, false);
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black_24dp).mutate();
        eyeStrike = ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_off_black_24dp).mutate();
        if(this.useVaile){
            setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus){
                        String chuoi = getText().toString();
                        TextInputLayout textInputLayout = (TextInputLayout) v.getParent();
                        matcher = pattern.matcher(chuoi);
                        if(!matcher.matches()) {
                            textInputLayout.setErrorEnabled(true);
                            textInputLayout.setError("Mật khẩu phải bao gồm 6 ký tự");
                        }else {
                            textInputLayout.setErrorEnabled(false);
                            textInputLayout.setError("");
                        }
                    }
                }
            });
        }
        Caidat();
    }
    private void Caidat(){
        setInputType(InputType.TYPE_CLASS_TEXT | (visible? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();
        drawable = useStrike && !visible? eyeStrike : eye;
        drawable.setAlpha(Alpha);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_UP && event.getX() >= (getRight() - drawable.getBounds().width())){
            visible = !visible;
            Caidat();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
