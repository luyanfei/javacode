package cn.jhc.restaurant;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReviewAdapter extends BaseAdapter {

	public class ReviewListView extends LinearLayout {

		private TextView name;
		private TextView rating;

		public ReviewListView(Context context, String itemName,
				String itemRating) {
			super(context);
			setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			params.setMargins(5, 3, 5, 0);
			name = new TextView(context);
			name.setText(itemName);
			name.setTextSize(16f);
			name.setTextColor(Color.WHITE);
			addView(name, params);
			rating = new TextView(context);
			rating.setText(itemRating);
			rating.setTextSize(16f);
			rating.setTextColor(Color.GRAY);

			addView(rating, params);

		}

		public void setName(String itemName) {
			name.setText(itemName);
		}

		public void setRating(String itemRating) {
			rating.setText(itemRating);
		}

	}

	private final Context context;
	private final List<Review> reviews;

	public ReviewAdapter(Context context, List<Review> reviews) {
		this.context = context;
		this.reviews = reviews;
	}

	@Override
	public int getCount() {
		return reviews.size();
	}

	@Override
	public Object getItem(int position) {
		return reviews.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Review review = reviews.get(position);
		if (convertView == null || !(convertView instanceof ReviewListView)) {
			return new ReviewListView(context, review.name, review.rating);
		}
		ReviewListView view = (ReviewListView) convertView;
		view.setName(review.name);
		view.setRating(review.rating);
		return view;
	}

}
