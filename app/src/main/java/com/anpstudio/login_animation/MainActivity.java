package com.anpstudio.login_animation;

import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

  private Button mButtonNext;
  private LinearLayout mContainerViews;

  private int mInitialHeight;
  private boolean isMeasure;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mButtonNext = (Button)findViewById(R.id.main_button_next);
    mContainerViews = (LinearLayout) findViewById(R.id.container_form);

    mButtonNext.setOnClickListener(listenerButton);


    mContainerViews.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        if (!isMeasure){
          mInitialHeight = mContainerViews.getHeight();
          isMeasure = true;
        }
      }
    });

  }


  private View.OnClickListener listenerButton = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      expandView();
    }
  };





  @Override
  public void onBackPressed() {
    //super.onBackPressed();
    collapseView();
  }




  private void expandView(){
    HeightAnimation heightAnim = new HeightAnimation(mContainerViews, mContainerViews.getHeight(), mContainerViews.getRootView().getHeight());
    heightAnim.setDuration(500);
    mContainerViews.startAnimation(heightAnim);
  }

  private void collapseView(){
    HeightAnimation heightAnim = new HeightAnimation(mContainerViews, mContainerViews.getHeight(), mInitialHeight);
    heightAnim.setDuration(500);
    mContainerViews.startAnimation(heightAnim);
  }
}
