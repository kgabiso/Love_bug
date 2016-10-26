package com.example.kaysherman.love_bug;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public static final int RESULT_PICK_CONTACT = 8550;
        public static final int RESULT_PICK_CONTACT2 = 8500;
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        Button Calculate1;
        Button Calculate2;
        EditText txtFemale, txtMale;
        TextView edtScore;
        AlertDialog.Builder builder;
        /*******************/
        EditText number1, number2;
        TextView score, name1, name2;
        ImageButton picker1, picker2;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                final View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);
                txtFemale = (EditText) rootView.findViewById(R.id.female);
                txtMale = (EditText) rootView.findViewById(R.id.male);
                Calculate1 = (Button) rootView.findViewById(R.id.btnCalc);
                edtScore = (TextView) rootView.findViewById(R.id.Score);
                builder = new AlertDialog.Builder(getActivity());
                Calculate1.setEnabled(true);
                Calculate1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!txtFemale.getText().toString().equals("") && !txtMale.getText().toString().equals("")) {
                            String fmale = txtFemale.getText().toString().trim();
                            String male = txtMale.getText().toString().trim();
                            int Score = 0;
                            final String concat = String.valueOf(fmale).concat(String.valueOf(male)).toUpperCase();//change to lower later

                            if (fmale.toString().trim().length() == 0 || male.toString().trim().length() == 0) {
                                Toast.makeText(getActivity(), "Please fill both the fields", Toast.LENGTH_LONG).show();
                            } else {
                                int sum = 0;
                                for (int i = 0; i < concat.length(); i++) {
                                    char chara = concat.charAt(i);
                                    int ascii = (int) chara;
                                    sum += ascii;
                                }
                                Score = (sum % 100);
                                edtScore.setText(Score + "%");

                                Calculate1.setEnabled(false);
                                txtFemale.setEnabled(false);
                                txtMale.setEnabled(false);
                                Handler handler = new Handler();
                                final int finalScore = Score;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                        progressDialog.setMessage("Processing your Lobola ...");
                                        progressDialog.show();
                                        Window window = progressDialog.getWindow();
                                        window.setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
//
                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(getActivity(), LobolaActivity.class);
                                                intent.putExtra("Score", String.valueOf(finalScore));
                                                intent.putExtra("Female", txtFemale.getText().toString());
                                                intent.putExtra("Male", txtMale.getText().toString());
                                                startActivity(intent);
                                                progressDialog.dismiss();
                                            }
                                        }, 3000);
                                    }
                                }, 2000);


                            }
                        }
                    }
                });
                return rootView;
            } else {
                View rootView = inflater.inflate(R.layout.fragment_calculate_friends, container, false);

                picker1 = (ImageButton) rootView.findViewById(R.id.contactpicker1);
                picker2 = (ImageButton) rootView.findViewById(R.id.contactpicker2);
                score = (TextView) rootView.findViewById(R.id.FinalScore);
                number1 = (EditText) rootView.findViewById(R.id.contact1);
                number2 = (EditText) rootView.findViewById(R.id.contact2);
                name1 = (TextView) rootView.findViewById(R.id.name1);
                name2 = (TextView) rootView.findViewById(R.id.name2);

                Calculate2 = (Button) rootView.findViewById(R.id.btnCalc2);
                Calculate2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int Score = 0;
                        if (!number1.getText().toString().equals("") && !number2.getText().toString().equals("")) {
                            String fmale = number1.getText().toString();
                            String male = number2.getText().toString();
                            Score = 0;
                            final String concat = String.valueOf(fmale).concat(String.valueOf(male)).toUpperCase();//change to lower later

                            if (fmale.toString().trim().length() == 0 || male.toString().trim().length() == 0) {
                                Toast.makeText(getActivity(), "Please fill both the fields", Toast.LENGTH_LONG).show();
                            } else {
                                int sum = 0;
                                for (int i = 0; i < concat.length(); i++) {
                                    char chara = concat.charAt(i);
                                    int ascii = (int) chara;
                                    sum += ascii;
                                }
                                Score = (sum % 100);
                                score.setText(Score + "%");
                            }
                        }
                        Calculate2.setEnabled(false);
                        number1.setEnabled(false);
                        number2.setEnabled(false);
                        Handler handler = new Handler();
                        final int finalScore = Score;

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                                progressDialog.setMessage("Processing your Lobola ...");
                                progressDialog.show();
                                Window window = progressDialog.getWindow();
                                window.setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
//
                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(getActivity(), LobolaActivity.class);
                                        intent.putExtra("Score", String.valueOf(finalScore));
                                        intent.putExtra("Female", number1.getText().toString());
                                        intent.putExtra("Male", number2.getText().toString());
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                            }
                        }, 2000);
                    }
                });

                picker1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent contactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        startActivityForResult(contactIntent, RESULT_PICK_CONTACT);
                        Vibrate();
                    }
                });
                picker2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent contactIntent2 = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        startActivityForResult(contactIntent2, RESULT_PICK_CONTACT2);
                        Vibrate();
                    }
                });
                return rootView;
            }
        }

        private void Vibrate() {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(200);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                //check for the request code ,we might be using multiple startActivityResult
                switch (requestCode) {
                    case RESULT_PICK_CONTACT:
                        contactPicked(data);
                        break;
                    case RESULT_PICK_CONTACT2:
                        contactPicked2(data);
                        break;

                }
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.cancel();

            } else {
                Toast.makeText(getActivity(), "Fail to pick contact", Toast.LENGTH_SHORT).show();
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.cancel();
            }

        }

        private void contactPicked2(Intent data) {
            Cursor cursorContact = null;
            try {
                String phoneNumber = null;
                String name = null;

                Uri uri = data.getData();
                //Query the contact uri
                cursorContact = getActivity().getContentResolver().query(uri, null, null, null, null);
                cursorContact.moveToFirst();
                //column index of the phone number
                int phoneIndex = cursorContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                //column index of the contact name
                int nameIndex = cursorContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                phoneNumber = cursorContact.getString(phoneIndex);
                name = cursorContact.getString(nameIndex);
                //set the value to the textView
                name2.setText(phoneNumber);
                number2.setText(name);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void contactPicked(Intent data) {
            Cursor cursorContact = null;
            try {
                String phoneNumber = null;
                String name = null;

                Uri uri = data.getData();
                //Query the contact uri
                cursorContact = getActivity().getContentResolver().query(uri, null, null, null, null);
                cursorContact.moveToFirst();
                //column index of the phone number
                int phoneIndex = cursorContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                //column index of the contact name
                int nameIndex = cursorContact.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                phoneNumber = cursorContact.getString(phoneIndex);
                name = cursorContact.getString(nameIndex);
                //set the value to the textView
                name1.setText(phoneNumber);
                number1.setText(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Calculate Love";
                case 1:
                    return "Calculate Friends";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
