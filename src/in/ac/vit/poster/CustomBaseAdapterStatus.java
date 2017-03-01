package in.ac.vit.poster;

import java.util.List;

import com.google.android.gms.internal.cb;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CustomBaseAdapterStatus extends BaseAdapter 
{
    Context context;
    List<RowItemStatus> rowItems;
    public CustomBaseAdapterStatus(Context context, List<RowItemStatus> items) {
        this.context = context;
        this.rowItems = items;
        
    }

    

	/*private view holder class*/
    private class ViewHolder {

       TextView tvTitle;
       CheckBox cbNotifications;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItemStatus rowItem = (RowItemStatus) getItem(position);

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.status_details, null);
            holder = new ViewHolder();
            holder.cbNotifications = (CheckBox)
            		convertView.findViewById(R.id.cbNotifications);
            SharedPreferences pref = context.getSharedPreferences("notif"+position, 0);
			final String state = pref.getString("notif", "on");
			final Editor edit = pref.edit();
			if(state.equals("on"))
			{
				holder.cbNotifications.setChecked(true);
			}
			else
			{
				holder.cbNotifications.setChecked(false);
			}
			
            holder.cbNotifications.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					if(state.equals("on"))
					{
						edit.putString("notif", "off");
					}
					else
					{
						edit.putString("notif", "on");
					}
					edit.commit();
				}
			});
           holder.tvTitle=(TextView)convertView.findViewById(R.id.tvTitle);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        
         holder.tvTitle.setText(""+rowItem.getTitle());
     
        //holder.txtTitle.setText(rowItem.getTitle());
        
       // holder.txtCat.setText(rowItem.getCat());
       

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
}