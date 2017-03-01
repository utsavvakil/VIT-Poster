package in.ac.vit.poster;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterChapter extends BaseAdapter{

	Context context;
	int layoutResourceId;
	int height;
	Typeface font;
	private List<RowItemChap> objects;
	DatabaseHandlerCalender db;

	int i=0;

	public CustomAdapterChapter(Context context, List<RowItemChap> objects,Typeface font) {
		this.context=context;
		this.objects = objects;
		db= new DatabaseHandlerCalender(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final RecordHolder holder ;
		LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (row == null) {
			row = inflater.inflate(R.layout.chap_list_item, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.textViewtitle);
			holder.txtTime = (TextView)row.findViewById(R.id.textViewTime);
			holder.imageItem = (ImageView) row.findViewById(R.id.ivIcon);
			holder.txtDate = (TextView)row.findViewById(R.id.textViewDay);
			holder.txtLoc =  (TextView)row.findViewById(R.id.textViewLocation);
			
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		RowItemChap rowItem = objects.get(position);

		
		
		holder.txtTitle.setText(rowItem.getTitle());
		holder.txtTime.setText(rowItem.getTime());
		holder.txtDate.setText(rowItem.getDate());
		holder.txtLoc.setText(rowItem.getLocation());
		holder.imageItem.setImageResource(rowItem.getImage());
		return row;

	}
	static class RecordHolder {
		TextView txtTitle,txtTime,txtLoc,txtDate;
		int image;
		
		TextView chapName;
		ImageView imageItem;

		
	}

	
	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return objects.indexOf(getItem(position));
	}
}
