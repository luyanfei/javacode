package cn.jhc.myorbquest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends Activity {
	private final static int DIALOG_CONFIRM_SHARE = 10;

	private TextView turnsTextView;
	private TextView scoreTextView;
	private GameView gameView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.game);

		turnsTextView = (TextView) findViewById(R.id.turnsTextView);
		scoreTextView = (TextView) findViewById(R.id.scoreTextView);

		gameView = (GameView) findViewById(R.id.gameView);

		gameView.reset();
	}
}
