package com.example.pintu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GamePintuLayout extends RelativeLayout implements View.OnClickListener{
    //设置Item的数量n*n；默认为3
    public int mColumn = 3;
    //布局的宽度
    private int mWidth;
    //布局的padding
    private int mPadding;
    //存放所有的Item
    private ImageView[] mGamePintuItems;
    //Item的宽度
    private int mItemWidth;
    //Item横向与纵向的边距
    private int mMargin = 3;
    //拼图的图片
    private Bitmap mBitmap;
    //存放切完以后的图片bean
    private List<ImagePiece> mItemBitmaps;
    //初始化界面的标记
    private boolean once;

    public GamePintuLayout(Context context) {
        this(context, null);
    }
    public GamePintuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    //构造函数，用来初始化
    public GamePintuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //把设置的margin值转换为dp
        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                mMargin, getResources().getDisplayMetrics());
        // 设置Layout的内边距，四边一致，设置为四内边距中的最小值
        mPadding = min(getPaddingLeft(), getPaddingTop(), getPaddingRight(),
                getPaddingBottom());
    }
    //计时器
    Chronometer chron;
    public void timecount(){
        chron.setFormat("%s");
        chron.setBase(SystemClock.elapsedRealtime());
        chron.start();
    }
    //改变难度
    public void level() {
        this.removeAllViews();

        initBitmap();
        initItem();
    }
    //改变图片
    public void changePicture1() {
        this.removeAllViews();

        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.aa);
        initBitmap();
        initItem();
    }
    public void changePicture2() {
        this.removeAllViews();

        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.bb);
        initBitmap();
        initItem();
    }
    public void changePicture3() {
        this.removeAllViews();

        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.cc);
        initBitmap();
        initItem();
    }
    //用来设置设置自定义的View的宽高，
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得游戏布局的边长
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (!once) {
            initBitmap();
            initItem();
        }
        once = true;
        setMeasuredDimension(mWidth, mWidth);
    }
    //初始化bitmap
    private void initBitmap() {
        if (mBitmap == null)
            mBitmap = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.aa);

        mItemBitmaps = ImageSplitter.split(mBitmap, mColumn);
        //对图片进行排序
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece lhs, ImagePiece rhs) {
                //用random随机比较大小
                return Math.random() > 0.5 ? 1 : -1;
            }
        });
    }
    //初始化每一个item
    private void initItem() {
        // 获得Item的宽度
        int childWidth = (mWidth - mPadding * 2 - mMargin * (mColumn - 1))
                / mColumn;
        mItemWidth = childWidth;

        mGamePintuItems = new ImageView[mColumn * mColumn];
        // 放置Item
        for (int i = 0; i < mGamePintuItems.length; i++) {
            ImageView item = new ImageView(getContext());

            item.setOnClickListener(this);

            item.setImageBitmap(mItemBitmaps.get(i).bitmap);
            mGamePintuItems[i] = item;
            item.setId(i + 1);
            item.setTag(i + "_" + mItemBitmaps.get(i).index);

            RelativeLayout.LayoutParams lp = new LayoutParams(mItemWidth,
                    mItemWidth);
            // 设置横向边距,不是最后一列
            if ((i + 1) % mColumn != 0) {
                lp.rightMargin = mMargin;
            }
            // 如果不是第一列
            if (i % mColumn != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF,//
                        mGamePintuItems[i - 1].getId());
            }
            // 如果不是第一行，//设置纵向边距，非最后一行
            if ((i + 1) > mColumn) {
                lp.topMargin = mMargin;
                lp.addRule(RelativeLayout.BELOW,//
                        mGamePintuItems[i - mColumn].getId());
            }
            addView(item, lp);
        }
    }
    //用来得到最小值
    private int min(int ... params) {
        int minValue = params[0];
        for(int param : params){
            if(minValue>param){
                minValue=param;
            }
        }
        return minValue;
    }
    //记录第一次点击的ImageView
    private ImageView mFirst;
    //记录第二次点击的ImageView
    private ImageView mSecond;
    //点击事件
    @Override
    public void onClick(View view) {
        Log.d("TAG", "onClick: "+view.getTag());
        // 如果正在执行动画，则屏蔽

        //如果两次点击的是同一个View
        if(mFirst == view){
            mFirst.setColorFilter(null);
            mFirst = null;
            return;
        }
        //点击第一个View
        if(mFirst==null){
            mFirst= (ImageView) view;
            mFirst.setColorFilter(Color.parseColor("#55FF0000"));
        }else{//点击第二个View
            mSecond= (ImageView) view;
            exchangView();
        }
    }

    //交换两个Item图片
    private void exchangView(){
        mFirst.setColorFilter(null);
        String firstTag = (String) mFirst.getTag();
        String secondTag = (String) mSecond.getTag();

        //得到在list中索引位置
        String[] firstImageIndex = firstTag.split("_");
        String[] secondImageIndex = secondTag.split("_");

        mFirst.setImageBitmap(mItemBitmaps.get(Integer
                .parseInt(secondImageIndex[0])).bitmap);
        mSecond.setImageBitmap(mItemBitmaps.get(Integer
                .parseInt(firstImageIndex[0])).bitmap);

        mFirst.setTag(secondTag);
        mSecond.setTag(firstTag);

        mFirst = mSecond = null;
        checkSuccess();
    }

    //用来判断游戏是否成功

    private void checkSuccess() {
        boolean isSuccess = true;
        for (int i = 0; i < mGamePintuItems.length; i++) {
            ImageView first = mGamePintuItems[i];
            Log.e("TAG", getIndexByTag((String) first.getTag()) + "");
            if (getIndexByTag((String) first.getTag()) != i) {
                isSuccess = false;
            }
        }
        if (isSuccess) {
            chron.stop();
            Toast.makeText(getContext(), "游戏成功!"+"用时"+chron.getText().toString(),
                    Toast.LENGTH_LONG).show();
            nextLevel();
        }
    }

    //进入下一关

    private void nextLevel() {
        this.removeAllViews();

        initBitmap();
        initItem();
        chron.setBase(SystemClock.elapsedRealtime());
    }



    //获得图片的真正索引

    private int getIndexByTag(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }
}

