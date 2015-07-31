package com.example.da.widgettest.w;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.da.widgettest.R;

/**
 * Created by da on 7/21/15.
 */
public class EditWidget extends FrameLayout {
  private TextView mTitleView;
  private EditText mEditView;

  private String mHint;

  private Animation mAnimationSmaller;
  private Animation mAnimationBigger;

  public EditWidget(Context context) {
    super(context);
  }

  public EditWidget(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public EditWidget(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(final Context context, AttributeSet attrs) {
    final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditWidget);
    try {
      mHint = a.getString(R.styleable.EditWidget_android_hint);
    } finally {
      a.recycle();
    }
    LayoutInflater.from(context).inflate(R.layout.widget_edit, this);
    mTitleView = (TextView) this.findViewById(R.id.widget_edit_hint);
    mEditView = (EditText) this.findViewById(R.id.widget_edit_value);
    mTitleView.setText(mHint);
    mEditView.addTextChangedListener(mAnimationTextWatcher);

    mAnimationBigger = AnimationUtils.loadAnimation(getContext(), R.anim.scale);
//    mAnimationBigger.reset();
    mAnimationBigger.setFillAfter(true);         // animations should be applied on the finish line
    mAnimationBigger.setFillEnabled(true);

    mAnimationSmaller = AnimationUtils.loadAnimation(getContext(), R.anim.scaledown);
    mAnimationSmaller.setFillAfter(true);
    mAnimationSmaller.setFillEnabled(true);
  }

  private TextWatcher mAnimationTextWatcher = new TextWatcher() {

    int prevLength = 0;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      final int newLength = s.length();
      if (prevLength > 0 && newLength == 0) {
        animateSmaller();
      } else if (prevLength == 0 && newLength > 0) {
        animateBigger();
      }
      prevLength = newLength;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
  };

  private void animateBigger() {
    mTitleView.clearAnimation();
    mTitleView.startAnimation(mAnimationBigger);
  }

  private void animateSmaller() {
    mTitleView.clearAnimation();
    mTitleView.startAnimation(mAnimationSmaller);
  }

}
