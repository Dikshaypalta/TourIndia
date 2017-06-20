package com.example.tourindia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends Activity{
TextView welcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_design);
		welcome=(TextView)findViewById(R.id.tv1);
		welcome.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));
		Thread t=new Thread(){
			public void run()
			{
				try{
					sleep(1500);
			}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					Intent i=new Intent("com.example.tourindia.MAINACTIVITY");
					startActivity(i);
				}
		}	
	};
	t.start();
}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}

