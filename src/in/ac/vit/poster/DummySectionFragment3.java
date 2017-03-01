package in.ac.vit.poster;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DummySectionFragment3 extends Fragment implements OnItemClickListener
{
	public static final String ARG_SECTION_NUMBER = "section_number";
	private boolean undo = false;
	private CaldroidFragment caldroidFragment;
	DatabaseHandlerCalender db;
	ListView llCal;
	Date date;
	Calendar cal;
	int i=0;
	
	public int[] images_going={R.drawable.event_1,R.drawable.event_4,R.drawable.event_6};
	DatabaseHandlerChapters dbGoing;
	int [] going;
	public String[] event_id;
	public String[] event_location;
	public String[] time;
	public String[] description;
	public String[] event_name;
	public String[] cord1_name;
	public String[] cord1_contact;
	public String[] date_event;
	public String[] chapter_name;
	public String[] thumb, cover;
	private CaldroidFragment dialogCaldroidFragment;

	private void setCustomResourceForDates(Date date) {
		

		// Min date is last 7 day
		
		Date blueDate = date;
		
		System.out.println("Date"+date);
		Toast.makeText(getActivity(), "Date "+Calendar.DATE,Toast.LENGTH_SHORT).show();
		

		// Max date is next 7 days
		

		if (caldroidFragment != null) {
			caldroidFragment.setBackgroundResourceForDate(R.color.blue,
					blueDate);
			
			caldroidFragment.setTextColorForDate(R.color.white, blueDate);
			
		}
	}
	public DummySectionFragment3()
	{

	}


	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.calendar, container, false);
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

		
		caldroidFragment = new CaldroidFragment();
		cal = Calendar.getInstance();
		
		db = new DatabaseHandlerCalender(getActivity());
		llCal=(ListView)rootView.findViewById(R.id.llCalEvents);
		llCal.setOnItemClickListener(this);
		List<Events> event_1 =db.getAllEvents();
		
		System.out.println("fetching data1");
		String[] dateStr = new String[event_1.size()];
		
		i=0;
		for (Events cn : event_1)
				
		{
			
			dateStr[i]=""+cn.getDate();
			
			System.out.println("the fetching date is "+dateStr[i]);
			
			//Toast.makeText(getActivity(), dateStr[i]+" "+dateStr[i].length(), Toast.LENGTH_SHORT).show();
			
			
			 String[] dateSplit = dateStr[0].split("-");
			System.out.println("Split Date "+dateSplit[0]+"-"+dateSplit[1]+"-"+dateSplit[2]);
			System.out.println("fetching data2");
			try {
				 
				//date = format1.parse(dateSplit[0]+"-"+dateSplit[1]+"-"+dateSplit[2]);
				date=format1.parse(dateStr[i]);
				System.out.println("Date"+date);
				//Toast.makeText(getActivity(), "Date "+date,Toast.LENGTH_SHORT).show();
				
				
				System.out.println(formatter.format(date));
				if(date==null){
					
				}else{
				setCustomResourceForDates(date);
				}
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
		i++;
		}
	//	Toast.makeText(getActivity(), dateStr[1], Toast.LENGTH_SHORT).show();
		
		
		
		
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

			caldroidFragment.setArguments(args);
		}
		
		// Attach to the activity
		FragmentTransaction t = getFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				Toast.makeText(getActivity(), formatter.format(date),
						Toast.LENGTH_SHORT).show();
				RowItem item;
				List<RowItem> rowItems = new ArrayList<RowItem>();
				
				List<Events> event_1=db.getAllDateEvents(format1.format(date));
				System.out.println("date and event size"+format1.format(date));
				event_id = new String[event_1.size()];
				event_location = new String[event_1.size()];
				time = new String[event_1.size()];
				description = new String[event_1.size()];
				cord1_name = new String[event_1.size()];
				cord1_contact = new String[event_1.size()];
				date_event = new String[event_1.size()];
				event_name = new String[event_1.size()];
				chapter_name=new String[event_1.size()];
				thumb=new String[event_1.size()];
				cover=new String[event_1.size()];
				
				int k=0;
				for (Events cn : event_1)
				{
					
					System.out.println("this is k loop"+k);
					event_id[k]=""+cn.getEventId();
					event_name[k]=""+cn.getEventName();
					
					time[k]=""+cn.getTime();
					event_location[k]=""+cn.getVenue();
					description[k]=""+cn.getDescription();
					cord1_name[k]=""+cn.getCord_name();
					cord1_contact[k]=""+cn.getCord_no();
					date_event[k] = ""+cn.getDate();
					chapter_name[k]=""+cn.getChapterName();
					thumb[k]=""+cn.getThumb();
					cover[k]=""+cn.getCover();
					k++;
					
					
					
				}
				for ( i = 0; i<event_1.size(); i++) {
					System.out.println("event name"+event_name[i]);
					item = new RowItem( event_name[i],time[i],
							event_location[i],"","#CCCCCC",1,images_going[i],thumb[i]);
					rowItems.add(item);
					
				}
				CustomAdapterCalendarEvents adapter = new CustomAdapterCalendarEvents(getActivity(), rowItems,null);
				llCal.setAdapter(adapter);
				

			}

			@Override
			public void onChangeMonth(int month, int year) {
				String text = "month: " + month + " year: " + year;
				Toast.makeText(getActivity(), text,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLongClickDate(Date date, View view) {
				Toast.makeText(getActivity(),
						"Long click " + formatter.format(date),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCaldroidViewCreated() {
				if (caldroidFragment.getLeftArrowButton() != null) {
					Toast.makeText(getActivity(),
							"Caldroid view is created", Toast.LENGTH_SHORT)
							.show();
				}
			}

		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);


		
		
	
		return rootView;	
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
Intent i= new Intent(getActivity(),EventPage.class);
		
		i.putExtra("event_id", event_id[position]);
		i.putExtra("event_name", event_name[position]);
		i.putExtra("chapter_name", chapter_name[position]);
		System.out.println("lol "+chapter_name[position]);
		i.putExtra("description", description[position]);
		i.putExtra("date", date_event[position]);
		i.putExtra("time", time[position]);
		i.putExtra("venue", event_location[position]);
		i.putExtra("cord_name", cord1_name[position]);
		i.putExtra("cord_no", cord1_contact[position]);
		startActivity(i);
	}
}
