package cn.jhc.restaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ReviewCriteria extends Activity {

	private static final int MENU_GET_REVIEWS = Menu.FIRST;
	private Spinner cuisine;
	private Button grabReviews;
	private EditText location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_criteria);
		location = (EditText) findViewById(R.id.location);
		cuisine = (Spinner) findViewById(R.id.cuisine);
		grabReviews = (Button) findViewById(R.id.get_reviews_button);
		ArrayAdapter<String> cuisines = new ArrayAdapter<String>(this,
				R.layout.spinner_view, getResources().getStringArray(
						R.array.cuisines));
		cuisines.setDropDownViewResource(R.layout.spinner_view_dropdown);
		cuisine.setAdapter(cuisines);
		grabReviews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				handleGetReviews();
			}
		});
	}

	protected void handleGetReviews() {
		if ((location.getText() == null)
				|| location.getText().toString().equals("")) {
			new AlertDialog.Builder(this)
					.setTitle(R.string.alert_label)
					.setMessage(R.string.location_not_supplied_message)
					.setPositiveButton(
							"Continue",
							new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int arg1) {
									// Just close alert.
								}
							}).show();
			return;
		}
		RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
		application.setReviewCriteriaCuisine(cuisine.getSelectedItem()
				.toString());
		application.setReviewCriteriaLocation(location.getText().toString());
		Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_LIST);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_GET_REVIEWS, 0, R.string.menu_get_reviews).setIcon(
				android.R.drawable.ic_menu_more);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case MENU_GET_REVIEWS:
			handleGetReviews();
			return true;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
