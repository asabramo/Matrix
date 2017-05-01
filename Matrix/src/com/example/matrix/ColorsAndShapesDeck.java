package com.example.matrix;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ColorsAndShapesDeck extends CardsDeckBase{
	final public static Integer myBasicColorArray[] = {Color.BLUE, Color.RED, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.GRAY};
	final public static Integer myBasicShapesArray[] = {ColorsAndShapesDeck.MONSTER_1, ColorsAndShapesDeck.SHAPE_BIG_EMPTY_CIRCLE, ColorsAndShapesDeck.SHAPE_SMALL_EMPTY_CIRCLE, ColorsAndShapesDeck.SHAPE_SMALL_FULL_CIRCLE, ColorsAndShapesDeck.SHAPE_BIG_FULL_CIRCLE, ColorsAndShapesDeck.SHAPE_BIG_FULL, ColorsAndShapesDeck.SHAPE_BIG_EMPTY, ColorsAndShapesDeck.SHAPE_SMALL_FULL, ColorsAndShapesDeck.SHAPE_SMALL_EMPTY };
	static final int SHAPE_BIG_FULL = 0;
	static final int SHAPE_BIG_EMPTY = 1;
	static final int SHAPE_SMALL_FULL = 2;
	static final int SHAPE_SMALL_EMPTY = 3;
	static final int SHAPE_BIG_FULL_CIRCLE = 5;
	static final int SHAPE_SMALL_FULL_CIRCLE = 6;
	static final int SHAPE_BIG_EMPTY_CIRCLE = 7;
	static final int SHAPE_SMALL_EMPTY_CIRCLE = 8;
	static final int MONSTER_1 = 9;
	
	public ColorsAndShapesDeck() {
		super();		 
//		myCurrentVerticalTitles = {Color.BLUE, Color.RED, Color.YELLOW, Color.MAGENTA};
//		myCurrentHotizontzlTitles = {ColorsAndShapesDeck.SHAPE_BIG_FULL, ColorsAndShapesDeck.SHAPE_BIG_EMPTY, ColorsAndShapesDeck.SHAPE_SMALL_FULL, ColorsAndShapesDeck.SHAPE_SMALL_EMPTY};
		myBasicVerticalTypes = myBasicColorArray;
		myBasicHorizontalTypes =  myBasicShapesArray;
		shuffleHorizontal();
		shuffleVertical();
	}
	
	public void drawCard(Canvas canvas, float TileCenterX, float TileCenterY, int vertNum, int horizNum) {
		drawShape(canvas, TileCenterX, TileCenterY, vertNum, horizNum);
	}

	public void drawShape(Canvas canvas, float TileCenterX, float TileCenterY, int shapeNum, int color){
		if ((MatrixCardManager.theCardManager == null) || 
			(myCardsArray == null)){
			return;
		}
		if (color == CardsDeck.DRAW_SAMPLE){
			myPaint.setColor(Color.BLACK);
		}
		else if (color >= 0 && color < myCurrentVerticalTitles.length){
			myPaint.setColor((Integer)(myCurrentVerticalTitles[color]));
		} else{
			myPaint.setColor(color);
		}
		int shapeEnum = 7000;//uninit
		if (shapeNum == CardsDeck.DRAW_SAMPLE){
			shapeEnum = ColorsAndShapesDeck.SHAPE_BIG_FULL_CIRCLE;
		}
		else{
			shapeEnum = (Integer)(myCurrentHorizontalTitles[shapeNum]);
		}
			
		float bigRectHalfWidth = 	0.5f*MatrixCardManager.theCardManager.myCardWidth*0.75f;
		float bigRectHalfHeight= 	0.5f*MatrixCardManager.theCardManager.myCardHeight*0.75f;
		float smallRectHalfWidth = 	0.5f*MatrixCardManager.theCardManager.myCardWidth*0.35f;
		float smallRectHalfHeight= 	0.5f*MatrixCardManager.theCardManager.myCardHeight*0.35f;
		myPaint.setStrokeWidth(5);
		
		switch(shapeEnum){		
		
		case ColorsAndShapesDeck.SHAPE_SMALL_EMPTY://Small filled rect 
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawRect(TileCenterX-smallRectHalfWidth, 
					TileCenterY-smallRectHalfHeight, 
					TileCenterX+smallRectHalfWidth, TileCenterY+smallRectHalfHeight, myPaint);
			break;
		case ColorsAndShapesDeck.SHAPE_BIG_EMPTY://Large empty rect
			myPaint.setStyle(Paint.Style.STROKE);			
			canvas.drawRect(TileCenterX-bigRectHalfWidth, 
					TileCenterY-bigRectHalfHeight, 
					TileCenterX+bigRectHalfWidth, TileCenterY+bigRectHalfHeight, myPaint);
			break;
		case ColorsAndShapesDeck.SHAPE_BIG_FULL://Large filled rect 
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawRect(TileCenterX-bigRectHalfWidth, 
					TileCenterY-bigRectHalfHeight, 
					TileCenterX+bigRectHalfWidth, TileCenterY+bigRectHalfHeight, myPaint);
			break;		
		case ColorsAndShapesDeck.SHAPE_SMALL_FULL:
			myPaint.setStyle(Paint.Style.STROKE);
			canvas.drawRect(TileCenterX-smallRectHalfWidth, 
					TileCenterY-smallRectHalfHeight, 
					TileCenterX+smallRectHalfWidth, TileCenterY+smallRectHalfHeight, myPaint);
			break;
			
			
		case ColorsAndShapesDeck.SHAPE_BIG_FULL_CIRCLE:
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(TileCenterX, TileCenterY, (bigRectHalfWidth < bigRectHalfHeight ? bigRectHalfWidth : bigRectHalfHeight), myPaint);
			break;
		case ColorsAndShapesDeck.SHAPE_SMALL_FULL_CIRCLE:
			myPaint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(TileCenterX, TileCenterY, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			break;
		case ColorsAndShapesDeck.SHAPE_BIG_EMPTY_CIRCLE:
			myPaint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(TileCenterX, TileCenterY, (bigRectHalfWidth < bigRectHalfHeight ? bigRectHalfWidth : bigRectHalfHeight) , myPaint);
			break;
		case ColorsAndShapesDeck.SHAPE_SMALL_EMPTY_CIRCLE:
			myPaint.setStyle(Paint.Style.STROKE);			
			canvas.drawCircle(TileCenterX, TileCenterY, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			break;
			
			
		case ColorsAndShapesDeck.MONSTER_1:
			myPaint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(TileCenterX-smallRectHalfWidth, TileCenterY-smallRectHalfHeight, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			canvas.drawCircle(TileCenterX+smallRectHalfWidth, TileCenterY+smallRectHalfHeight, (smallRectHalfWidth < smallRectHalfHeight ? smallRectHalfWidth : smallRectHalfHeight), myPaint);
			canvas.drawCircle(TileCenterX, TileCenterY, (bigRectHalfWidth < bigRectHalfHeight ? bigRectHalfWidth : bigRectHalfHeight) , myPaint);
			break;
	
		}
	}
}
