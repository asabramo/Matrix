package com.example.matrix;

import android.graphics.Canvas;

public interface CardsDeck {

	//This constant indicates that a sample card should be drawn - e.g. if it's a shape, draw it in black, not in a color that's part of the game
	public final int DRAW_SAMPLE = 7000;
	public CardStruct[] getCards();
	public void shuffleVertical();
	public void shuffleHorizontal();
	//Draw the top row
	public void drawTheRowTitles(Canvas canvas, float firstTileCenterX, float firstTileCenterY);
	public void drawTheColumnTitles(Canvas canvas, float firstTileCenterX, float firstTileCenterY);
	public void drawCard(Canvas canvas, float TileCenterX, float TileCenterY, int vertNum, int horizNum);
	
}
