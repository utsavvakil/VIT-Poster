package in.ac.vit.poster;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class CustomAdapterCalendarEvents extends BaseAdapter{

	Context context;
	int layoutResourceId;
	int height;
	Typeface font;
	private List<RowItem> objects;
	
	int i=0;

	public CustomAdapterCalendarEvents(Context context,
			List<RowItem> objects,Typeface font) {
		this.context=context;
		this.objects = objects;
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
			holder.imageItem = (NetworkImageView) row.findViewById(R.id.ivIcon);
			holder.txtDate = (TextView)row.findViewById(R.id.textViewDay);
			holder.txtLoc =  (TextView)row.findViewById(R.id.textViewLocation);
			
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		RowItem rowItem = objects.get(position);

		
		
		holder.txtTitle.setText(rowItem.getTitle());
		holder.txtTime.setText(rowItem.getTime());
		holder.txtLoc.setText(rowItem.getLocation());
		//holder.imageItem.setImageResource(rowItem.getImage());
		
		RequestQueue mRequestQueue;
		ImageLoader mImageLoader;
		mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
		    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
		    public void putBitmap(String url, Bitmap bitmap) {
		        mCache.put(url, bitmap);
		    }
		    public Bitmap getBitmap(String url) {
		        return mCache.get(url);
		    }
		});
		System.out.println("YOLO"+rowItem.getThumb());
		holder.imageItem.setImageUrl(rowItem.getThumb(), mImageLoader);
		return row;

	}
	static class RecordHolder {
		TextView txtTitle,txtTime,txtLoc,txtDate;
		int image;
		
		TextView chapName;
		NetworkImageView imageItem;

		
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
