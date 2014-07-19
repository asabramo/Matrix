package com.example.matrix;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MatrixActivityMain extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//The Catd Manager needs to be created before the View is set
		MatrixCardManager.theCardManager = new MatrixCardManager(this);
		setContentView(R.layout.matrix_activity_main);	
		shuffleShapestMenuItem();
		MatrixCardManager.showNextCard();
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matrix_activity_main, menu);
		return true;
	}   
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.Restart:
	        	restartMenuItem();
	            return true;
	        case R.id.shuffle_color:
	        	shuffleColorstMenuItem();
	        	return true;
	        case R.id.shuffle_shape:
	        	shuffleShapestMenuItem(); 
	        	return true;
	        case R.id.words_letters:
	        	shuffleShapestMenuItem(); 
	        	return true;	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void buttonClicked(View view){    	
		MatrixCardManager.showNextCard();
	}
	public void restartMenuItem(){    	
		MatrixCardManager.showOrHideAllCards(View.INVISIBLE);
		MatrixCardManager.showNextCard();
	}
	public void invalidateMainView(){
		View v = findViewById(R.id.matrix_main_view);
		if (v != null){
			v.invalidate();
		}
	}
	
	public void shuffleColorstMenuItem(){    	
		MatrixCardManager.showOrHideAllCards(View.INVISIBLE);
		MatrixCardManager.shuffleColors();	
		invalidateMainView();
	}
	public void shuffleShapestMenuItem(){    	
		MatrixCardManager.showOrHideAllCards(View.INVISIBLE);
		MatrixCardManager.shuffleShapes();
		invalidateMainView();
	}
	
//	/* (non-Javadoc)
//	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
//	 */
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onRestoreInstanceState(savedInstanceState);
//		View layout = findViewById(R.layout.matrix_activity_main); 
//		for (int i=0; i < ((ViewGroup) layout).getChildCount();i++){
//			View view = ((ViewGroup) layout).getChildAt(i);
//			if (view.getClass() == MatrixCardView.class){
//				MatrixCardManager.registerOrFindCard(view.getId(), (MatrixCardView)view);
//			}
//		}
//	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}
