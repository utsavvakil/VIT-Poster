package in.ac.vit.poster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Settings extends Activity implements  OnItemClickListener {
	String[] myAccount={"Username","Email"};
	String[] moreInfo={"About","Privacy Policy","Feedback"};
	String[] Logout={"Chapter Login"};
	ListView lvAccount,lvInfo,lvLogout;
	List<RowItemStatus>rowItems;
	CustomBaseAdapterStatus adapter;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.settings);
	lvAccount=(ListView)findViewById(R.id.lvAccount);
	lvInfo=(ListView)findViewById(R.id.lvInfo);
	lvLogout=(ListView)findViewById(R.id.lvLogout);
	
	rowItems=new ArrayList<RowItemStatus>();
	for ( int i = 0; i<2; i++) {

		RowItemStatus item= new RowItemStatus(myAccount[i],"arpit");
		rowItems.add(item);
	}
	adapter = new CustomBaseAdapterStatus(this, rowItems);
	lvAccount.setAdapter(adapter);
	ArrayAdapter<String> adapterInfo = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, moreInfo);
	lvInfo.setAdapter(adapterInfo);
	ArrayAdapter<String> adapterLogout = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, Logout);
	lvLogout.setAdapter(adapterLogout);
	lvLogout.setOnItemClickListener(this);
}



@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	Intent i =new Intent(getApplicationContext(),ChapterLogin.class);
	startActivity(i);
}
}
