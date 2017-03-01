package in.ac.vit.poster;

import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class TestActivity extends ActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03b5f5"));
		FadingActionBarHelper helper = new FadingActionBarHelper()
        .actionBarBackground(colorDrawable)
        .headerLayout(R.layout.header)
        .contentLayout(R.layout.floatingstuff);

        setContentView(helper.createView(this));
		
	}
	

}
