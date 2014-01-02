package com.example.matrix;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MatrixCardManager {
	final public static int myBasicColorArray[] = {Color.BLUE, Color.RED, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.GRAY};
	final public static int myBasicShapesArray[] = {CardStruct.MONSTER_1, CardStruct.SHAPE_BIG_EMPTY_CIRCLE, CardStruct.SHAPE_SMALL_EMPTY_CIRCLE, CardStruct.SHAPE_SMALL_FULL_CIRCLE, CardStruct.SHAPE_BIG_FULL_CIRCLE, CardStruct.SHAPE_BIG_FULL, CardStruct.SHAPE_BIG_EMPTY, CardStruct.SHAPE_SMALL_FULL, CardStruct.SHAPE_SMALL_EMPTY };
	public static int myColorArray[] = {Color.BLUE, Color.RED, Color.YELLOW, Color.MAGENTA};
	public static int myShapesArray[] = {CardStruct.SHAPE_BIG_FULL, CardStruct.SHAPE_BIG_EMPTY, CardStruct.SHAPE_SMALL_FULL, CardStruct.SHAPE_SMALL_EMPTY};
	static public MatrixCardManager theCardManager = null;
	private CardStruct myCardsArray[];	
	float myCardWidth;
	float myCardHeight;
	float myTopLeftCardX;
	float myTopLeftCardY;
	int myNumCards = 0;
	boolean isScreenInit = false;
	
	public MatrixCardManager() {
		myCardsArray = new CardStruct[16];
		int i = 0;
		while (i< myCardsArray.length){
			for (int shape = 0; shape < myShapesArray.length ; shape++){
				for (int color = 0; color < myColorArray.length; color++){
					myCardsArray[i] = new CardStruct(shape, color);
					i++;
				}
			}
		}
		System.out.println("CardAllocator initialized.");
	}
	
	static public void updateScreenParams(float width, float height, float topLeftCardX, float topLeftCardY){
		if (theCardManager == null){
			return;
		}
		theCardManager.myCardWidth = width;
		theCardManager.myCardHeight = height;
		theCardManager.myTopLeftCardX = topLeftCardX;
		theCardManager.myTopLeftCardY = topLeftCardY;	
		theCardManager.isScreenInit = true;
	}
	
	static public float getCardCorrectX(int shape, int color){
		return (theCardManager.myTopLeftCardX + (color+1)*theCardManager.myCardWidth);
	}
	static public float getCardCorrectY(int shape, int color){
//		System.out.println( "MatrixCardManager::getCardCorrectY() color = " + color + " theCardManager.myTopLeftCardY = " + theCardManager.myTopLeftCardY +
//				" theCardManager.myCardHeight = " + theCardManager.myCardHeight);
		return (theCardManager.myTopLeftCardY + (shape+1)*theCardManager.myCardHeight);
	}
	static public void shuffleColors(){
		for (int i = 0; i < myColorArray.length; i++) {
			int rand = -1;
			while (rand < 0){
				rand = (int)(Math.random()*(myBasicColorArray.length));
				for (int j = 0; j < i; j++){
					if (myColorArray[j] == myBasicColorArray[rand]){
						rand = -1;
						break;
					}
				}
			}
			myColorArray[i] = myBasicColorArray[rand];
		}
	}
	static public void shuffleShapes(){
		for (int i = 0; i < myShapesArray.length; i++) {
			int rand = -1;
			while (rand < 0){
				rand = (int)(Math.random()*(myBasicShapesArray.length));
				for (int j = 0; j < i; j++){
					if (myShapesArray[j] == myBasicShapesArray[rand]){
						rand = -1;
						break;
					}
				}
			}
			myShapesArray[i] = myBasicShapesArray[rand];
		}
	}
	static public void showOrHideAllCards(int visibility){
		if (theCardManager == null){
			return;
		}		
		
		theCardManager.showOrHideAllCardsInternal(visibility);
	}
	private void showOrHideAllCardsInternal(int visibility){
		for (int i = 0; i < myCardsArray.length; i++) {
			MatrixCardView view = myCardsArray[i].myView;
			if (view != null){
				view.setVisibility(visibility);
			}				
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	static public void  showNextCard(){
		if (theCardManager == null){
			return;
		}
		theCardManager.showNextCardInternal();
	}
	private void  showNextCardInternal(){
		CardStruct arrayOfValids[] = new CardStruct[myCardsArray.length];
		int numValids = 0;
		for (int i = 0; i < myCardsArray.length; i++) {
			MatrixCardView view = myCardsArray[i].myView;
			if (view != null &&
				view.getVisibility() != View.VISIBLE){
				arrayOfValids[numValids] = myCardsArray[i];  
				numValids++;
			}				
		}
		if (numValids == 0){	 		
			return;
		}
		//Choose a valid card to reveal and reveal it
		int rand = (int)(Math.random() * (numValids));
		CardStruct card = arrayOfValids[rand];
		
//		System.out.println( "card x = " + card.myView.getX() + " card y = " + card.myView.getY());
		card.myView.setX(myTopLeftCardX);
		card.myView.setY(myTopLeftCardY);
//		System.out.println( "card new x = " + card.myView.getX() + " card new y = " + card.myView.getY());
		setCardSize(card);
		card.myView.setVisibility(View.VISIBLE);
		card.myView.bringToFront();
	}
	
	static public CardStruct registerOrFindCard(int cardViewId, MatrixCardView view){
		if (theCardManager == null){
			return null;
		}
		return theCardManager.registerOrFindCardInternal(cardViewId, view);
	}
	private CardStruct registerOrFindCardInternal(int cardViewId, MatrixCardView view){
		CardStruct ret = null;
		for (int i = 0; i < myCardsArray.length; i++){
			CardStruct card = myCardsArray[i]; 
			if (card.myId == cardViewId){
				ret = card;	
				break;
			}				
		}
		//Not found - create new
		for (int i = 0; i < myCardsArray.length; i++){
			CardStruct card = myCardsArray[i]; 
			if (card.myId == 0){
				card.myId = cardViewId;
				card.myView = view;
				myNumCards++;
//				if (myNumCards == myCardsArray.length){
//					showNextCard();
//				}
				ret = card;
				break;
			}				
		}
		
		return ret;
	}
	private void setCardSize(CardStruct card){
		if (card.myView == null){
			return;
		}
		MatrixCardView view = card.myView;
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
		params.height = (int)myCardHeight;
		params.width = (int)myCardWidth;
		if (params.height != view.getHeight() ||
			params.width != view.getWidth()	)
		try{
			view.setLayoutParams(params);
			//view.setVisibility(View.VISIBLE);
		}
		catch (Exception e){
			System.out.println("got exception " + e.toString());
			view.setVisibility(View.INVISIBLE);				
		}
	}
	static public CardStruct getMyCard(int cardViewId, MatrixCardView view){
		if (theCardManager == null){
			return null;
		}
		return theCardManager.getMyCardInternal(cardViewId, view);
	}
	private CardStruct getMyCardInternal(int cardViewId, MatrixCardView view){
		CardStruct ret = registerOrFindCard(cardViewId, view);
		if (view != null && ret != null ||
			view.getVisibility() == View.VISIBLE){
			setCardSize(ret);
		}
		return ret;
	}		
	
}
