package in.ac.vit.poster;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DummySectionFragment extends Fragment implements OnItemClickListener
{
	public static final String ARG_SECTION_NUMBER = "section_number";
	String[] myAccount={"Notification","Reminder"};
	String[] moreInfo={"About","Privacy Policy","Feedback"};
	String[] Logout={"Chapter Login","All Chapters"};
	ListView lvAccount,lvInfo,lvLogout;
	List<RowItemStatus>rowItems;
	CustomBaseAdapterStatus adapter;
	
	public DummySectionFragment()
	{

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.settings, container, false);
		lvAccount=(ListView)rootView.findViewById(R.id.lvAccount);
		lvInfo=(ListView)rootView.findViewById(R.id.lvInfo);
		lvLogout=(ListView)rootView.findViewById(R.id.lvLogout);
		
		rowItems=new ArrayList<RowItemStatus>();
		for ( int i = 0; i<2; i++) {

			RowItemStatus item= new RowItemStatus(myAccount[i],"arpit");
			rowItems.add(item);
		}
		adapter = new CustomBaseAdapterStatus(getActivity(), rowItems);
		lvAccount.setAdapter(adapter);
		ArrayAdapter<String> adapterInfo = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_list_item_1, android.R.id.text1, moreInfo);
		lvInfo.setAdapter(adapterInfo);
		ArrayAdapter<String> adapterLogout = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_list_item_1, android.R.id.text1, Logout);
		lvLogout.setAdapter(adapterLogout);
		lvLogout.setOnItemClickListener(this);
		return rootView;	
	}


	@SuppressLint("ShowToast")
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		if(position==0){
		Intent i =new Intent(getActivity(),ChapterLogin.class);
		startActivity(i);
		}
		else{
			Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_LONG);
		}
	}
}