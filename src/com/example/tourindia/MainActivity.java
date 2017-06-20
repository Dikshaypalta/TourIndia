package com.example.tourindia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity{
 LinearLayout ll;

 ListView state;
 ArrayAdapter<String> list;
 ArrayList<String>AL;
 ArrayList<String>AL1;
  String s[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
        String name[]={"Punjab","Delhi","Haryana","Himachal Pradesh","ddd",
        	"Andhra Pradesh","Maharashtra","Rajasthan","Karnataka","Uttar Pradesh","Goa","Gujrat","Kerala","Bihar","Meghalaya","Sikkim","Assam","Chhattisgarh","Uttarakhand","Manipur"};
        final String Punjab[]={"Golden Temple","Jallianwala Bagh","Wagah Border","Rock Garden","Sukhna Lake","Punjab Haveli"};
        final String Delhi[]={"Qutab Minar","Red Fort","Rashtrapati Bhavan","India Gate","Humayun Tomb","Lotus Temple"}; 
        final String Haryana[]={"Sheesh Mahal","Brahma Sarovar","Kingdom of Dreams","Sri Krishna Museum","Kalpana Chawla Planetarium"};
        final String AP[]={"Golkonda","Visakha Museum","Kanaka Durga Temple","Veerabhadra Temple","Hussain Sagar","tirumala venkateswara temple"};
        final String UP[]={"Taj Mahal","Jama Masjid","Allen Forest Zoo","Agra Fort"};
        final String HP[]={"Bhagsunag Falls","Bhrigu Lake","Chintpurni Temple","Kalka Shimla Railway","Ridge Shimla","Rohtang Pass","Hadimba Temple"};
        final String Rajasthan[]={"Amer Fort","Ghanta Ghar","Jaiselmer Fort","Chittor Garh","Jal Mahal"};
        final String Maharashtra[]={"Elephant Caves","Chawpatty Beach","Essel World","Gateway Of India","Karla Caves"};
        final String Karnataka[]={"Bahubali","Bidar Fort","Kudremukh","Mullayanagiri","Mysore Zoo","Ranganathittu Bird Sanctuary"};
        final String Bihar[]={"Mahabodhi Temple","Mahavir Mandir","Patan Devi","Patna Museum","Vishnupad Mandir"};
        final String Kerala[]={"Cherai Beach","Chettikulangara Devi Temple","Edakkal Caves","Jawaharlal Nehru Stadium","Thiruvananthapuram Zoo","Veegaland"};
       	final String Gujrat[]={"Baroda Museum and Picture Gallery","Dwarkadish Temple","Kashtabhanjan Hanuman Mandir","Statue Of Unity"};
     	final String Goa[]={"Casino Royal Goa","Chapora Beach","Dudhsagar Falls","Goa State Museum"};
     	final String Meghalaya[]={"Chherapunji","Dawki","Mawsynram","Shillong","Umium Lake"};
        final String Sikkim[]={"Ganesh Tok","Hanuman Tok","Nathula Pass","Rumtek Monastery","Gurudongmar Lake"};
        final String Assam[]={"Assam State Museum","Kamakhya Temple","Kaziranga National Park","Umananda Temple"};
        final String Manipur[]={"Imphalcity","Keibul Lamjao National Park","Loktak Lake"};
        final String Uttarakhand[]={"Badrinath Temple","Har Ki Pauri","Hemkund Sahib","Nainital Lake","Neelkanth Mahadev Temple","Ram Jhula"};
        final String Chhattisgarh[]={"Bhoramdeo Temple","Chitrakot Waterfall","Gadiya Mountain","Maitri Bhagh","Rajpuri Waterfall"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   
        ll=(LinearLayout)findViewById(R.id.LL);
        ll.setBackgroundColor(Color.rgb(185, 162, 230));
        AL=new ArrayList<String>(Arrays.asList(name));
        Collections.sort(AL);
        state=(ListView)findViewById(R.id.listView1);
        list=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, AL);
        state.setAdapter(list);
        state.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posision,
					long arg3) {
				// TODO Auto-generated method stub	
				String place=state.getItemAtPosition(posision).toString();
				place=place.replaceAll(" ","");
				Intent i=new Intent(MainActivity.this,Places.class);
				if(place.equals("Punjab"))
				AL1=new ArrayList<String>(Arrays.asList(Punjab));
				else if(place.equals("Delhi"))
				AL1=new ArrayList<String>(Arrays.asList(Delhi));
				else if(place.equals("Haryana"))
				AL1=new ArrayList<String>(Arrays.asList(Haryana));
				else if(place.equals("AndhraPradesh"))
				AL1=new ArrayList<String>(Arrays.asList(AP));
				else if(place.equals("UttarPradesh"))
					AL1=new ArrayList<String>(Arrays.asList(UP));
				else if(place.equals("Maharashtra"))
					AL1=new ArrayList<String>(Arrays.asList(Maharashtra));
				else if(place.equals("HimachalPradesh"))
					AL1=new ArrayList<String>(Arrays.asList(HP));
				else if(place.equals("Rajasthan"))
					AL1=new ArrayList<String>(Arrays.asList(Rajasthan));
				else if(place.equals("Karnataka"))
					AL1=new ArrayList<String>(Arrays.asList(Karnataka));
				else if(place.equals("Kerala"))
					AL1=new ArrayList<String>(Arrays.asList(Kerala));
				else if(place.equals("Bihar"))
					AL1=new ArrayList<String>(Arrays.asList(Bihar));
				else if(place.equals("Gujrat"))
					AL1=new ArrayList<String>(Arrays.asList(Gujrat));
				else if(place.equals("Goa"))
					AL1=new ArrayList<String>(Arrays.asList(Goa));
				else if(place.equals("Meghalaya"))
					AL1=new ArrayList<String>(Arrays.asList(Meghalaya));
				else if(place.equals("Sikkim"))
					AL1=new ArrayList<String>(Arrays.asList(Sikkim));
				else if(place.equals("Assam"))
					AL1=new ArrayList<String>(Arrays.asList(Assam));
				else if(place.equals("Uttarakhand"))
					AL1=new ArrayList<String>(Arrays.asList(Uttarakhand));
				else if(place.equals("Manipur"))
					AL1=new ArrayList<String>(Arrays.asList(Manipur));
				else if(place.equals("Chhattisgarh"))
					AL1=new ArrayList<String>(Arrays.asList(Chhattisgarh));
				
				else
					AL1=new ArrayList<String>(Arrays.asList(AP));
				i.putStringArrayListExtra("AL", AL1);
				i.putExtra("place", place);
				startActivity(i);			
			}
		});
    }
  
}
