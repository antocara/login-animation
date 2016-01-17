package com.anpstudio.login_animation;

import android.app.Service;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
  private final static int DURATION_EXPAND_COLLAPSE_ANIMATION = 500;

  @Bind(R.id.rootView)
  RelativeLayout mContainerViews;

  @Bind(R.id.container_form)
  RelativeLayout mContainerForm;

  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  @Bind(R.id.main_edit_email)
  EditText mEditButton;

  @Bind(R.id.editTop1)
  EditText mEditTop1;

  @Bind(R.id.editTop2)
  EditText mEditTop2;

  @Bind(R.id.main_button_next)
  public Button mBoton;

  private int mInitialHeight;
  private boolean isMeasure;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
    setStateInitUI();

    mContainerForm.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            if (!isMeasure) {
              mInitialHeight = mContainerForm.getHeight();
              isMeasure = true;
            }
          }
        });
  }

  @OnClick(R.id.main_button_next)
  public void onLCickEvent() {
    expandView();

    mEditTop1.setVisibility(View.VISIBLE);
    mEditTop2.setVisibility(View.VISIBLE);
    mToolbar.setVisibility(View.VISIBLE);

    animateUpEditTextButton();

    animateInEditText();
  }

  private void setStateInitUI() {
    hideInitialViews();

    setViewsOutScreen();

    RelativeLayout.LayoutParams params =
        (RelativeLayout.LayoutParams) mContainerForm.getLayoutParams();
    params.height = (int) (getResources().getDimension(R.dimen.view_height) * 2);
    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    mContainerForm.setLayoutParams(params);
  }

  private void hideInitialViews() {
    mToolbar.setVisibility(View.GONE);
    mEditTop1.setVisibility(View.GONE);
    mEditTop2.setVisibility(View.GONE);
  }

  private void setViewsOutScreen() {
    TranslateAnimation transI = new TranslateAnimation(0, -mContainerForm.getWidth() / 3, 0, 0);
    transI.setFillAfter(true);
    transI.setDuration(1000);
    mEditTop1.startAnimation(transI);
    mEditTop2.startAnimation(transI);
  }

  private void animateUpEditTextButton() {

    TranslateAnimation transB =
        new TranslateAnimation(0, 0, 0, -mContainerForm.getRootView().getHeight());

    AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);

    AnimationSet animationSet = new AnimationSet(true);
    animationSet.setFillAfter(true);
    animationSet.setDuration(700);
    animationSet.addAnimation(transB);
    animationSet.addAnimation(alphaAnimation);

    mEditButton.startAnimation(animationSet);
  }

  private void animateDownEditTextButton() {

    TranslateAnimation transB =
        new TranslateAnimation(0, 0, -mContainerForm.getRootView().getHeight(), 0);

    AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

    AnimationSet animationSet = new AnimationSet(true);
    animationSet.setFillAfter(true);
    animationSet.setDuration(700);
    animationSet.addAnimation(transB);
    animationSet.addAnimation(alphaAnimation);

    mEditButton.startAnimation(animationSet);
  }

  private void animateInEditText() {
    TranslateAnimation trans = new TranslateAnimation(-mEditTop1.getWidth() / 3, 0, 0, 0);
    trans.setFillAfter(true);
    trans.setDuration(1000);
    mEditTop1.startAnimation(trans);

    TranslateAnimation transE = new TranslateAnimation(-mEditTop2.getWidth() / 3, 0, 0, 0);
    transE.setFillAfter(true);
    transE.setDuration(1000);
    transE.setStartOffset(300);
    mEditTop2.startAnimation(transE);
  }

  @Override
  public void onBackPressed() {
    //super.onBackPressed();
    collapseView();
  }

  private void expandView() {
    //HeightAnimation heightAnim = new HeightAnimation(mContainerViews, mContainerViews.getHeight(),
    //    mContainerViews.getRootView().getHeight());
    //heightAnim.setDuration(DURATION_EXPAND_COLLAPSE_ANIMATION);
    //mContainerViews.startAnimation(heightAnim);

    RelativeLayout.LayoutParams params =
        (RelativeLayout.LayoutParams) mContainerForm.getLayoutParams();
    params.height = mContainerViews.getMeasuredHeight();
    mContainerForm.setLayoutParams(params);
    showKeyBoard();
  }

  private void collapseView() {
    //HeightAnimation heightAnim =
    //    new HeightAnimation(mContainerViews, mContainerViews.getHeight(), mInitialHeight);
    //heightAnim.setDuration(DURATION_EXPAND_COLLAPSE_ANIMATION);
    //mContainerViews.startAnimation(heightAnim);

    hideKeyboard();
    hideInitialViews();
    animateDownEditTextButton();

    RelativeLayout.LayoutParams params =
        (RelativeLayout.LayoutParams) mContainerForm.getLayoutParams();
    params.height = mInitialHeight;
    mContainerForm.setLayoutParams(params);
  }

  private void showKeyBoard() {
    InputMethodManager imm =
        (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
    imm.showSoftInput(mEditTop1, 0);
    mEditTop1.requestFocus();
  }

  private void hideKeyboard() {
    InputMethodManager imm =
        (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromInputMethod(mEditTop1.getWindowToken(), 0);
  }
}
