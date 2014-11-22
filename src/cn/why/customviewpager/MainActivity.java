package cn.why.customviewpager;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.why.customviewpager.CustomViewPager.CustomPageChangedListener;

public class MainActivity extends Activity {

	private CustomViewPager customViewPager;
	private RadioGroup radioGroup;
	private ListView listView;
	private int[] ids = new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        customViewPager = (CustomViewPager) findViewById(R.id.viewPager);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < ids.length; i++) {
        	ImageView imageView = new ImageView(this);
        	imageView.setBackgroundResource(ids[i]);
        	customViewPager.addView(imageView);
        	
        	RadioButton radioButton = new RadioButton(this);
        	radioButton.setId(i);
        	radioGroup.addView(radioButton);
        	if (i == 0) {
        		radioButton.setChecked(true);
        	}
		}
      //给自定义viewGroup添加测试的布局
  		View temp = getLayoutInflater().inflate(R.layout.temp, null);
  		customViewPager.addView(temp, 2);
  		listView = (ListView) findViewById(R.id.listView1);
  		listView.setAdapter(new CustomListViewAdapter());
        //使用自定义的页面变化事件
        customViewPager.setPageChangedListener(new CustomPageChangedListener() {
			public void moveToDest(int currId) {
				Toast.makeText(getApplicationContext(), "页面发生了改变", Toast.LENGTH_SHORT).show();
				((RadioButton)radioGroup.getChildAt(currId)).setChecked(true);
			}
		});
        //为radioGroup设置监听事件
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				customViewPager.moveToDest(checkedId);
			}
		});
    }
    /*
     * 自定义listview适配器
     */
    public class CustomListViewAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return ids.length;
		}

		@Override
		public Object getItem(int position) {
			return ids[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup group) {
			ImageView imageView = new ImageView(getApplicationContext());
			imageView.setBackgroundResource(ids[position]);
			return imageView;
		}
    	
    }
    
}
