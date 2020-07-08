package com.jywl.yezai.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签
 */
public class FlowTagView extends View {

    //常亮默认值，这些参数若不调用方法传递，则直接使用默认值
    public static final int ROUND_RADIUS = 30;
    public static final int TEXT_COLOR_DEFAULT = Color.BLACK;
    public static final int TEXT_COLOR_SELECTED = Color.WHITE;
    public static final int TEXT_SIZE = 30;
    public static final int BACKGROUND_COLOR_DEFAULT = Color.GRAY;
    public static final int BACKGROUND_COLOR_SELECTED = Color.GREEN;
    public static final int HORIZONTAL_PADDING = 30;
    public static final int VERTICAL_PADDING = 30;
    public static final int TEXT_HORIZONTAL_PADDING = 30;
    public static final int ITEM_HEIGHT = 60;
    public static final int MAX_COUNT = 3;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int textColorDefault = TEXT_COLOR_DEFAULT;
    private int textColorSelected = TEXT_COLOR_SELECTED;
    private int textSize = TEXT_SIZE;
    private int backgroundColorDefault = BACKGROUND_COLOR_DEFAULT;
    private int backgroundColorSelected = BACKGROUND_COLOR_SELECTED;
    //Tag之间的横向和纵向的间隔
    private int horizontalPadding = HORIZONTAL_PADDING;
    private int verticalPadding = VERTICAL_PADDING;
    //每个Tag内部的横向间隔
    private int textHorizontalPadding = TEXT_HORIZONTAL_PADDING;
    //每个Tag的高度
    private int itemHeight = ITEM_HEIGHT;
    //最大选择数量
    private int maxCount = MAX_COUNT;

    //tag的绘制起点，动态计算得值
    private int startX;
    private int startY;
    //Tag显示的文本
    private String[] datas;
    private List<Tag> tags = new ArrayList<Tag>();

    //点击事件的滑动距离阈值
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    //ACTION_DOWN时的坐标值
    private float mTouchX;
    private float mTouchY;
    //ACTION_DOWN时选中的tag的索引
    private int mTouchPosition;
    //当前选中的数量
    private int selectedCount = 0;

    private OnTagSelectedListener listener;

    public FlowTagView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public FlowTagView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public FlowTagView(Context context){
        super(context);
    }

    public FlowTagView textColor(int defaultColor, int selectedColor){
        this.textColorDefault = defaultColor;
        this.textColorSelected = selectedColor;
        return this;
    }

    public FlowTagView textSize(int textSize){
        this.textSize = textSize;
        return this;
    }

    public FlowTagView backgroundColor(int defaultColor, int selectedColor){
        this.backgroundColorDefault = defaultColor;
        this.backgroundColorSelected = selectedColor;
        return this;
    }

    public FlowTagView padding(int horizontalPadding, int verticalPadding, int textHorizontalPadding){
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
        this.textHorizontalPadding = textHorizontalPadding;
        return this;
    }

    public FlowTagView maxCount(int maxCount){
        this.maxCount = maxCount;
        return this;
    }

    public FlowTagView itemHeight(int height){
        this.itemHeight = height;
        return this;
    }

    public FlowTagView datas(String[] datas){
        this.datas = datas;
        return this;
    }

    public FlowTagView listener(OnTagSelectedListener listener){
        this.listener = listener;
        return this;
    }

    public void commit(){
        if(datas == null){
            Log.e("FlowTagView", "maybe not invok the method named datas(String[])");
            throw new IllegalStateException("maybe not invok the method named datas(String[])");
        }
        paint.setTextSize(textSize);
        if(datas.length != tags.size()){
            //重新实例化
            ViewGroup.LayoutParams params = getLayoutParams();
            setLayoutParams(params);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //算出绘制起点
        startX = getPaddingLeft();
        startY = getPaddingTop();
        tags.clear();
        for(int i = 0; i < datas.length; i++){
            //判断是否越过边界
            if(startX + getRealWidth(paint, textSize, datas[i], textHorizontalPadding) + horizontalPadding > width - getPaddingRight()){
                //在下一行开始绘制
                startX = getPaddingLeft();
                startY += itemHeight + verticalPadding;
            }
            Tag tag = new Tag(datas[i], textSize, textColorDefault, textColorSelected,
                    backgroundColorDefault, backgroundColorSelected, paint, itemHeight, textHorizontalPadding, startX, startY);
            tags.add(tag);
            //动态更新值
            startX += getRealWidth(paint, textSize, datas[i], textHorizontalPadding) + horizontalPadding;
        }
        //算出整个控件需要的高度
        int height = startY + itemHeight + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    /**
     * 根据参数算出某个Tag所需要占用的宽度值，包括内补白
     */
    public static int getRealWidth(Paint paint, int textSize, String text, int textHorizontalPadding){
        paint.setTextSize(textSize);
        int textWidth = (int)paint.measureText(text);
        return textWidth + 2 * textHorizontalPadding;
    }

    @Override
    protected void onDraw(Canvas canvas){
        //绘制代理
        for(int i = 0; i < tags.size(); i++){
            tags.get(i).draw(canvas);
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        switch(event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                mTouchX = event.getX();
//                mTouchY = event.getY();
//                mTouchPosition = getTagPosition(mTouchX, mTouchY);
//                return true;
//
//            case MotionEvent.ACTION_UP:
//                float mUpX = event.getX();
//                float mUpY = event.getY();
//                //滑动距离小于点击阈值并且点击时的索引值不是非法值，并且up时的索引值和down时的索引值相等时，才触发选中操作
//                if(Math.abs(mUpX - mTouchX) < mTouchSlop && Math.abs(mUpY - mTouchY) < mTouchSlop
//                        && mTouchPosition != -1 && getTagPosition(mUpX, mUpY) == mTouchPosition){
//                    //触发点击选中
//                    setSelect(mTouchPosition);
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    /**
     * 根本坐标值，返回对应的tag的索引，若不存在则返回-1
     */
    private int getTagPosition(float x, float y){
        for(int i = 0; i < tags.size(); i++){
            if(tags.get(i).rect.contains(x, y)){
                return i;
            }
        }
        return -1;
    }

//    public void setSelect(int position){
//        if(position < 0 || position >= tags.size()){
//            Log.e("FlowTagView", "the position is illetal");
//            throw new IllegalArgumentException("the position is illetal");
//        }
//       if (!tags.get(position).isSelected){
//           if (selectedCount >= maxCount){
//
//               DisplayUtils.getInstance().showShortToast("最多选择3个标签");
//           }else{
//               tags.get(position).isSelected = true;
//               selectedCount ++;
//               //触发监听器
//               if(listener != null){
//                   listener.onTagSelected(this, position);
//               }
//           }
//       }else{
//           tags.get(position).isSelected = false;
//           selectedCount --;
//           //触发监听器
//           if(listener != null){
//               listener.onTagDisSelected(this, position);
//           }
//       }
//        //必须要刷新UI
//        invalidate();
//    }

    public int getSelect(){
        for(int i = 0; i < tags.size(); i++){
            if(tags.get(i).isSelected){
                return i;
            }
        }
        return -1;
    }

    public class Tag{
        //文本属性
        public String text;
        public int textColorDefault;
        public int textColorSelected;
        public int backgroundColorDefault;
        public int backgroundColorSelected;
        public boolean isSelected;
        public Paint paint;
        //文本的绘制起点
        public int drawX;
        public int drawY;
        //整个Tag占用的坐标范围
        public RectF rect = new RectF();

        public Tag(String text, int textSize, int textColorDefault, int textColorSelected, int backgroundColorDefault, int backgroundColorSelected,
                   Paint paint, int height, int horizontalPadding, int startX, int startY){
            this.text = text;
            this.textColorDefault = textColorDefault;
            this.textColorSelected = textColorSelected;
            this.backgroundColorDefault = backgroundColorDefault;
            this.backgroundColorSelected = backgroundColorSelected;
            this.paint = paint;
            //求出整个Tag的宽度
            paint.setTextSize(textSize);
            int textWidth = (int)paint.measureText(text);
            int width = textWidth + 2 * horizontalPadding;
            //计算坐标范围，startX，staryY是指左上角的起点
            rect.left = startX;
            rect.top = startY;
            rect.right = startX + width;
            rect.bottom = startY + height;
            //计算居中绘制时的绘制起点
            drawX = startX + horizontalPadding;
            Paint.FontMetrics metrics =  paint.getFontMetrics();
            drawY = (int)(startY + height / 2 + (metrics.bottom - metrics.top) / 2 - metrics.bottom);
        }

        public void draw(Canvas canvas){
            if(isSelected){
                //绘制背景
                paint.setColor(backgroundColorSelected);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rect, ROUND_RADIUS, ROUND_RADIUS, paint);
                //绘制文本
                paint.setColor(textColorSelected);
                canvas.drawText(text, drawX, drawY, paint);
            }else{
                //绘制背景
                paint.setColor(backgroundColorDefault);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRoundRect(rect, ROUND_RADIUS, ROUND_RADIUS, paint);
                //绘制文本
                paint.setColor(textColorDefault);
                canvas.drawText(text, drawX, drawY, paint);
            }
        }

    }

    public interface OnTagSelectedListener{
        void onTagSelected(FlowTagView view, int position);
        void onTagDisSelected(FlowTagView view, int position);
    }
}
