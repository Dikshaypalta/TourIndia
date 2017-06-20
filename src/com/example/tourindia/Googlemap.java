package com.example.tourindia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
 
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
 
public class Googlemap extends FragmentActivity {
	    GoogleMap map;
	    ArrayList<LatLng> markerPoints;
	    LatLng latLng,dest;
	    String location;
	    Button route,me,destin,distance;
	   public LatLng getLocationFromAddress(String strAddress) {

	        Geocoder coder = new Geocoder(getApplicationContext());
	        List<Address> address;
	        LatLng p1 = null;

	        try {
	            address = coder.getFromLocationName(strAddress, 5);
	            if (address == null) {
	                return null;
	            }
	            Address location = address.get(0);
	            location.getLatitude();
	            location.getLongitude();

	            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

	        } catch (Exception ex) {

	            ex.printStackTrace();
	        }

	        return p1;
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.googlemap_design);
	        Bundle extra=getIntent().getExtras();
	        location=extra.getString("gpsname");
	        dest=getLocationFromAddress(location);


	        // Initializing
	        markerPoints = new ArrayList<LatLng>();
	        // Getting reference to SupportMapFragment of the activity_main
	        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
	     // Getting Map for the SupportMapFragment
	        map = fm.getMap();
	        // Getting reference to Button
	        route = (Button)findViewById(R.id.b1);
	        me = (Button)findViewById(R.id.b2);
	        destin = (Button)findViewById(R.id.b3);
	        distance = (Button)findViewById(R.id.b4);
	        route.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					 if(event.getAction() == MotionEvent.ACTION_DOWN) {
				         route.setBackgroundResource(R.drawable.yellow_color);
						 //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
				        } else if (event.getAction() == MotionEvent.ACTION_UP) {
				        	//Toast.makeText(getApplicationContext(), "bye", Toast.LENGTH_LONG).show();
				        	   route.setBackgroundResource(R.drawable.gold_color);
				        }
					return false;
				}
			});
             me.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					 if(event.getAction() == MotionEvent.ACTION_DOWN) {
				         route.setBackgroundResource(R.drawable.yellow_color);
						 //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
				        } else if (event.getAction() == MotionEvent.ACTION_UP) {
				        	//Toast.makeText(getApplicationContext(), "bye", Toast.LENGTH_LONG).show();
				        	   route.setBackgroundResource(R.drawable.gold_color);
				        }
					return false;
				}
			});
           destin.setOnTouchListener(new OnTouchListener() {
	
     	      @Override
	          public boolean onTouch(View v, MotionEvent event) {
		      if(event.getAction() == MotionEvent.ACTION_DOWN) {
	           route.setBackgroundResource(R.drawable.yellow_color);
			   //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
	           } else if (event.getAction() == MotionEvent.ACTION_UP) {
	        	//Toast.makeText(getApplicationContext(), "bye", Toast.LENGTH_LONG).show();
	        	   route.setBackgroundResource(R.drawable.gold_color);
	        }
		   return false;
	   }
        });
             distance.setOnTouchListener(new OnTouchListener() {
	
	          @Override
	          public boolean onTouch(View v, MotionEvent event) {
		      if(event.getAction() == MotionEvent.ACTION_DOWN) {
	          route.setBackgroundResource(R.drawable.yellow_color);
			  //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
	          } else if (event.getAction() == MotionEvent.ACTION_UP) {
	        	//Toast.makeText(getApplicationContext(), "bye", Toast.LENGTH_LONG).show();
	        	   route.setBackgroundResource(R.drawable.gold_color);
	        }
		return false;
	}
});

	        // Enable MyLocation Button in the Map
	        map.setMyLocationEnabled(true);
	        LocationManager locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
	        Criteria criteria=new Criteria();
	        String provider=locationManager.getBestProvider(criteria, true);
	        Location myLocation=locationManager.getLastKnownLocation(provider);
	        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	        double latitude=myLocation.getLatitude();
	        double longitude=myLocation.getLongitude();
	        latLng=new LatLng(latitude, longitude);
	        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	        for(int i=0;i<2;i++){
	         if(markerPoints.size()==0){ 
	         map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("I am here")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
	      	 markerPoints.add(latLng);
	          }
	         else if(markerPoints.size()==1){
	         markerPoints.add(dest);
	         map.addMarker(new MarkerOptions().position(new LatLng(dest.latitude, dest.longitude)).title(location)
	         .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
	            }	            
	       	}
	        // Click event handler for Button btn_draw
	        route.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                // Checks, whether start and end locations are captured
	                if(markerPoints.size() >= 2){
	                    LatLng origin = markerPoints.get(0);
	                    LatLng dest = markerPoints.get(1);
	 
	                    // Getting URL to the Google Directions API
	                    String url = getDirectionsUrl(origin, dest);
	 
	                    DownloadTask downloadTask = new DownloadTask();
	 
	                    // Start downloading json data from Google Directions API
	                    downloadTask.execute(url);
	                }
	            }
	        });
	    }
	 
	    private String getDirectionsUrl(LatLng origin,LatLng dest){
	 
	        // Origin of route
	        String str_origin = "origin="+origin.latitude+","+origin.longitude;
	 
	        // Destination of route
	        String str_dest = "destination="+dest.latitude+","+dest.longitude;
	 
	        // Sensor enabled
	        String sensor = "sensor=false";
	 
	        // Waypoints
	        String waypoints = "";
	        for(int i=2;i<markerPoints.size();i++){
	            LatLng point  = (LatLng) markerPoints.get(i);
	            if(i==2)
	                waypoints = "waypoints=";
	            waypoints += point.latitude + "," + point.longitude + "|";
	        }
	 
	        // Building the parameters to the web service
	        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+waypoints;
	 
	        // Output format
	        String output = "json";
	 
	        // Building the url to the web service
	        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
	 
	        return url;
	    }
	  
    /** A method to download json data from url*/ 
     private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb  = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
 
    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{
 
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
 
            // For storing data from web service
 
            String data = "";
 
            try{
                // Fetching the data from web service
                 data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
 
           ParserTask parserTask = new ParserTask();
 
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
 
    /** A class to parse the Google Places in JSON format*/
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
 
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
 
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;
 
            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
 
                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
 
        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
 
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
 
            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
 
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
 
                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
 
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
 
                    points.add(position);
                }
 
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
            }
 
             // Drawing polyline in the Google Map for the i-th route
             map.addPolyline(lineOptions);
         }
    }
   public void zoomMe(View v){
	     map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	     map.animateCamera(CameraUpdateFactory.zoomTo(10));
   }
   public void zoomDest(View v){
	     map.moveCamera(CameraUpdateFactory.newLatLng(dest));
	     map.animateCamera(CameraUpdateFactory.zoomTo(10));
   }
   public void distancecal(View v){
	   float distance=getDistanceOnRoad(latLng,dest);
	   Toast.makeText(getApplicationContext(), "Distance is: "+distance+" Km", Toast.LENGTH_LONG).show();
   }
   public float getDistanceOnRoad(LatLng StartP, LatLng EndP) {
	    int Radius = 6371;// radius of earth in Km
	    double lat1 = StartP.latitude;
	    double lat2 = EndP.latitude;
	    double lon1 = StartP.longitude;
	    double lon2 = EndP.longitude;
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	            + Math.cos(Math.toRadians(lat1))
	            * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
	            * Math.sin(dLon / 2);
	    float c = (float) (2 * Math.asin(Math.sqrt(a)));
	    double valueResult = Radius * c;
	    double km = valueResult / 1;
	    DecimalFormat newFormat = new DecimalFormat("####");
	    int kmInDec = Integer.valueOf(newFormat.format(km));
	    double meter = valueResult % 1000;
	    int meterInDec = Integer.valueOf(newFormat.format(meter));
	    Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
	            + " Meter   " + meterInDec);

	    return Radius * c;
	}
}