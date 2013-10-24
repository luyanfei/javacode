package cn.jhc.restaurant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewDetail extends Activity {
	private static final int MENU_CALL_REVIEW = Menu.FIRST + 2;
	private static final int MENU_MAP_REVIEW = Menu.FIRST + 1;
	private static final int MENU_WEB_REVIEW = Menu.FIRST;
	private String imageLink;
	private String link;
	private TextView location;
	private TextView name;
	private TextView phone;
	private TextView rating;
	private TextView review;
	private ImageView reviewImage;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if ((imageLink != null) && !imageLink.equals("")) {
				try {
					URL url = new URL(imageLink);
					URLConnection conn = url.openConnection();
					conn.connect();
					BufferedInputStream bis = new BufferedInputStream(
							conn.getInputStream());
					Bitmap bm = BitmapFactory.decodeStream(bis);
					bis.close();
					reviewImage.setImageBitmap(bm);
				} catch (IOException e) {
					// log and or handle here
				}
			} else {
				reviewImage.setImageResource(R.drawable.no_review_image);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_detail);
		name = (TextView) findViewById(R.id.name_detail);
		rating = (TextView) findViewById(R.id.rating_detail);
		location = (TextView) findViewById(R.id.location_detail);
		phone = (TextView) findViewById(R.id.phone_detail);
		review = (TextView) findViewById(R.id.review_detail);
		reviewImage = (ImageView) findViewById(R.id.review_image);
		RestaurantFinderApplication application = (RestaurantFinderApplication) getApplication();
		Review currentReview = application.getCurrentReview();
		link = currentReview.link;
		imageLink = currentReview.imageLink;
		name.setText(currentReview.name);
		rating.setText(currentReview.rating);
		location.setText(currentReview.location);
		review.setText(currentReview.content);
		if ((currentReview.phone != null) && !currentReview.phone.equals("")) {
			phone.setText(currentReview.phone);
		} else {
			phone.setText("NA");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ReviewDetail.MENU_WEB_REVIEW, 0, R.string.menu_web_review)
				.setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(0, ReviewDetail.MENU_MAP_REVIEW, 1, R.string.menu_map_review)
				.setIcon(android.R.drawable.ic_menu_mapmode);
		menu.add(0, ReviewDetail.MENU_CALL_REVIEW, 2, R.string.menu_call_review)
				.setIcon(android.R.drawable.ic_menu_call);

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case MENU_WEB_REVIEW:
			if ((link != null) && !link.equals("")) {
				intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
				startActivity(intent);
			} else {
				new AlertDialog.Builder(this)
						.setTitle(
								getResources().getString(R.string.alert_label))
						.setMessage(R.string.no_link_message)
						.setPositiveButton("Continue", new OnClickListener() {
							public void onClick(DialogInterface dialog, int arg1) {
							}
						}).show();
			}
			return true;
		case MENU_MAP_REVIEW:
			if ((location.getText() != null) && !location.getText().equals("")) {
				intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" +
						location.getText().toString()));
				startActivity(intent);
			} else {
				new AlertDialog.Builder(this)
						.setTitle(
								getResources().getString(R.string.alert_label))
						.setMessage(R.string.no_location_message)
						.setPositiveButton("Continue", new OnClickListener() {
							public void onClick(DialogInterface dialog, int arg1) {
							}
						}).show();
			}
			return true;
		case MENU_CALL_REVIEW:
			if ((phone.getText() != null) && !phone.getText().equals("")
					&& !phone.getText().equals("NA")) {
				String phoneString = parsePhone(phone.getText().toString());
				intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ phoneString));
				startActivity(intent);
			} else {
				new AlertDialog.Builder(this)
						.setTitle(
								getResources().getString(R.string.alert_label))
						.setMessage(R.string.no_phone_message)
						.setPositiveButton("Continue", new OnClickListener() {
							public void onClick(DialogInterface dialog, int arg1) {
							}
						}).show();
			}
			return true;
		}
		return super.onMenuItemSelected(featureId, item);

	}

	private String parsePhone(final String phone) {
		String parsed = phone;
		parsed = parsed.replaceAll("\\D", "");
		parsed = parsed.replaceAll("\\s", "");
		return parsed.trim();
	}

}
