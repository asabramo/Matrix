package com.example.matrix;

import android.graphics.Canvas;
import android.graphics.Paint;

public class MatrixDrawingUtils {
	Paint myPaint;
	public MatrixDrawingUtils() {
		myPaint = new Paint();
	}
	public void drawShape(Canvas canvas, float TileCenterX, float TileCenterY, int shapeNum, int color){
		if (MatrixCardManager.theCardManager == null){
			return;
		}
		if (color >= 0 && color < MatrixCardManager.myColorArray.length){
			myPaint.setColor(MatrixCardManager.myColorArray[color]);
		} else{
			myPaint.setColor(color);
		}
			
		float bigRectHalfWidth = 	0.5f*MatrixCardManager.theCardManager.myCardWidth*0.75f;
		float bigRectHalfHeight= 	0.5f*MatrixCardManager.theCardManager.myCardHeight*0.75f;
		float smallRectHalfWidth = 	0.5f*MatrixCardManager.theCardManager.myCardWidth*0.35f;
		float smallRectHalfHeight= 	0.5f*MatrixCardManager.theCardManager.myCardHeight*0.35f;
		myPaint.setStrokeWidth(5);
		
		switch(MatrixCardManager.myShapesArray[shapeNum]){		
		
		case CardStruct.SHAPE_SMALL_EMPTY://Small filled rect 
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawRect(TileCenterX-smallRectHalfWidth, 
					TileCenterY-smallRectHalfHeight, 
					TileCenterX+smallRectHalfWidth, TileCenterY+smallRectHalfHeight, myPaint);
			break;
		case CardStruct.SHAPE_BIG_EMPTY://Large empty rect
			myPaint.setStyle(Paint.Style.STROKE);			
			canvas.drawRect(TileCenterX-bigRectHalfWidth, 
					TileCenterY-bigRectHalfHeight, 
					TileCenterX+bigRectHalfWidth, TileCenterY+bigRectHalfHeight, myPaint);
			break;
		case CardStruct.SHAPE_BIG_FULL://Large filled rect 
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawRect(TileCenterX-bigRectHalfWidth, 
					TileCenterY-bigRectHalfHeight, 
					TileCenterX+bigRectHalfWidth, TileCenterY+bigRectHalfHeight, myPaint);
			break;		
		case CardStruct.SHAPE_SMALL_FULL:
			myPaint.setStyle(Paint.Style.STROKE);
			canvas.drawRect(TileCenterX-smallRectHalfWidth, 
					TileCenterY-smallRectHalfHeight, 
					TileCenterX+smallRectHalfWidth, TileCenterY+smallRectHalfHeight, myPaint);
			break;
			
			
		case CardStruct.SHAPE_BIG_FULL_CIRCLE:
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(TileCenterX, TileCenterY, (bigRectHalfWidth < bigRectHalfHeight ? bigRectHalfWidth : bigRectHalfHeight), myPaint);
			break;
		case CardStruct.SHAPE_SMALL_FULL_CIRCLE:
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(TileCenterX, TileCenterY, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			break;
		case CardStruct.SHAPE_BIG_EMPTY_CIRCLE:
			myPaint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(TileCenterX, TileCenterY, (bigRectHalfWidth < bigRectHalfHeight ? bigRectHalfWidth : bigRectHalfHeight) , myPaint);
			break;
		case CardStruct.SHAPE_SMALL_EMPTY_CIRCLE:
			myPaint.setStyle(Paint.Style.STROKE);			
			canvas.drawCircle(TileCenterX, TileCenterY, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			break;
			
			
		case CardStruct.MONSTER_1:
			myPaint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(TileCenterX-smallRectHalfWidth, TileCenterY-smallRectHalfHeight, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			canvas.drawCircle(TileCenterX+smallRectHalfWidth, TileCenterY+smallRectHalfHeight, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			canvas.drawCircle(TileCenterX, TileCenterY, (bigRectHalfWidth < bigRectHalfHeight ? bigRectHalfWidth : bigRectHalfHeight) , myPaint);
			break;
	
		}
	}
	
}
