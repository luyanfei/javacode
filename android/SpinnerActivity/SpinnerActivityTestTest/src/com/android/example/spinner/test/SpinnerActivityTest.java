package com.android.example.spinner.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.example.spinner.R;
import com.android.example.spinner.SpinnerActivity;

public class SpinnerActivityTest extends
		ActivityInstrumentationTestCase2<SpinnerActivity> {

	public static final int ADAPTER_COUNT = 9;
	public static final int INITIAL_POSITION = 0;
	public static final int TEST_POSITION = 5;

	private SpinnerActivity activity;
	private Spinner spinner;
	private SpinnerAdapter data;

	private String mSelection;
	private int mPos;

	public SpinnerActivityTest() {
		super(SpinnerActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity = getActivity();
		spinner = (Spinner) activity.findViewById(R.id.Spinner01);
		data = spinner.getAdapter();
	}

	public void testPreCondition() {
		assertTrue(spinner.getOnItemSelectedListener() != null);
		assertTrue(data != null);
		assertEquals(data.getCount(), ADAPTER_COUNT);
	}

	public void testSpinnerUI() {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				spinner.requestFocus();
				spinner.setSelection(INITIAL_POSITION);
			}
		});

		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		for (int i = 0; i < TEST_POSITION; i++)
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

		mPos = spinner.getSelectedItemPosition();
		mSelection = (String) spinner.getItemAtPosition(mPos);
		TextView resultView = (TextView) activity
				.findViewById(R.id.SpinnerResult);
		String resultText = (String) resultView.getText();
		assertEquals(resultText, mSelection);
	}

	public static final int TEST_STATE_DESTROY_POSITION = 2;
	public static final String TEST_STATE_DESTROY_SELECTION = "Earth";

	public void testStateDestroy() {
		activity.setSpinnerPosition(TEST_STATE_DESTROY_POSITION);
		activity.setSpinnerSelection(TEST_STATE_DESTROY_SELECTION);
		activity.finish();

		activity = getActivity();
		int currentPosition = activity.getSpinnerPosition();
		String currentSelection = activity.getSpinnerSelection();

		assertEquals(TEST_STATE_DESTROY_POSITION, currentPosition);
		assertEquals(TEST_STATE_DESTROY_SELECTION, currentSelection);
	}

	public static final int TEST_STATE_PAUSE_POSITION = 4;
	public static final String TEST_STATE_PAUSE_SELECTION = "Jupiter";

	@UiThreadTest
	public void testStatePause() {
		Instrumentation instrumentation = getInstrumentation();
		activity.setSpinnerPosition(TEST_STATE_PAUSE_POSITION);
		activity.setSpinnerSelection(TEST_STATE_PAUSE_SELECTION);
		instrumentation.callActivityOnPause(activity);
		
		activity.setSpinnerPosition(0);
		activity.setSpinnerSelection("");
		instrumentation.callActivityOnResume(activity);
		
		int currentPosition = activity.getSpinnerPosition();
		String currentSelection = activity.getSpinnerSelection();
		
		assertEquals(TEST_STATE_PAUSE_POSITION, currentPosition);
		assertEquals(TEST_STATE_PAUSE_SELECTION, currentSelection);
		
	}
}
