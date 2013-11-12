package cn.jhc.mapapp;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class MapActivity extends Activity {

	private class SearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
		}

		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int error) {
			if (error == MKEvent.ERROR_RESULT_NOT_FOUND) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG)
						.show();
				return;
			} else if (error != 0 || result == null) {
				Toast.makeText(MapActivity.this, "搜索出错啦..", Toast.LENGTH_LONG)
						.show();
				return;
			}
			
			PoiOverlay poiOverlay = new PoiOverlay(MapActivity.this, mapView);
			poiOverlay.setData(result.getAllPoi());
			mapView.getOverlays().clear();
			mapView.getOverlays().add(poiOverlay);
			mapView.refresh();
			
			//动画定位到第一个Poi点
			for(MKPoiInfo info : result.getAllPoi()) {
				if(info.pt!=null) {
					mapView.getController().animateTo(info.pt);
					break;
				}
			}

		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
		}

	}

	BMapManager mapManager = null;
	MapView mapView = null;

	MKSearch search = null;

	LocationManager locationManager = null;
	LocationProvider locationProvider = null;
	// 金华职业技术学院为中心点
	private static final GeoPoint CENTER = new GeoPoint(
			(int) (29.069427584920 * 1e6), (int) (119.643211130380 * 1e6));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapManager = new BMapManager(getApplication());
		mapManager.init("BF9f664e6ab9166ed20bbab7ebc281e2", null);
		setContentView(R.layout.activity_map);

		mapView = (MapView) findViewById(R.id.bmapsView);
		mapView.setBuiltInZoomControls(true);

		search = new MKSearch();
		search.init(mapManager, new SearchListener());

		Button searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				search.poiSearchNearBy("酒店", CENTER, 5000);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Log.d("mapapp", "GPS Provider is not enabled.");
			return;
		}
		locationProvider = locationManager
				.getProvider(LocationManager.GPS_PROVIDER);
		MapController mapController = mapView.getController();
		mapController.setCenter(CENTER);
		mapController.setZoom(17);
	}

	@Override
	protected void onDestroy() {
		
		mapView.destroy();
		if (mapManager != null) {
			mapManager.destroy();
			mapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		if (mapManager != null) {
			mapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		if (mapManager != null) {
			mapManager.start();
		}
		super.onResume();
	}
}
