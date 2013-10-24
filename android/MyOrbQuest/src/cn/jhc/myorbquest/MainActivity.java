package cn.jhc.myorbquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

	private Button playGameButton;
	private Button highScoreButton;
	private Button aboutButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		playGameButton = (Button) findViewById(R.id.startButton);
		highScoreButton = (Button) findViewById(R.id.highScoreButton);
		aboutButton = (Button) findViewById(R.id.aboutButton);

		playGameButton.setOnClickListener(this);
		highScoreButton.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View button) {
		if(button == playGameButton) {
			Intent intent = new Intent(this, GameActivity.class);
			startActivity(intent);
		}
		else if (button == highScoreButton) {
			
		}
		else if (button == aboutButton) {
			
		}
	}

}
