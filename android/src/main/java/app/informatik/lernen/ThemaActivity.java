package app.informatik.lernen;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;

import java.util.ArrayList;
import java.util.List;


public class ThemaActivity extends AppCompatActivity {

    private static int NUM_PAGES;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int itemID;
    static List<StepBean> stepsBeanList = new ArrayList<>();
    private static HorizontalStepView state;
    private static List<Thema> themas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thema);

        itemID = getIntent().getExtras().getInt("thema");
        NUM_PAGES = Data.items.get(itemID).getThemen().size();

        ActionBar test = getSupportActionBar();
        test.setBackgroundDrawable(new ColorDrawable(Data.items.get(itemID).getColor()));
        test.setTitle(Data.items.get(itemID).getName());

        state = (HorizontalStepView) findViewById(R.id.state_view);

        themas = Data.items.get(itemID).getThemen();

        for (int i = 0; i < themas.size(); i++) {
            StepBean stepBean = new StepBean("asd", themas.get(i).getCompleteCode());
            stepsBeanList.add(stepBean);
        }

        /*StepBean stepBean0 = new StepBean("接单",1);
        StepBean stepBean1 = new StepBean("打包",1);
        StepBean stepBean2 = new StepBean("出发",1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);*/


        state.setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.done))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.selected));//设置StepsViewIndicator AttentionIcon

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeProgress(position, actx);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        MainActivity.updateProgress();
        super.onBackPressed();
        /*if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }*/
    }

    private static int cur_pos = 0;
    public static void topicDone(Context context) {
        stepsBeanList.get(cur_pos).setState(1);
        changeProgress(cur_pos, context);
    }

    public static void topicUnDone(Context context) {
        stepsBeanList.get(cur_pos).setState(0);
        changeProgress(cur_pos, context);
    }

    public static void changeProgress(int position, Context context) {
        cur_pos = position;
        for (int i = 0; i < stepsBeanList.size(); i++) {
            stepsBeanList.get(i).setState(themas.get(i).getCompleteCode());
        }

        if (stepsBeanList.get(position).getState() != 1) {
            stepsBeanList.get(position).setState(0);
        }
        //stepsBeanList.get(position).setState(1);
        state.setStepViewTexts(stepsBeanList)
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(context, android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(context, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(context, android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(context, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(context, R.drawable.done))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(context, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(context, R.drawable.selected));//设置StepsViewIndicator AttentionIcon
    }

    private Context actx = this;
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ThemaPageFragment(Data.items.get(itemID).getThemen().get(position), itemID, position, actx);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
