package isy.listsavingviewpage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements ScrollTabHolder {

    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private ProfilePage1Fragment profilePage1Fragment;
    private ProfilePage2Fragment profilePage2Fragment;
    private ProfilePage3Fragment profilePage3Fragment;
    private ProfileHeaderFragment profileHeaderFragment;
    private int mNumFragments;
    private int mScrollState;
    private int actionBarHeight;
    private Toolbar toolbar;
    private Bundle bundle;
    private ViewPager viewPager;
    private ArrayList<ScrollTabHolderFragment> fragmentArrayList = new ArrayList<ScrollTabHolderFragment>();
    private ProfilePagerAdapter pagerAdapter;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private View profileRelativeLayout;
    private int tabHeight;
    private int scrollY;
    private FragmentManager supportManager;
    private final String HEADER_TRANSLATION_Y = "header_translation_y";
    private boolean oneTop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        supportManager = getSupportFragmentManager();

        actionBarHeight = (int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material);

        bundle = getIntent().getExtras();

        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;

        toolbar = (Toolbar) findViewById(R.id.action_bar);
        toolbar.setBackgroundColor(Color.BLUE);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("TITLE");
        setSupportActionBar(toolbar);

        profileRelativeLayout = findViewById(R.id.profile_frame);
        viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pager_header);
        viewPager.setOffscreenPageLimit(3);

        mPagerSlidingTabStrip.setBackgroundColor(Color.BLUE);
        pagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager(), bundle, fragmentArrayList);

        profilePage1Fragment = new ProfilePage1Fragment();
        profilePage2Fragment = new ProfilePage2Fragment();
        profilePage3Fragment = new ProfilePage3Fragment();
        profileHeaderFragment = new ProfileHeaderFragment();
        profilePage2Fragment.setArguments(bundle);
        profilePage1Fragment.setArguments(bundle);
        supportManager.beginTransaction().replace(R.id.profile_header, profileHeaderFragment).commit();
        populateViews();
        viewPager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
        mPagerSlidingTabStrip.setOnPageChangeListener(getViewPagerPageChangeListener());
        mPagerSlidingTabStrip.setViewPager(viewPager);
    }

    public void populateViews() {
        fragmentArrayList.add(profilePage1Fragment);
        fragmentArrayList.add(profilePage2Fragment);
        fragmentArrayList.add(profilePage3Fragment);
        pagerAdapter.setTabHolderScrollingContent(this);
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }


    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY, boolean isOthersTop) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {

        if (viewPager.getCurrentItem() == pagePosition) {
            scrollY = getScrollY(view);
            profileRelativeLayout.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
        }

        oneTop = (getScrollY(view) < getResources().getDimension(R.dimen.header_height)) ? true : false;
    }

    private ViewPager.OnPageChangeListener getViewPagerPageChangeListener() {
        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels >= 0) {
                    int currentItem = viewPager.getCurrentItem();
                    SparseArrayCompat<ScrollTabHolder> scrollTabHolders = pagerAdapter.getScrollTabHolders();
                    //position == next page
                    ScrollTabHolder fragmentContent;
                    if (position < currentItem) {
                        fragmentContent = scrollTabHolders.valueAt(position);
                    } else {
                        fragmentContent = scrollTabHolders.valueAt(position + 1);
                    }
                    if (fragmentContent != null)
                        fragmentContent.adjustScroll((int) (profileRelativeLayout.getHeight() + profileRelativeLayout.getTranslationY()),
                                (int) getResources().getDimension(R.dimen.tab_height), oneTop);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mScrollState = state;
            }
        };
        return listener;
    }
}
