package com.ximsfei.skindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.ximsfei.skindemo.actionbar.ActionbarTestActivity;
import com.ximsfei.skindemo.alert.AlertDialogActivity;
import com.ximsfei.skindemo.constraint.ConstraintLayoutActivity;
import com.ximsfei.skindemo.flycotablayout.ui.SimpleHomeActivity;
import com.ximsfei.skindemo.mdtab.MaterialDesignActivity;
import com.ximsfei.skindemo.picker.ColorPickerActivity;
import com.ximsfei.skindemo.picker.DrawablePickerActivity;
import com.ximsfei.skindemo.tab.MainActivity;
import com.ximsfei.skindemo.test.TestActivity;
import com.ximsfei.skindemo.window.WindowManagerActivity;
import com.ximsfei.skindemo.zip.ZipActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatSupportable;

/**
 * Created by ximsf on 2017/3/8.
 */

public class SplashActivity extends BaseActivity implements SkinCompatSupportable {
    private ListView mListView;
    private Context mContext = this;

    OptionsPickerView pvOptions;
    OptionsPickerBuilder builder;
    TextView tv;
    ImageView iv;

    private final String[] mItems = {
            "基础控件",
            "Material Design",
            "ConstraintLayout",
            "FlycoTabLayout",
            "AlertDialog",
            "WindowManager",
            "Test",
            "Actionbar",
            "Color Picker",
            "Drawable Picker",
            "Zip包资源加载"
    };
    private final Class<?>[] mClasses = {
            MainActivity.class,
            MaterialDesignActivity.class,
            ConstraintLayoutActivity.class,
            SimpleHomeActivity.class,
            AlertDialogActivity.class,
            WindowManagerActivity.class,
            TestActivity.class,
            ActionbarTestActivity.class,
            ColorPickerActivity.class,
            DrawablePickerActivity.class,
            ZipActivity.class
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initToolbar();

        iv = findViewById(R.id.iv_laucher);
        iv.setBackground(SkinCompatResources.getDrawable(this,R.mipmap.ic_launcher));
        tv = findViewById(R.id.tv_hello);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> dataList = new ArrayList<>();
                dataList.add("男");
                dataList.add("女");
                builder = new OptionsPickerBuilder(SplashActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    }
                }).setSubmitColor(SkinCompatResources.getColor(SplashActivity.this,R.color.ui_primary_color));

                pvOptions = builder.build();
                pvOptions.setPicker(dataList);
                pvOptions.show();
            }
        });

        mListView = (ListView) findViewById(R.id.list);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setFadingEdgeLength(0);
        mListView.setAdapter(new HomeAdapter(mContext, mItems));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, mClasses[position]);
                startActivity(intent);
            }
        });
    }


    public static int getColor(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void applySkin() {

        if (tv != null)
            tv.setTextColor( SkinCompatResources.getColor(this,R.color.ui_primary_color));
        if(iv != null){
            iv.setBackgroundResource(R.mipmap.ic_launcher);
        }
    }


    public class HomeAdapter extends BaseAdapter {
        private String[] mItems;
        private DisplayMetrics mDisplayMetrics;

        public HomeAdapter(Context context, String[] items) {
            this.mItems = items;
            mDisplayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        }

        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int padding = (int) (mDisplayMetrics.density * 10);


            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.simple_spinner_item, null);
            tv.setText(mItems[position]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tv.setTextAppearance(SplashActivity.this, R.style.SkinCompatTextAppearance);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(padding, padding, padding, padding);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    AbsListView.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(lp);
            return tv;
        }
    }

}
