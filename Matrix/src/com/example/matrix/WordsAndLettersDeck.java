package com.example.matrix;

import java.util.HashMap;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
/*
 * This card deck has colors on the X axis and letters on the Y axis
 * On the actual cards, there are words that start with the letters on the Y axis painted in the colors of the X axis
 */
public class WordsAndLettersDeck extends CardsDeckBase {
	/* 
	 * wordsLettersPairs[N] are the letters
	 * wordsLettersPairs[N+1] are the words
	 */
	final public static String wordsLettersPairs[][] = {
														{"ג", "ל", "י", "ה"}, 
														{"גן", "לוח", "ילד","הולך"},
														
														{"A", "B", "C", "D"}, 
														{"Apple","Boy", "Car","Dog"},
														
														{"E", "F", "G", "H"}, 
														{"Egg", "Fox", "Good", "Home"},
														
														{"I", "J", "K", "L"}, 
														{"Ice", "Joy", "Kiss", "Love"},
														
														{"M", "N", "O", "P"}, 
														{"Moon", "No", "Orange", "Pepper"},
														
														{"Q", "R", "S", "T"}, 
														{"Queen", "Run", "Sit", "Tail"},
														
														{"U", "V", "W", "X"}, 
														{"Up", "Vicky", "Wait", "miX"},
														
														{"$", "*", "Y", "Z"}, 
														{"Dollar", "Star", "You", "Zoo"},
														
	};
	public String myLettersArray[];
	public String myWordsArray[];
	private HashMap<String, Drawable> myImages;
	
	public WordsAndLettersDeck(HashMap<String, Drawable> images) {
		super();
		System.out.println("Initializing WordsAndLettersDeck");		
		int set = 2*(int)Math.floor(Math.random() * (wordsLettersPairs.length)/2.0) ;
		myBasicVerticalTypes = wordsLettersPairs[set];
		myLettersArray = wordsLettersPairs[set];
		myWordsArray = wordsLettersPairs[set + 1];
		myBasicHorizontalTypes = ColorsAndShapesDeck.myBasicColorArray;
		shuffleHorizontal();
		shuffleVertical();
		myImages= images;
	}

	@Override
	public void drawCard(Canvas canvas, float TileCenterX, float TileCenterY,
			int letterNum, int color) {
		if (MatrixCardManager.theCardManager == null){
			return;
		}
		boolean drawWord = false;
		if (color != CardsDeck.DRAW_SAMPLE && letterNum != CardsDeck.DRAW_SAMPLE){
			drawWord = true;
		}
			
		if (color == CardsDeck.DRAW_SAMPLE){
			myPaint.setColor(Color.BLACK);
		}
		else if (color >= 0 && color < myCurrentHorizontalTitles.length){
			myPaint.setColor((Integer)(myCurrentHorizontalTitles[color]));
		} else{
			myPaint.setColor(color);
		}
		
		if (letterNum == CardsDeck.DRAW_SAMPLE){//Draw a sample color
			myPaint.setStyle(Paint.Style.FILL);
			float bigRectHalfWidth = 	0.5f*MatrixCardManager.theCardManager.myCardWidth*0.75f;
			float bigRectHalfHeight= 	0.5f*MatrixCardManager.theCardManager.myCardHeight*0.75f;
			canvas.drawCircle(TileCenterX, TileCenterY, (bigRectHalfWidth < bigRectHalfHeight ? bigRectHalfWidth : bigRectHalfHeight), myPaint);
			return;
		}
		if (!drawWord){
			//Draw a sample letter
			//myPaint.setTextSize(48);
			//canvas.drawText(myLettersArray[letterNum], TileCenterX, TileCenterY, myPaint);
			sizeTextAndDraw(myLettersArray[letterNum], TileCenterX, TileCenterY,
					MatrixCardManager.theCardManager.myCardWidth*0.25f,
					MatrixCardManager.theCardManager.myCardHeight*0.25f,
					canvas);
		}
		else {			
			float textHeight = 0.75f;
			float textPosition = 0;
			String word = myWordsArray[letterNum];
			Drawable image = myImages.get(myLettersArray[letterNum].toLowerCase());
			
			if (image != null) {				
				image.setBounds(0, 0, 
						(int)(MatrixCardManager.theCardManager.myCardWidth*0.80f),
						(int)(MatrixCardManager.theCardManager.myCardHeight*0.50f));
				textHeight = 0.25f;
				textPosition = 0.5f;
				image.draw(canvas);	
			}			
			
			sizeTextAndDraw(word, 
					(int)(0.1*MatrixCardManager.theCardManager.myCardHeight*textPosition),
					(int)(MatrixCardManager.theCardManager.myCardHeight*textPosition),
					MatrixCardManager.theCardManager.myCardWidth*0.75f,
					MatrixCardManager.theCardManager.myCardHeight*textHeight,
					canvas);						
		} 
	}
//	void sizeTextAndDraw(String word, float x, float y, float width, float height, Canvas canvas){
//		Path path = new Path();		
//		path.moveTo(x, y);
//		path.lineTo(x+width, y+100);
//		myPaint.setColor(Color.BLACK);
//		myPaint.setStrokeWidth(10);
//		canvas.drawPath(path, myPaint);
//		canvas.drawTextOnPath(word,
//							  path,
//							  0,
//							  height, myPaint);
//	}
	void sizeTextAndDraw(String word, float x, float y, float width, float height, Canvas canvas){
		myPaint.setTextSize(100);
		myPaint.setTextScaleX(1.0f);
		Rect bounds = new Rect();
		// ask the paint for the bounding rect if it were to draw this
		// text
		myPaint.getTextBounds(word, 0, word.length(), bounds);

		// get the height that would have been produced
		int h = bounds.bottom - bounds.top;		
		// make the text text up 70% of the height
		//float target = (float)height*.7f;

		// figure out what textSize setting would create that height
		// of text
		float size  = ((height/h)*100f);

		// and set it into the paint
		myPaint.setTextSize(size);
		myPaint.setTextScaleX(1.0f);

		// ask the paint for the bounding rect if it were to draw this
		// text.
		myPaint.getTextBounds(word, 0, word.length(), bounds);
		// determine the width
		int w = bounds.right - bounds.left;
		// calculate the baseline to use so that the
		// entire text is visible including the descenders
		//int text_h = bounds.bottom-bounds.top;
		//mTextBaseline=bounds.bottom+((mViewHeight-text_h)/2);

		// determine how much to scale the width to fit the view
		float xscale = ((float) (width)) / w ;
		xscale = (xscale < 1.5f ? xscale : 1.5f);

		// set the scale for the text paint
		myPaint.setTextScaleX(xscale);
		canvas.drawText(word, x+ bounds.left-2, y + height-2, myPaint);
	}

}
