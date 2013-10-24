package cn.jhc.myorbquest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class GameView extends ViewGroup implements View.OnClickListener {
	private static final int GRID_NUM = 5;

	private final Drawable[] ORB_DRAWABLES = new Drawable[] {
		getResources().getDrawable(R.drawable.red_orb),
		getResources().getDrawable(R.drawable.green_orb),
		getResources().getDrawable(R.drawable.blue_orb)
	};
	
	private final static Random random = new Random();
	private OrbView[][] orbs = new OrbView[5][5];
	private OrbView selectedOrbView = null;
	private boolean acceptInput = true;

	@SuppressWarnings("deprecation")
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundDrawable(new Background());
	}

	public void reset() {
		acceptInput = true;
		
		removeAllViews();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				orbs[i][j] = new OrbView(getContext(),random.nextInt(3));
				addView(orbs[i][j]);
			}
		}		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		int size = Math.min(parentHeight - 20, parentWidth - 20);
		this.setMeasuredDimension(size, size);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int size = getWidth()/5;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				orbs[i][j].layout(j*size, i*size, (j+1)*size, (i+1)*size);
			}
		}
	}

	protected class OrbView extends ImageView{
		private int type;
		
		protected OrbView(Context context, int type) {
			super(context);
			this.type = type;

			setImageDrawable(ORB_DRAWABLES[type]);
			setClickable(true);
			setOnClickListener(GameView.this);
		}

		public int getType() {
			return type;
		}

		public void setRandomType() {
			this.type = random.nextInt(3);
			setImageDrawable(ORB_DRAWABLES[type]);
		}
		
		public void setType(int newType) {
			this.type = newType;
			setImageDrawable(ORB_DRAWABLES[type]);
		}
	}

	@Override
	public void onClick(View view) {
		if(!(view instanceof OrbView) || !acceptInput) 
			return;
		OrbView orbView = (OrbView)view;
		if(selectedOrbView == null) {
			selectedOrbView = orbView;
			Animation scaleDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
			orbView.startAnimation(scaleDownAnimation);
		}
		else {
			if(selectedOrbView == orbView) {
				Animation scaleUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
				orbView.startAnimation(scaleUpAnimation);
				selectedOrbView = null;
			}
			else {
				swapOrbs(selectedOrbView, orbView);
				selectedOrbView = null;
			}
		}
	}

	private void swapOrbs(final OrbView orb1, final OrbView orb2) {
//		turns--;
//
		acceptInput = false;

		//Animate Orb1
		TranslateAnimation trans1 = new TranslateAnimation(0, orb2.getLeft()
				- orb1.getLeft(), 0, orb2.getTop() - orb1.getTop());
		trans1.setDuration(500);
		trans1.setStartOffset(500);

		ScaleAnimation scaleUp1 = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
				Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
				.5f);
		scaleUp1.setDuration(500);
		scaleUp1.setStartOffset(1000);

		AnimationSet set1 = new AnimationSet(false);
		set1.addAnimation(scaleUp1);
		set1.addAnimation(trans1);

		orb1.startAnimation(set1);

		//Animate Orb2
		ScaleAnimation scaleDown2 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
				Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
				.5f);
		scaleDown2.setDuration(500);
		scaleDown2.setInterpolator(new AnticipateOvershootInterpolator());

		TranslateAnimation trans2 = new TranslateAnimation(0, orb1.getLeft()
				- orb2.getLeft(), 0, orb1.getTop() - orb2.getTop());
		trans2.setDuration(500);
		trans2.setStartOffset(500);

		ScaleAnimation scaleUp2 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
				Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
				.5f);
		scaleUp2.setDuration(500);
		scaleUp2.setStartOffset(1000);

		AnimationSet set2 = new AnimationSet(false);
		set2.addAnimation(scaleDown2);
		set2.addAnimation(scaleUp2);
		set2.addAnimation(trans2);

		set2.setAnimationListener(new RunAfter() {

			@Override
			public void onAnimationEnd(Animation animation) {
				//swap orb's type
				int t = orb1.getType();
				orb1.setType(orb2.getType());
				orb2.setType(t);
				requestLayout();
				checkMatches();
			}
		});
		orb2.startAnimation(set2);
	}
	
	protected void checkMatches() {
		final Set<OrbView> matchingRowOrbs = new HashSet<GameView.OrbView>();
		//检测具有相同type的行
		for(int i=0; i<GRID_NUM; i++) {
			boolean isSame = true;
			for(int j=1; j<GRID_NUM; j++) {
				if(orbs[i][j].getType() != orbs[i][0].getType()) {
					isSame = false;
					break;
				}
			}
			if(isSame)
				matchingRowOrbs.addAll(Arrays.asList(orbs[i]));
		}
		final Set<OrbView> matchingColOrbs = new HashSet<GameView.OrbView>();
		//检测具有相同type的列
		for(int j=0; j<GRID_NUM; j++) {
			boolean isSame = true;
			for(int i=1; i<GRID_NUM; i++) {
				if(orbs[i][j].getType() != orbs[0][j].getType()) {
					isSame = false;
					break;
				}
			}
			if(isSame) {
				for(int i=0; i<GRID_NUM; i++) 
					matchingColOrbs.add(orbs[i][j]);
			}
		}
		matchingColOrbs.removeAll(matchingRowOrbs);
		
//		Log.d("gameview","row size " + matchingRowOrbs.size() + ", col size " + matchingColOrbs.size());
		//there's no more matching orbs, matching is done
		if(matchingRowOrbs.size() == 0 && matchingColOrbs.size() == 0) {
			doneMatching();
			return;
		}
		
		//标识是否已经添加动画监听器。动画数量很多，只需为其中一个动画添加监听器即可。
		Boolean hasAnimationListener = false;
		removeAnimate(matchingRowOrbs, hasAnimationListener, true);
		removeAnimate(matchingColOrbs, hasAnimationListener, false);
	}

	private void removeAnimate(final Set<OrbView> matchingOrbs,
			Boolean hasAnimationListener, boolean isHorizontal) {
		for(OrbView view : matchingOrbs) {
			Animation animation = buildRemoveOrbAnimation(isHorizontal);
			view.startAnimation(animation);
			if(!hasAnimationListener) {
				hasAnimationListener = true;
				animation.setAnimationListener(new RunAfter() {
					
					@Override
					public void onAnimationEnd(Animation animation) {
						regenerateRemovedOrbs(matchingOrbs);
					}
				});
			}
		}
	}
	
	protected void regenerateRemovedOrbs(Set<OrbView> removedOrbs) {
//		Log.d("gameview", "regenerateRemvoedOrbs is executed.");
		for(OrbView view : removedOrbs)
			view.setRandomType();
		requestLayout();
		checkMatches();
	}

	/**
	 * 用于构建水平右移（或垂直下移）的动画。
	 * @param horizontal
	 * 		true 表示水平右移。
	 * 		false 表示垂直下移。
	 * @return
	 */
	private Animation buildRemoveOrbAnimation(boolean horizontal) {
		int size = getWidth();
		ScaleAnimation scaleDown = new ScaleAnimation(1.0f, 0.5f, 1.0f,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleDown.setDuration(500);
		scaleDown.setFillAfter(true);

		TranslateAnimation trans = horizontal ? 
				  new TranslateAnimation(0, size, 0, 0) 
				: new TranslateAnimation(0, 0, 0, size);
		trans.setDuration(500);
		trans.setStartOffset(500);
		trans.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.addAnimation(scaleDown);
		set.addAnimation(trans);
		return set;
	}

	private void doneMatching() {
		requestLayout();
		acceptInput = true;
	}

	private abstract class RunAfter implements AnimationListener{
		@Override
		public void onAnimationRepeat(Animation animation) {
		}
		@Override
		public void onAnimationStart(Animation animation) {
		}
	}
}
