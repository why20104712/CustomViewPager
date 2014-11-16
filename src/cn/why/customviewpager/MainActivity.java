package cn.why.customviewpager;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private CustomViewPager customViewPager;
	//图片资源ID 数组
	private int[] ids = new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        customViewPager = (CustomViewPager) findViewById(R.id.viewPager);
        for (int i = 0; i < ids.length; i++) {
        	ImageView imageView = new ImageView(this);
        	imageView.setBackgroundResource(ids[i]);
        	customViewPager.addView(imageView);
		}
    }
    
    
}
