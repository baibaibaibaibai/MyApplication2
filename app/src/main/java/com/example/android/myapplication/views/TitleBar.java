package com.example.android.myapplication.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.myapplication.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class TitleBar extends LinearLayout
{
  private TextView activityTitle;
  private Context context;
  private ImageView leftBt;
  private TextView leftTv;
  private ImageView rightBt;
  private TextView rightTv;
  private RelativeLayout rl_title;
  
  public TitleBar(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    initUI();
  }
  
  public TitleBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    initUI();
  }
  
  private void initUI()
  {
    ((LayoutInflater)this.context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_titlebar, this);
    this.rl_title = ((RelativeLayout)findViewById(R.id.rl_title));
    this.leftBt = ((ImageView)findViewById(R.id.leftBt));
    this.rightBt = ((ImageView)findViewById(R.id.rightBt));
    this.leftTv = ((TextView)findViewById(R.id.leftTv));
    this.rightTv = ((TextView)findViewById(R.id.rightTv));
    this.activityTitle = ((TextView)findViewById(R.id.activityTitle));
    this.rightBt.setVisibility(GONE);
  }
  
  public void setBackground(int paramInt1, int paramInt2, int paramInt3)
  {
    this.activityTitle.setTextColor(Color.rgb(0, 0, 0));
    this.rl_title.setBackgroundColor(Color.rgb(paramInt1, paramInt2, paramInt3));
    this.leftBt.setImageResource(R.mipmap.ic_title_back_normal);
  }
  
  public void setLeftButtonVisibility(int paramInt)
  {
    this.leftBt.setVisibility(paramInt);
  }
  
  public void setLeftTvText(int paramInt)
  {
    this.leftBt.setVisibility(GONE);
    this.leftTv.setText(this.context.getString(paramInt));
  }
  
  public void setOnLeftBtClickedListener(View.OnClickListener paramOnClickListener)
  {
    this.leftBt.setOnClickListener(paramOnClickListener);
  }
  
  public void setOnRightBtClickedListener(View.OnClickListener paramOnClickListener)
  {
    this.rightBt.setOnClickListener(paramOnClickListener);
  }
  
  public void setOnRightTvClickedListener(View.OnClickListener paramOnClickListener)
  {
    this.rightTv.setOnClickListener(paramOnClickListener);
  }
  
  public void setRightButtonVisibility(int paramInt)
  {
    this.rightBt.setVisibility(paramInt);
  }
  
  public void setRightDrawable(int paramInt)
  {
    this.rightTv.setVisibility(GONE);
    this.rightBt.setVisibility(VISIBLE);
    this.rightBt.setImageResource(paramInt);
  }
  
  public void setRightTvText(int paramInt)
  {
    this.rightBt.setVisibility(GONE);
    this.rightTv.setVisibility(VISIBLE);
    this.rightTv.setText(this.context.getString(paramInt));
  }
  
  public void setTitle(int paramInt)
  {
    this.activityTitle.setText(paramInt);
  }
  
  public void setTitle(CharSequence paramCharSequence)
  {
    this.activityTitle.setText(paramCharSequence);
  }
}
