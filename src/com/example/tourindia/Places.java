package com.example.tourindia;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Places extends Activity{
    LinearLayout ll;
    TextView tv2;
	ArrayList<String>LA;
	ListView lv;
	ArrayAdapter<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places_design);
		tv2=(TextView)findViewById(R.id.textView1);
		ll=(LinearLayout)findViewById(R.id.LL1);
		lv=(ListView)findViewById(R.id .lv1); 
		LA=getIntent().getStringArrayListExtra("AL");
		Bundle extra=getIntent().getExtras();
		final String text=extra.getString("place");
		//Drawable b=Drawable.createFromPath("/TourIndia/res/drawable/punjab");      
		//ll.setBackgroundDrawable(b);
		//ll.setBackgroundColor(android.R.color.white);
		tv2.append(text);
		tv2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD),Typeface.BOLD);
		list=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, LA);
	    lv.setAdapter(list);
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String beauty=lv.getItemAtPosition(arg2).toString();
				String gpsname=text+" "+beauty;
				Intent i=new Intent(getApplicationContext(),Beauty.class);
				i.putExtra("description",beauty);
				i.putExtra("gpsname", gpsname);
				startActivity(i);
			}
	    	
		});
	    	}

}
