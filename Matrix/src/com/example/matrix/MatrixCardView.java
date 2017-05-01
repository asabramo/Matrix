package com.example.matrix;

import java.io.IOException;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MatrixCardView extends View implements OnTouchListener{

	float prevX, prevY;
	Paint myPaint;	
	private MediaPlayer myPlupMp;
	private GestureDetector mGestureDetector = null;
	private MatrixCardView theViewObject = null;
	public MatrixCardView(Context context) {
		super(context);
		initCardView(context);
	}

	public MatrixCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCardView(context);
	}

	public MatrixCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initCardView(context);		
	}

	private void initCardView(Context context)
	{
		theViewObject = this;
		setOnTouchListener(this);
		myPaint = new Paint();
		setBackgroundColor(Color.rgb(255, 250, 205));		
		setVisibility(View.INVISIBLE);	
		MatrixCardManager.registerOrFindCard(getId(), this);
		mGestureDetector = new GestureDetector(context, 
				new GestureDetector.SimpleOnGestureListener (){       	
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			public boolean onDoubleTap(MotionEvent e) {
				return (theViewObject.onDoubleTap(e));
			}	
		});
		myPlupMp = MediaPlayer.create(getContext(), R.raw.plup);
	}
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {		
		super.onDraw(canvas);
//		System.out.println( "MatrixCardView::onDraw() card x = " + getX() + " card y = " + getY());
		//Draw the card's background		
//		myPaint.setColor(Color.rgb(255, 250, 205));
//		myPaint.setStyle(Style.FILL);		
		
		//myPaint.setStrokeWidth(3);
//		canvas.drawRect(0, 0, getWidth()-1, getHeight()-1, myPaint);
		//Draw the card's frame		
		myPaint.setColor(Color.BLACK);
		myPaint.setStyle(Style.STROKE);
		myPaint.setStrokeWidth(3);
		canvas.drawRect(0, 0, getWidth()-1, getHeight()-1, myPaint);
		//Draw the card's content
		float centerX = (float)getWidth() / 2f;
		float centerY = (float)getHeight() / 2f;
		CardStruct cardStruct = MatrixCardManager.getMyCard(getId(), this);
		if (cardStruct == null){
			MatrixCardManager.registerOrFindCard(getId(), this);
			cardStruct = MatrixCardManager.getMyCard(getId(), this);
		}
		if (cardStruct != null){
			MatrixCardManager.drawCard(canvas, centerX, centerY, cardStruct.myVerticalType, cardStruct.myHorizType);	
		}
	}

	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		boolean ret = false;
		switch (event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
			prevX = event.getX();
			prevY = event.getY();
			ret = true;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			
			float deltaX = event.getX()-prevX;
			float deltaY = event.getY()-prevY;
			float finalX = v.getX()+deltaX; 
			float finalY = v.getY()+deltaY;
			
			//Handle snapping
			CardStruct cardStruct = MatrixCardManager.getMyCard(getId(), this);
			float correctX = MatrixCardManager.getCardCorrectX(cardStruct.myVerticalType, cardStruct.myHorizType);
			float correctY = MatrixCardManager.getCardCorrectY(cardStruct.myVerticalType, cardStruct.myHorizType);
			float diffX = Math.abs(finalX - correctX);
			float diffY = Math.abs(finalY - correctY);
//			System.out.println( "MatrixCardView::onTouch() diffX = " + diffX + " diffY = " + diffY);
			if ((diffX < 20) && (diffY < 20)){
//				System.out.println( "MatrixCardView::onTouch() SNAP");
				finalX = MatrixCardManager.getCardCorrectX(cardStruct.myVerticalType, cardStruct.myHorizType);
				finalY = MatrixCardManager.getCardCorrectY(cardStruct.myVerticalType, cardStruct.myHorizType);
				cardStruct.isSnappedToRightLocation = true;
			}
			else{
				cardStruct.isSnappedToRightLocation = false;
			}
			
			v.setX(finalX);
			v.setY(finalY);
			
			MatrixCardManager.checkVictory();
			
			ret = true;
		default:
			break;				
		}
		mGestureDetector.onTouchEvent(event);
		return ret;
	}
	
	public boolean onDoubleTap(MotionEvent e) {
		setVisibility(View.INVISIBLE);
		CardStruct cardStruct = MatrixCardManager.getMyCard(getId(), this);
		cardStruct.isSnappedToRightLocation = false;
       
		myPlupMp.start();
		return true;
	}
}
