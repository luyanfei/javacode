package cn.jhc.opengldemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	GLView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new GLView(this);
		setContentView(view);
	}

	@Override
	protected void onPause() {
		super.onPause();
		view.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		view.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
