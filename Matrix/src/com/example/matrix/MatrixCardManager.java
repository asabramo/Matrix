package com.example.matrix;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MatrixCardManager {
		
	static public MatrixCardManager theCardManager = null;
	private CardsDeck myCardsDeck;
	public static final int GRID_SIZE = 4;//a 4X4 grid with row and column titles
	//Card decks
	public final int CARDS_DECK_SHAPES = 0;
	public final int CARDS_DECK_WORDS_LETTERS = 0;

	private boolean myFirstCardEver = true;
	float myCardWidth;
	float myCardHeight;
	float myTopLeftCardX;
	float myTopLeftCardY;
	int myNumCards = 0;
	private MediaPlayer myWheepyMp;
	private HashMap<String, Drawable> myImagesMap;
	
	boolean isScreenInit = false;
	MatrixActivityMain myMatrixActivity;
	
	public MatrixCardManager(MatrixActivityMain context, CardsDeck initialDeck) {
		myMatrixActivity = context;		
		myCardsDeck = initialDeck;	
		myWheepyMp = MediaPlayer.create(context, R.raw.wheepy);
		//Init the images array
		Field[] fields = R.drawable.class.getFields();
		myImagesMap = new HashMap<String, Drawable>();	    
	    for (Field field : fields) {
	        if (field.getName().length() == 1) {
	            Drawable d;
				try {
					d = context.getResources().getDrawable(field.getInt(null));
					myImagesMap.put(field.getName(), d);
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
		
		System.out.println("CardAllocator initialized.");
	}
	
	public HashMap<String, Drawable> getImages() {
		return myImagesMap;
	}
	static public void setCardDeck(CardsDeck deck){
		if (theCardManager == null){
			return;
		}
		CardStruct cards[] = theCardManager.myCardsDeck.getCards();
		
		showOrHideAllCards(View.INVISIBLE);
		theCardManager.myCardsDeck = deck;		
		//In case we got a reused deck object
		shuffleHorizontal();
		shuffleVertical();
		//Update the card views - basically they are being reused
		for(CardStruct card : cards){
			MatrixCardManager.registerOrFindCard(card.myView.getId(), card.myView);
		}
		showOrHideAllCards(View.INVISIBLE);
		showNextCard();
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
	
	static public void drawCard(Canvas canvas, float TileCenterX, float TileCenterY, int vertNum, int horizNum){
		theCardManager.myCardsDeck.drawCard(canvas, TileCenterX, TileCenterY, vertNum, horizNum);
	}
	
	static public void drawTheRowTitles(Canvas canvas, float firstTileCenterX, float firstTileCenterY){
		theCardManager.myCardsDeck.drawTheRowTitles(canvas, firstTileCenterX, firstTileCenterY);
	}
	
	static public void drawTheColumnTitles(Canvas canvas, float firstTileCenterX, float firstTileCenterY){
		theCardManager.myCardsDeck.drawTheColumnTitles(canvas, firstTileCenterX, firstTileCenterY);
	}
	
	static public float getCardCorrectX(int shape, int color){
		return (theCardManager.myTopLeftCardX + (color+1)*theCardManager.myCardWidth);
	}
	static public float getCardCorrectY(int shape, int color){
//		System.out.println( "MatrixCardManager::getCardCorrectY() color = " + color + " theCardManager.myTopLeftCardY = " + theCardManager.myTopLeftCardY +
//				" theCardManager.myCardHeight = " + theCardManager.myCardHeight);
		return (theCardManager.myTopLeftCardY + (shape+1)*theCardManager.myCardHeight);
	}
	
	static public void shuffleHorizontal(){
		if (theCardManager == null){
			return;
		}
		theCardManager.myCardsDeck.shuffleHorizontal();
	}

	static public void shuffleVertical(){
		if (theCardManager == null){
			return;
		}
		theCardManager.myCardsDeck.shuffleVertical();
	}
	static public void showOrHideAllCards(int visibility){
		if (theCardManager == null){
			return;
		}		
		
		theCardManager.showOrHideAllCardsInternal(visibility);
	}
	private void showOrHideAllCardsInternal(int visibility){
		if (myCardsDeck == null){
			return;
		}
		CardStruct cards[] = myCardsDeck.getCards(); 
		
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] == null)
				break;
			MatrixCardView view = cards[i].myView;
			if (view != null){
				view.setVisibility(visibility);
				if (visibility == View.INVISIBLE){
					cards[i].isSnappedToRightLocation = false;					
				}
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
		if (myFirstCardEver){
			MatrixCardManager.showOrHideAllCards(View.INVISIBLE);
			myFirstCardEver = false;
		}
		if (myCardsDeck == null){
			return;
		}
		CardStruct cards[] = myCardsDeck.getCards();
		
			
		CardStruct arrayOfValids[] = new CardStruct[cards.length];
		int numValids = 0;
		for (int i = 0; i < cards.length; i++) {
			MatrixCardView view = cards[i].myView;
			if (view != null &&
				view.getVisibility() != View.VISIBLE){
				arrayOfValids[numValids] = cards[i];  
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
		CardStruct cards[] = myCardsDeck.getCards();
		for (int i = 0; i < cards.length; i++){
			CardStruct card = cards[i]; 
			if (card.myId == cardViewId){
				ret = card;	
				break;
			}				
		}
		//Not found - create new
		for (int i = 0; i < cards.length; i++){
			CardStruct card = cards[i]; 
			if (card.myId == 0){
				card.myId = cardViewId;
				card.myView = view;
				myNumCards++;
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
	
	static  public boolean checkVictory(){
		if (theCardManager == null){
			return false;
		}
		return theCardManager.checkVictoryInternal();
	}
	private boolean checkVictoryInternal(){
		CardStruct cards[] = myCardsDeck.getCards();
		for (int i = 0; i < cards.length; i++) {
			CardStruct cardStruct = cards[i];
			if (!cardStruct.isSnappedToRightLocation)
				return false;
		}
		System.out.println( "Victory!");
		myWheepyMp.start();
	//	MatrixDrawingUtils.drawSmiley();
		MatrixCardManager.shuffleHorizontal();
		MatrixCardManager.shuffleVertical();		
		MatrixCardManager.showOrHideAllCards(View.INVISIBLE);
		myMatrixActivity.invalidateMainView();
		MatrixCardManager.showNextCard();		
		return true;
	}
}
