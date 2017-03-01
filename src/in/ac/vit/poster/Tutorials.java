package in.ac.vit.poster;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Tutorials extends ActionBarActivity
{

	Context context;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorials);

		ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03b5f5"));
		getSupportActionBar().setBackgroundDrawable(colorDrawable);
		getSupportActionBar().setTitle("");
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pagerTutorials);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}



	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{

		public SectionsPagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		Bundle args;
		@Override
		public Fragment getItem(int position) 
		{
			switch (position)
			{

			case 0:
				Fragment fragment = new DummySectionFragment();
				args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;

			case 1:
				Fragment fragment2 = new DummySectionFragment2();
				args = new Bundle();
				args.putInt(DummySectionFragment2.ARG_SECTION_NUMBER, position + 2);
				fragment2.setArguments(args);
				return fragment2;
			case 2:
				Fragment fragment3 = new DummySectionFragment3();
				args = new Bundle();
				args.putInt(DummySectionFragment3.ARG_SECTION_NUMBER, position + 3);
				fragment3.setArguments(args);
				return fragment3;
			case 3:
				Fragment fragment4 = new DummySectionFragment4();
				args = new Bundle();
				args.putInt(DummySectionFragment4.ARG_SECTION_NUMBER, position + 4);
				fragment4.setArguments(args);
				return fragment4;



			default:
				return null;
			}


		}


		@Override
		public int getCount() 
		{
			// Show 3 total pages.
			return 4;
		}


	}

	public static class DummySectionFragment extends Fragment
	{
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment()
		{

		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.tutorial_page1, container, false);
			return rootView;	
		}
	}

	public static class DummySectionFragment2 extends Fragment
	{
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment2()
		{

		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.tutorial_page2, container, false);
			return rootView;	
		}
	}

	public static class DummySectionFragment3 extends Fragment
	{
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment3()
		{

		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.tutorial_page3, container, false);
			return rootView;	
		}
	}

	public static class DummySectionFragment4 extends Fragment implements OnClickListener
	{

		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment4()
		{

		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.tutorial_page4, container, false);
			Button bLogin = (Button)rootView.findViewById(R.id.bLogin);
			bLogin.setOnClickListener(this);
			return rootView;	
		}


		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
			case R.id.bLogin:
				Intent intentLogin = new Intent(getActivity(),Fetch.class);
				startActivity(intentLogin);
				getActivity().finish();
				break;
			}

		}
	}

}
