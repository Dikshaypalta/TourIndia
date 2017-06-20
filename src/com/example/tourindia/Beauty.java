package com.example.tourindia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Beauty extends Activity {
	private GridView gv;
	TextView tv;
	Button b,b1;
	WifiManager wifi;
	WebView w;
	String gpsname;
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	private int j=0;
	private GestureDetector detector;
	private static final int SWIPE_MIN_DISTANCE=120;
	private static final int SWIPE_THRESHOLD_VELOCITY=200;
	private int[] thumb=new int[3];
	private ImageView expandedImageView;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beauty_design);
        String s[]=new String[3];
        String text1;
        w=(WebView)findViewById(R.id.wv);
        b=(Button)findViewById(R.id.golink);
        b=(Button)findViewById(R.id.map);
        gv=(GridView)findViewById(R.id.grid_view);
        tv=(TextView)findViewById(R.id.descript);
        tv.setMovementMethod(new ScrollingMovementMethod());
        Bundle extra=getIntent().getExtras();
	    String text=extra.getString("description");
	    gpsname=extra.getString("gpsname");
	    text1=text;
	    text1=text1.replaceAll(" ", "");
	    text1=text1.toLowerCase();
	    for(int i=0;i<3;i++){
	        s[i]=text1+(i+1);	
	        int res = getResources().getIdentifier(s[i], "drawable", this.getPackageName());    
	        thumb[i]=res;  
	    }
	   int resourceId = this.getResources().getIdentifier("@string/"+text1, "string", this.getPackageName());
	   tv.setText(getText(resourceId));
        TabHost th=(TabHost)findViewById(R.id.tabhost);
        th.setup();
       //tv.requestFocus();s
        w.setWebViewClient(new ourViewClient());
        TabSpec specs=th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Description");
        th.addTab(specs);
        specs=th.newTabSpec("tag2");
        specs.setContent(R.id.container);
        specs.setIndicator("Images");

        th.addTab(specs);
        specs=th.newTabSpec("tag3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("TourGuide");
        th.addTab(specs);
        detector=new GestureDetector(this,new SwipeGestureDetector());
        gv=(GridView)findViewById(R.id.grid_view);
        gv.setAdapter(new ImageAdapter(this));
        gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				j=pos;
				zoomImageFromThumb(v,thumb[pos]);
			}
		});
        mShortAnimationDuration =getResources().getInteger(android.R.integer.config_shortAnimTime);
    }
	class ImageAdapter extends BaseAdapter{
		private LayoutInflater layoutInflater;
		public ImageAdapter(Beauty beauty){
			layoutInflater=(LayoutInflater)beauty.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		}
			@Override
			public int getCount(){
				return thumb.length;
			}

			@Override
			public Object getItem(int pos) {
				return pos;
			}

			@Override
			public long getItemId(int pos) {
				// TODO Auto-generated method stub
				return pos;
			}

			@Override
			public View getView(int pos, View convertView, ViewGroup parent) {
				View listitem=convertView;
				int p=pos;
				if(listitem==null){
					listitem=layoutInflater.inflate(R.layout.single_grid_item, null);
				}
				ImageView iv=(ImageView)listitem.findViewById(R.id.thumb);
				iv.setBackgroundResource(thumb[p]);
				return listitem;
			}
		}
			private void zoomImageFromThumb(final View thumbView,int imageResId){
				if(mCurrentAnimator!=null){
					mCurrentAnimator.cancel();
				}
				expandedImageView=(ImageView)findViewById(R.id.expanded_image);
				expandedImageView.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if(detector.onTouchEvent(event)){
							return true;
						}
						else{
						return false;
						}
					}
				});
				expandedImageView.setImageResource(imageResId);
				final Rect startBounds=new Rect();
				final Rect finalBounds=new Rect();
				final Point globalOffset=new Point();
				thumbView.getGlobalVisibleRect(startBounds);
				findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
				startBounds.offset(-globalOffset.x,-globalOffset.y );
				finalBounds.offset(-globalOffset.x,-globalOffset.y );
				float startScale;
				if((float)finalBounds.width()/finalBounds.height()>
				(float)startBounds.width()/startBounds.height()){
				startScale=(float)startBounds.height()/finalBounds.height();	
		        float startWidth=startScale*finalBounds.width();
		        float deltaWidth=(startWidth-startBounds.width())/2;
		        startBounds.left-=deltaWidth;
		        startBounds.right+=deltaWidth;
				}
				else{
		        startScale =(float)startBounds.width()/finalBounds.width();
		        float startHeight=startScale*finalBounds.height();
		        float deltaHeight=(startHeight=startBounds.height())/2;
		        startBounds.top-=deltaHeight;
		        startBounds.bottom+=deltaHeight;
				}
				thumbView.setAlpha(0f);
				expandedImageView.setVisibility(View.VISIBLE);
				expandedImageView.setPivotX(0f);
				expandedImageView.setPivotY(0f);
				AnimatorSet set=new AnimatorSet();
				set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,finalBounds.left))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.Y , startBounds.top,finalBounds.top))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X , startScale,1f))
				.with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y , startScale,1f));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {

					@Override
					public void onAnimationCancel(Animator animation) {
						mCurrentAnimator=null;
					}
					
				});
				set.start();
				mCurrentAnimator=set;
				final float startScaleFinal=startScale;
				expandedImageView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
					if(mCurrentAnimator!=null){
						mCurrentAnimator.cancel();
					}
						AnimatorSet set=new AnimatorSet();
						set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
						.with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
					    .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
					    .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
						set.setDuration(mShortAnimationDuration);
						set.setInterpolator(new DecelerateInterpolator());
						set.addListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								thumbView.setAlpha(1f);
								expandedImageView.setVisibility(View.GONE);
								mCurrentAnimator=null;
								
							}
							@Override
							public void onAnimationCancel(Animator animation) {
								thumbView.setAlpha(1f);
								expandedImageView.setVisibility(View.GONE);
								mCurrentAnimator=null;
							}
							
						});
						set.start(); 
						mCurrentAnimator=set;
					}
				});
			}
			private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener{
				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
						float velocityY) {
				try{
					if(e1.getX()-e2.getX()>SWIPE_MIN_DISTANCE && Math.abs(velocityX)>SWIPE_THRESHOLD_VELOCITY){
						if(thumb.length>j){
							j++;
						}
						if(j<thumb.length){
							expandedImageView.setImageResource(thumb[j]);
							return true;
						}else{
							j=0;
							expandedImageView.setImageResource(thumb[j]);
							return true;
						}
					}
					else if(e2.getX()-e1.getX()>SWIPE_MIN_DISTANCE && Math.abs(velocityX)>SWIPE_THRESHOLD_VELOCITY){
						if(j>0){
							j--;
							expandedImageView.setImageResource(thumb[j]);
							return true;
						}else{
							j=thumb.length-1;
							expandedImageView.setImageResource(thumb[j]);
							return true;
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return false;
				}
			}
			public void web(View v){	
			    boolean x=alert();
			   if(x==true){
				w.loadUrl("http://www.yatra.com");
		    	w.getSettings().setJavaScriptEnabled(true);
		    	
			   }
			   else
				   Toast.makeText(getApplicationContext(),"no internet connection",Toast.LENGTH_LONG).show();
			  }
	     public void tour(View v){
	    	 boolean x=alert();
	    	 if(x==true){
	    	 Intent i=new Intent(Beauty.this,Googlemap.class);
	    	 i.putExtra("gpsname", gpsname);
	         startActivity(i);
	    	 }
	    	 else
	    		  Toast.makeText(getApplicationContext(),"no internet connection",Toast.LENGTH_LONG).show();
	     }
	    public boolean alert(){   
	    	 
	    	/*AlertDialog.Builder builder=new AlertDialog.Builder(this);  
	        builder.setMessage("make sure that you are connected to internet")
	       .setTitle("Checking Connectivity")
	       .setCancelable(false)
	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	     	public void onClick(DialogInterface dialog,int id){*/	    
	     		ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    		    // ARE WE CONNECTED TO THE NET
	    		    if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
	    		    connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
	    		    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
	    		    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
	    		    // MESSAGE TO SCREEN FOR TESTING (IF REQ)
	    		    
	    		    	return true;
	    		    } else if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ){
	    		    return false;
	    		    }
	    		    else{
	                  return false;
	                  }
	    	         
	     	}
	      // });
	    
		//	AlertDialog alert=builder.create();
			//alert.show();
	    	   // }
	 }
