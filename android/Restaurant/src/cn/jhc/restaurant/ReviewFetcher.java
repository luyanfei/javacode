package cn.jhc.restaurant;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

public class ReviewFetcher {
	   private static final String CLASSTAG = ReviewFetcher.class.getSimpleName();
	    private static final String QBASE = "http://navitend.com/androidinaction/reviews.php?type=restaurant";
	    private static final String QD_PREFIX = "&description=";
	    private static final String QL_PREFIX = "&location=";
	    private String query;

	    /**
	     * Construct ReviewFetcher with location, description, rating, and paging params.
	     * 
	     * @param location
	     * @param description
	     * @param rating
	     * @param start
	     * @param numResults
	     */
	    public ReviewFetcher(String loc, String description, String rat, int start, int numResults) {

	        Log.v(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG + " location = " + loc + " rating = " + rat + " start = "
	            + start + " numResults = " + numResults);

	        String location = loc;
	        String rating = rat;

	        // urlencode params
	        try {
	            if (location != null) {
	                location = URLEncoder.encode(location, "UTF-8");
	            }
	            if (rating != null) {
	                rating = URLEncoder.encode(rating, "UTF-8");
	            }
	        } catch (UnsupportedEncodingException e1) {
	            e1.printStackTrace();
	        }

	        // build query
	        this.query = ReviewFetcher.QBASE;
	        if ((location != null) && !location.equals("")) {
	            this.query += (ReviewFetcher.QL_PREFIX + location);
	        }
	        if ((description != null) && !description.equals("ANY")) {
	            this.query += (ReviewFetcher.QD_PREFIX + description);
	        }

	        Log.v(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG + " query - " + this.query);
	    }

	    /**
	     * Call Google Base and parse via SAX.
	     * 
	     * @return
	     */
	    public ArrayList<Review> getReviews() {
	        long startTime = System.currentTimeMillis();
	        ArrayList<Review> results = null;

	        try {
	            URL url = new URL(this.query);
	            SAXParserFactory spf = SAXParserFactory.newInstance();
	            SAXParser sp = spf.newSAXParser();
	            XMLReader xr = sp.getXMLReader();

	            ReviewHandler handler = new ReviewHandler();
	            xr.setContentHandler(handler);

	            xr.parse(new InputSource(url.openStream()));
	            // after parsed, get record
	            results = handler.getReviews();
	        } catch (Exception e) {
	            Log.e(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG, e);
	        }
	        long duration = System.currentTimeMillis() - startTime;
	        Log.v(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG + " call and parse duration - " + duration);
	        return results;
	    }

}
