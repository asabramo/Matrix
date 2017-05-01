package com.example.matrix;

public class CardStruct {
	int myId = 0;
	int myVerticalType;
	int myHorizType;
	
	boolean isSnappedToRightLocation = false;
	MatrixCardView myView = null;
	
	public CardStruct(int vertical, int horizontal) {
		myVerticalType = vertical;
		myHorizType = horizontal;
		isSnappedToRightLocation = false;
	}

}
