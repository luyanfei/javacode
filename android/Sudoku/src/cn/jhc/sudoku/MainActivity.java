package cn.jhc.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener{

	private static final String TAG = "Sudoku";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View continueButton = findViewById(R.id.continue_button);
		continueButton.setOnClickListener(this);
		View newButton = findViewById(R.id.new_game_button);
		newButton.setOnClickListener(this);
		View aboutButton = findViewById(R.id.about_button);
		aboutButton.setOnClickListener(this);
		View exitButton = findViewById(R.id.exit_button);
		exitButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.main_settings:
			startActivity(new Intent(this, Prefs.class));
			break;

		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Music.play(this, R.raw.main);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Music.stop(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_button:
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.new_game_button:
			openNewGameDialog();
			break;
		case R.id.exit_button:
			finish();
			break;
		case R.id.continue_button:
			startGame(Game.DIFFICULTY_CONTINUE);
		default:
			break;
		}
	}

	private void openNewGameDialog() {
		new AlertDialog.Builder(this)
			.setTitle(R.string.difficulty_title)
			.setItems(R.array.difficulty, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame(which);
				}
			}).show();
	}

	protected void startGame(int i) {
		Log.d(TAG , "Clicked on " + i);
		Intent intent = new Intent(MainActivity.this, Game.class);
		intent.putExtra(Game.KEY_DIFFICULTY, i);
		startActivity(intent);
	}

}
