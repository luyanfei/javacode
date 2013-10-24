package cn.jhc.sudoku;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {

	private static MediaPlayer mPlayer = null;

	public static void play(Context context, int resource) {
		stop(context);
		if (Prefs.getMusic(context)) {
			mPlayer = MediaPlayer.create(context, resource);
			mPlayer.setLooping(true);
			mPlayer.start();
		}
	}

	public static void stop(Context context) {
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		}
	}
}
