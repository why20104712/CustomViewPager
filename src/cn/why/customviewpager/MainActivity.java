package cn.why.customviewpager;

import cn.why.customviewpager.CustomViewPager.CustomPageChangedListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private CustomViewPager customViewPager;
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
        //使用自定义的页面变化事件
        customViewPager.setPageChangedListener(new CustomPageChangedListener() {
			public void moveToDest(int currId) {
				Toast.makeText(getApplicationContext(), "页面发生了改变", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    
}
