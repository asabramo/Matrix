package com.example.matrix;

public class CardStruct {
	int myId = 0;
	int myShape;
	int myColor;
	float myHeight, myWidth;
	boolean isSnappedToRightLocation = false;
	MatrixCardView myView = null;
	
	static final int SHAPE_BIG_FULL = 0;
	static final int SHAPE_BIG_EMPTY = 1;
	static final int SHAPE_SMALL_FULL = 2;
	static final int SHAPE_SMALL_EMPTY = 3;	
	
	static final int SHAPE_BIG_FULL_CIRCLE = 5;
	static final int SHAPE_SMALL_FULL_CIRCLE = 6;
	static final int SHAPE_BIG_EMPTY_CIRCLE = 7;
	static final int SHAPE_SMALL_EMPTY_CIRCLE = 8;
	
	static final int MONSTER_1 = 9;
	
	
	public CardStruct(int shape, int color) {
		myShape = shape;
		myColor = color;
		isSnappedToRightLocation = false;
	}

}
