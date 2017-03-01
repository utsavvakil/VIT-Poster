package in.ac.vit.poster;

import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;






public class CustomBaseAdapter extends BaseAdapter implements OnClickListener 
{
    Context context;
    List<RowItem> rowItems;
    Typeface font;
    int pos;
    private RowItem[] objects;

    public CustomBaseAdapter(Context context, List<RowItem> items,Typeface font) {
        this.context = context;
        this.rowItems = items;
        this.font = font;
    }

    

	/*private view holder class*/
    private class ViewHolder {

        TextView txtTitle;
        TextView txtLoc;
        TextView txtTime;
        TextView txtCat;
        View	colourBar;
        TextView txtVenue;
        TextView txtCancel;
        Button btnMore;
        

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        pos = position;
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textViewtitle);
            holder.txtLoc = (TextView) convertView.findViewById(R.id.textViewLocation);
            holder.txtTime=(TextView) convertView.findViewById(R.id.textViewTime);
            holder.txtVenue = (TextView)convertView.findViewById(R.id.textViewViewVenue);
            holder.txtCancel = (TextView)convertView.findViewById(R.id.textViewCancel);
            
            holder.txtVenue.setOnClickListener(this);
            holder.txtCancel.setOnClickListener(this);
            
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        RowItem rowItem = (RowItem) getItem(position);

        
        holder.txtTitle.setText(rowItem.getTitle());
        holder.txtTitle.setTypeface(font);
       // holder.txtCat.setText(rowItem.getCat());
        holder.txtLoc.setText(rowItem.getLocation());
        holder.txtLoc.setTypeface(font);
        holder.txtTime.setText(rowItem.getTime());
        holder.txtTime.setTypeface(font);
        

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.textViewViewVenue: Toast.makeText(this.context, "View Venue" + pos, Toast.LENGTH_SHORT).show();
		break;
		case R.id.textViewCancel: Toast.makeText(this.context, "Cancel" + pos, Toast.LENGTH_SHORT).show();
		break;
		
		}
		// TODO Auto-generated method stub
		
	}
	 @Override
     public int getViewTypeCount() {
         return 2;
     }
}