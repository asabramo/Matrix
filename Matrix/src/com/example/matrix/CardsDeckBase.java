package com.example.matrix;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

abstract public class CardsDeckBase implements CardsDeck{

	protected Paint myPaint;
	public Object myBasicVerticalTypes[] = null;
	public Object myBasicHorizontalTypes[] = null;
	public Object myCurrentVerticalTitles[];
	public Object myCurrentHorizontalTitles[];
	protected CardStruct myCardsArray[];

	public CardsDeckBase() {
		myPaint = new Paint();
		myCardsArray = new CardStruct[16];
		myCurrentHorizontalTitles = new Object[MatrixCardManager.GRID_SIZE];
		myCurrentVerticalTitles = new Object[MatrixCardManager.GRID_SIZE];
		int i = 0;
		//Create the cards
		while (i< myCardsArray.length){
			for (int vertical = 0; vertical < myCurrentHorizontalTitles.length ; vertical++){
				for (int horiz = 0; horiz < myCurrentVerticalTitles.length; horiz++){
					myCardsArray[i] = new CardStruct(vertical, horiz);
					i++;
				}
			}
		}		
	}

	@Override
	public CardStruct[] getCards() {
		return myCardsArray;
	}
		
	public void drawTheRowTitles(Canvas canvas, float firstTileCenterX, float firstTileCenterY) {
		for (int i = 1; i < MatrixCardManager.GRID_SIZE+1; i++){
			drawCard(canvas, firstTileCenterX + (i*MatrixCardManager.theCardManager.myCardWidth), firstTileCenterY, CardsDeck.DRAW_SAMPLE, i-1);				
		}			
	}

	public void drawTheColumnTitles(Canvas canvas, float firstTileCenterX, float firstTileCenterY) {
		for (int i = 1; i < MatrixCardManager.GRID_SIZE+1; i++){
			float currentTileCenterY = firstTileCenterY + (i*MatrixCardManager.theCardManager.myCardHeight);
			drawCard(canvas, firstTileCenterX, currentTileCenterY, i-1, CardsDeck.DRAW_SAMPLE);				
		}
	}

	@Override
	public void shuffleVertical() {
		for (int i = 0; i < myCurrentHorizontalTitles.length; i++) {
			int rand = -1;
			while (rand < 0){
				rand = (int)(Math.random()*(myBasicHorizontalTypes.length));
				for (int j = 0; j < i; j++){
					if ((myCurrentHorizontalTitles[j] != null) &&
						(myCurrentHorizontalTitles[j].equals(myBasicHorizontalTypes[rand]))){
						rand = -1;
						break;
					}
				}
			}
			myCurrentHorizontalTitles[i] = myBasicHorizontalTypes[rand];
		}		
	}

	@Override
	public void shuffleHorizontal() {
		for (int i = 0; i < myCurrentVerticalTitles.length; i++) {
			int rand = -1;
			while (rand < 0){
				rand = (int)(Math.random()*(myBasicVerticalTypes.length));
				for (int j = 0; j < i; j++){
					if (myCurrentVerticalTitles[j] == myBasicVerticalTypes[rand]){
						rand = -1;
						break;
					}
				}
			}
			myCurrentVerticalTitles[i] = myBasicVerticalTypes[rand];		
		}
	}
}