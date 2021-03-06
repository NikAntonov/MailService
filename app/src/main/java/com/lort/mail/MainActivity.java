package com.lort.mail;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static FloatingActionButton fab;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CollectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        SwipeRefreshLayout mSwipeRefreshLayout;
        ArrayList<Task> taskList;
        RecyclerView rv;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public CollectFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CollectFragment newInstance(int sectionNumber) {
            CollectFragment fragment = new CollectFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
            mSwipeRefreshLayout.setOnRefreshListener(this);
            // делаем повеселее
            mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
            rv = (RecyclerView) rootView.findViewById(R.id.rv_main);
            taskList = new ArrayList<Task>();
            for (int i = 0; i < 6; i++) {
                taskList.add(new Task());
            }
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            TaskAdapter adapter = new TaskAdapter(taskList);
            rv.setAdapter(adapter);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            rv.setItemAnimator(itemAnimator);
            return rootView;
        }

        @Override
        public void onRefresh() {
            // говорим о том, что собираемся начать
            //Toast.makeText(this, R.string.refresh_started, Toast.LENGTH_SHORT).show();
            // начинаем показывать прогресс
            mSwipeRefreshLayout.setRefreshing(true);
            rv.getAdapter().notifyDataSetChanged();
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    // говорим о том, что собираемся закончить
                    //Toast.makeText(MainActivity.this, R.string.refresh_finished, Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a CollectFragment (defined as a static inner class below).

            if (position == 0) {
                Bundle args;
                Fragment fragment = new CollectFragment();
                args = new Bundle();
                args.putInt("Сбор", position + 1);
                fragment.setArguments(args);
                return fragment;
            } else if (position == 1) {
                Bundle args;
                Fragment fragment2 = new Fragment();
                args = new Bundle();
                args.putInt("Доставка", position + 2);
                fragment2.setArguments(args);
                return fragment2;
            } else if (position == 2) {
                Bundle args;
                Fragment fragment3 = new ContractListFragment();
                args = new Bundle();
                args.putInt("Контрольный лист", position + 3);
                fragment3.setArguments(args);
                return fragment3;
            } else {
                return null;
            }
            //return CollectFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Сбор";
                case 1:
                    return "Доставка";
                case 2:
                    return "Контрольный лист";
            }
            return null;
        }
    }
}
