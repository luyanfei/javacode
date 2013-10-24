package cn.jhc.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

public class PuzzleView extends View {

	private static final String TAG = "Sudoku";
	private final Game game;
	private float width;
	private float height;
	private Rect selRect = new Rect();
	private int selY;
	private int selX;
	private static Paint background = new Paint();
	private static Paint dark = new Paint();
	private static Paint hilite = new Paint();
	private static Paint light = new Paint();
	private static Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static Paint selected = new Paint();
	private static Paint hint = new Paint();
	private int[] hint_colors = {
			getResources().getColor(R.color.puzzle_hint_0),
			getResources().getColor(R.color.puzzle_hint_1),
			getResources().getColor(R.color.puzzle_hint_2) };
	
	private static final int ID = 1001;

	public PuzzleView(Context context) {
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		setId(ID);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w / 9f;
		height = h / 9f;
		setRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void setRect(int x, int y, Rect rect) {
		rect.set((int) (x * width), (int) (y * height),
				(int) (x * width + width), (int) (y * height + height));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);

		dark.setColor(getResources().getColor(R.color.puzzle_dark));

		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));

		light.setColor(getResources().getColor(R.color.puzzle_light));

		for (int i = 0; i < 9; i++) {
			canvas.drawLine(0, i * height, getWidth(), i * height, light);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1,
					hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(),
					hilite);
		}

		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0)
				continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, dark);
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), dark);
		}

		// draw numbers
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);

		FontMetrics fontMetrics = foreground.getFontMetrics();
		float x = width / 2;
		float y = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++)
				canvas.drawText(this.game.getTileString(i, j), i * width + x, j
						* height + y, foreground);
		}

		// 绘制当前选定的单元格
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);

		// draw hint
		if (Prefs.getHints(getContext())) {
			Rect rect = new Rect();
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					int movesleft = 9 - game.getUsedTiles(i, j).length;
					if (movesleft < hint_colors.length) {
						setRect(i, j, rect);
						hint.setColor(hint_colors[movesleft]);
						canvas.drawRect(rect, hint);
					}
				}
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			select(selX, selY - 1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			select(selX, selY + 1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			select(selX - 1, selY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			select(selX + 1, selY);
			break;

		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE:
			setSelectedTile(0);
			break;
		case KeyEvent.KEYCODE_1:
			setSelectedTile(1);
			break;
		case KeyEvent.KEYCODE_2:
			setSelectedTile(2);
			break;
		case KeyEvent.KEYCODE_3:
			setSelectedTile(3);
			break;
		case KeyEvent.KEYCODE_4:
			setSelectedTile(4);
			break;
		case KeyEvent.KEYCODE_5:
			setSelectedTile(5);
			break;
		case KeyEvent.KEYCODE_6:
			setSelectedTile(6);
			break;
		case KeyEvent.KEYCODE_7:
			setSelectedTile(7);
			break;
		case KeyEvent.KEYCODE_8:
			setSelectedTile(8);
			break;
		case KeyEvent.KEYCODE_9:
			setSelectedTile(9);
			break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			game.showKeypadOrError(selX, selY);
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		select((int) (event.getX() / width), (int) (event.getY() / height));
		game.showKeypadOrError(selX, selY);
		return true;
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable parcelable = super.onSaveInstanceState();
		Bundle bundle = new Bundle();
		bundle.putInt("selX", selX);
		bundle.putInt("selY", selY);
		bundle.putParcelable("viewState", parcelable);
		return bundle;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		Bundle bundle = (Bundle) state;
		select(bundle.getInt("selX"), bundle.getInt("selY"));
		super.onRestoreInstanceState(bundle.getParcelable("viewState"));
	}
	/**
	 * 设定当前单元格
	 * 
	 * @param x
	 * @param y
	 */
	private void select(int x, int y) {
		invalidate(selRect);
		selX = Math.min(Math.max(x, 0), 8);
		selY = Math.min(Math.max(y, 0), 8);
		setRect(selX, selY, selRect);
		invalidate(selRect);
	}

	public void setSelectedTile(int tile) {
		if (game.setTileIfValid(selX, selY, tile)) {
			invalidate();
		} else {
			Log.d(TAG, "setSelectedTile: invalid: " + tile);
			startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
		}
	}
}
