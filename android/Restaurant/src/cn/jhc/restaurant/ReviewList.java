package cn.jhc.restaurant;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ReviewList extends ListActivity {
	private static final int MENU_CHANGE_CRITERIA = Menu.FIRST + 1;
	private static final int MENU_GET_NEXT_PAGE = Menu.FIRST;
	private static final int NUM_RESULTS_PER_PAGE = 8;
	private TextView empty;
	private ProgressDialog progressDialog;
	private ReviewAdapter reviewAdapter;
	private List<Review> reviews;
	private final Handler handler = new Handler() {
		public void handleMessage(final Message msg) {
			progressDialog.dismiss();
			if ((reviews == null) || (reviews.size() == 0)) {
				empty.setText("No Data");
			} else {
				reviewAdapter = new ReviewAdapter(ReviewList.this, reviews);
				setListAdapter(reviewAdapter);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_list);
		empty = (TextView) findViewById(R.id.empty);

		ListView listView = getListView();
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setEmptyView(empty);

	}

	@Override
	protected void onResume() {
		super.onResume();
		RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
		String criteriaCuisine = application.getReviewCriteriaCuisine();
		String criteriaLocation = application.getReviewCriteriaLocation();
		int startFrom = getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1);

		loadReviews(criteriaLocation, criteriaCuisine, startFrom);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review_list, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case MENU_GET_NEXT_PAGE:
			intent = new Intent(Constants.INTENT_ACTION_VIEW_LIST);
			intent.putExtra(Constants.STARTFROM_EXTRA,
					getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1)
							+ ReviewList.NUM_RESULTS_PER_PAGE);
			startActivity(intent);
			return true;
		case MENU_CHANGE_CRITERIA:
			intent = new Intent(this, ReviewCriteria.class);
			startActivity(intent);
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
		application.setCurrentReview(reviews.get(position));
		Intent intent = new Intent(Constants.INTENT_ACTION_VIEW_DETAIL);
		intent.putExtra(Constants.STARTFROM_EXTRA,
				getIntent().getIntExtra(Constants.STARTFROM_EXTRA, 1));
		startActivity(intent);
	}

	private void loadReviews(String location, String cuisine, int startFrom) {
		final ReviewFetcher rf = new ReviewFetcher(location, cuisine, "ALL",
				startFrom, ReviewList.NUM_RESULTS_PER_PAGE);

		progressDialog = ProgressDialog.show(this, " Working...",
				" Retrieving reviews", true, false);

		new Thread() {

			public void run() {

				reviews = rf.getReviews();
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

}
