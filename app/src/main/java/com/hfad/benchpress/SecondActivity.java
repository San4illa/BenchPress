package com.hfad.benchpress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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

import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

public class SecondActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private SharedPreferences pref;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        pref = getSharedPreferences("myPos", MODE_PRIVATE);
        position = pref.getInt("position", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                if (position != i) {
                    fab.setImageResource(R.drawable.ic_swap);
                } else {
                    fab.setImageResource(R.drawable.ic_check);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getCurrentItem() == position) {
                    Snackbar.make(view, "Тренировка окончена?", Snackbar.LENGTH_LONG)
                            .setAction("Да", snackbarOnClickListener).show();
                } else {
                    mViewPager.setCurrentItem(position);
                }
            }
        });
        if (position == 0) {
            TapTargetView.showFor(this,
                    TapTarget.forView(findViewById(R.id.fab), "Закончите тренировку:", "Нажмите эту кнопку, чтобы перейти к следующей тренировке")
                            .outerCircleAlpha(0.96f)
                            .targetCircleColor(R.color.grey)
                            .titleTextSize(26)
                            .titleTextColor(R.color.white)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.white)
                            .textColor(R.color.white)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(false)
                            .tintTarget(false)
                            .transparentTarget(false)
                            .targetRadius(40),
                    new TapTargetView.Listener() {
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);
                        }
                    });
        }
    }

    private View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mViewPager.getCurrentItem() == position) {
                if (position != 8) {
                    position++;
                    pref.edit().putInt("position", position).apply();
                    mViewPager.setCurrentItem(position);
                } else {
                    pref.edit().putInt("position", 0).apply();
                    DialogFragment fragment = new FinishDialogFragment();
                    fragment.show(getSupportFragmentManager(), "finish");
                }
            } else {
                mViewPager.setCurrentItem(position);
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://drive.google.com/file/d/0B1LuWbXviYhaS3puZmN6X0lhLTQ");
            startActivity(Intent.createChooser(intent, "Отправить ссылку на приложение"));
            return true;
        }

        if (id == R.id.information) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        // Analog of floor method from Excel
        public static double floor(double a, double b) {
            return (int) (a * (1 / b)) * b;
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            TextView txt;
            int number = getArguments().getInt(ARG_SECTION_NUMBER);
            double pm = Double.parseDouble(getActivity().getIntent().getStringExtra("pm"));

            switch (number) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_1, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight4);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight5);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг ⩽ 7");
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_2, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 0.96, 2.5) + "кг x 1");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 0.96, 2.5) + "кг x 1");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 0.96, 2.5) + "кг x 1");
                    txt = (TextView) rootView.findViewById(R.id.weight4);
                    txt.setText(floor(pm * 1.06, 2.5) + "кг x 2");
                    txt = (TextView) rootView.findViewById(R.id.weight5);
                    txt.setText(floor(pm * 1.06, 2.5) + "кг x 2");
                    txt = (TextView) rootView.findViewById(R.id.weight6);
                    txt.setText(floor(pm * 1.06, 2.5) + "кг x 2");
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_3, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 0.88, 2.5) + "кг x 3");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 0.88, 2.5) + "кг x 3");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 0.88, 2.5) + "кг x 3");
                    txt = (TextView) rootView.findViewById(R.id.weight4);
                    txt.setText(floor(pm * 0.88, 2.5) + "кг ⩽ 5");
                    break;
                case 4:
                    rootView = inflater.inflate(R.layout.fragment_4, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 0.72, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 0.72, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 0.72, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight4);
                    txt.setText(floor(pm * 0.72, 2.5) + "кг ⩽ 7");
                    break;
                case 5:
                    rootView = inflater.inflate(R.layout.fragment_5, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 1, 2.5) + "кг x 1");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 1, 2.5) + "кг x 1");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 1.12, 2.5) + "кг x 2");
                    txt = (TextView) rootView.findViewById(R.id.weight4);
                    txt.setText(floor(pm * 1.12, 2.5) + "кг x 2");
                    txt = (TextView) rootView.findViewById(R.id.weight5);
                    txt.setText(floor(pm * 1.12, 2.5) + "кг x 2");
                    break;
                case 6:
                    rootView = inflater.inflate(R.layout.fragment_6, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 0.76, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 0.76, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 0.76, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight4);
                    txt.setText(floor(pm * 0.76, 2.5) + "кг ⩽ 7");
                    break;
                case 7:
                    rootView = inflater.inflate(R.layout.fragment_7, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 0.92, 2.5) + "кг x 3");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 0.92, 2.5) + "кг x 3");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 0.92, 2.5) + "кг ⩽ 5");
                    break;
                case 8:
                    rootView = inflater.inflate(R.layout.fragment_8, container, false);
                    txt = (TextView) rootView.findViewById(R.id.weight1);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight2);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight3);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight4);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    txt = (TextView) rootView.findViewById(R.id.weight5);
                    txt.setText(floor(pm * 0.68, 2.5) + "кг x 5");
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_9, container, false);
            }
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "1";
                case 1:
                    return "2";
                case 2:
                    return "3";
                case 3:
                    return "4";
                case 4:
                    return "5";
                case 5:
                    return "6";
                case 6:
                    return "7";
                case 7:
                    return "8";
                case 8:
                    return "9";
            }
            return null;
        }
    }
}