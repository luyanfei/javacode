package cn.jhc.restaurant;

import android.app.Application;

public class RestaurantFinderApplication extends Application {
	
	private Review currentReview;
	private String reviewCriteriaCuisine;
	private String reviewCriteriaLocation;

	public String getReviewCriteriaCuisine() {
		return reviewCriteriaCuisine;
	}

	public void setReviewCriteriaCuisine(String reviewCriteriaCuisine) {
		this.reviewCriteriaCuisine = reviewCriteriaCuisine;
	}

	public String getReviewCriteriaLocation() {
		return reviewCriteriaLocation;
	}

	public void setReviewCriteriaLocation(String reviewCriteriaLocation) {
		this.reviewCriteriaLocation = reviewCriteriaLocation;
	}

	public Review getCurrentReview() {
		return currentReview;
	}

	public void setCurrentReview(Review currentReview) {
		this.currentReview = currentReview;
	}


}
