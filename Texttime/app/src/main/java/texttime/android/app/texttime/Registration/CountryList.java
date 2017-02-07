package texttime.android.app.texttime.Registration;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import CustomViews.CustomTextViewBold;
import CustomViews.VerticalSeekBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import texttime.android.app.texttime.Adapter.CountryListAdapter;
import texttime.android.app.texttime.CommonClasses.CountryJSON;
import texttime.android.app.texttime.DataModels.Country;
import texttime.android.app.texttime.GeneralClasses.BaseActivity;
import texttime.android.app.texttime.R;

/**
 * Created by Dinesh_Text on 2/7/2017.
 */

public class CountryList extends BaseActivity implements Comparator<Country>, View.OnTouchListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    @BindView(R.id.country_picker_search)
    EditText countryPickerSearch;
    @BindView(R.id.alphabate)
    CustomTextViewBold alphabate;
    @BindView(R.id.list_country)
    ListView listCountry;
    @BindView(R.id.seekBar1)
    VerticalSeekBar seekBar;
    @BindView(R.id.alphabaticLayout)
    LinearLayout alphabaticLayout;
    @BindView(R.id.side_index)
    LinearLayout sideIndex;
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.search_icon)
    ImageView searchIcon;

    private CountryListAdapter adapter;
    private List<Country> cList, selectedCountriesList;
    private String[] countryname = null;
    private Map<String, Integer> mapIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_list);
        ButterKnife.bind(this);
        init(this);
        adjustUIcontant();
        setonClicklistener();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AllCountryList().execute();
            }
        },10);
    }

    public void init(){
        listCountry.setOnItemClickListener(this);
        listCountry.setOnScrollListener(this);

        Typeface font = dfunctions.getFontFamily(this);
        countryPickerSearch.setTypeface(font, Typeface.NORMAL);
        countryPickerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
        seekBar.setMax(25);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        int diff = 25 - progress;
                        if (diff < 0)
                            diff = diff * -1;
                        TextView tv = (TextView) alphabaticLayout.getChildAt(diff);
                        listCountry.setSelection(mapIndex.get(tv.getText().toString()));
                        alphabate.setText(tv.getText().toString());
                    }
                });
    }

    private void adjustUIcontant() {
        cv.adjustLinearSquare(searchIcon, 50);
        cv.adjustLinearSquare(cancel, 50);
        cv.adjustRelativeHeight(seekBar, 700);
        cv.adjustRelativeWidth(seekBar, 70);
        cv.adjustRelativeHeight(alphabaticLayout, 700);
    }

    private void setonClicklistener(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView cname = (TextView) view.findViewById(R.id.country_name);
        TextView dcode = (TextView) view.findViewById(R.id.dial_code);
        TextView iso2 = (TextView) view.findViewById(R.id.isoalpha2);
        sd.setDialCode(dcode.getText().toString());
        sd.setCountryName(cname.getText().toString());
        sd.setISOAlpha2(iso2.getText().toString());
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int i) {
        int ic = listCountry.getFirstVisiblePosition();
        TextView txt = (TextView) view.findViewById(R.id.country_name);
        String indx = txt.getText().toString().substring(0, 1);
        if (ic > 7)
            alphabate.setText(indx);
        else
            alphabate.setText("#");
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }


    //--------------Get all CountryList in the background frame
    private class AllCountryList extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            getAllCountries();
            getIndexList(countryname);
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            adapter = new CountryListAdapter(CountryList.this, selectedCountriesList);
            listCountry.setAdapter(adapter);
            init();
            fillLayout();
        }
    }

    // -------------- Search Country name from list-----
    private void search(String text) {
        selectedCountriesList.clear();
        for (Country country : cList) {
            if (country.getName().toLowerCase(Locale.ENGLISH)
                    .contains(text.toLowerCase())) {
                selectedCountriesList.add(country);
            }
        }
        adapter.notifyDataSetChanged();
    }

    //-------------Get All Country Function-------
    private List<Country> getAllCountries() {
        if (cList == null) {
            try {
                cList = new ArrayList<Country>();
                try {
                    JSONArray countries = new CountryJSON().getAllCountryList();
                    countryname = new String[countries.length()];
                    JSONArray prior = new CountryJSON().getFirstPriorityCountry();
                    for (int i = 0; i < prior.length(); i++) {
                        JSONObject da = prior.getJSONObject(i);
                        Country country = new Country();
                        country.setCode(da.getString("code"));
                        country.setName(da.getString("name"));
                        country.setDialCode(da.getString("dial_code"));
                        cList.add(country);
                    }
                    for (int idx = 0; idx < countries.length(); idx++) {
                        JSONObject data = countries.getJSONObject(idx);
                        Country country = new Country();
                        country.setCode(data.getString("code"));
                        country.setName(data.getString("name"));
                        country.setDialCode(data.getString("dial_code"));
                        countryname[idx] = data.getString("name");
                        cList.add(country);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selectedCountriesList = new ArrayList<Country>();
                selectedCountriesList.addAll(cList);
                // Return
                return cList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //-------------End All Country Body------
    //-------------Get Countries Index List -------
    private void getIndexList(String[] country) {
        mapIndex = new LinkedHashMap<String, Integer>();
        Arrays.sort(countryname);
        mapIndex.put("#", 0);
        for (int i = 0; i < country.length; i++) {
            String countries = country[i];
            String index = countries.substring(0, 1);
            if (mapIndex.get(index) == null)
                mapIndex.put(index, (i + 7));
        }
    }

    @Override
    public int compare(Country lhs, Country rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }

    private void fillLayout() {
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.gravity = Gravity.CENTER;
            tv.setLayoutParams(lp);
            tv.setTextColor(getResources().getColor(R.color.textColor1));
            tv.setText(index);
            tv.setTextSize(9f);
            tv.setPadding(1,1,1,1);
            alphabaticLayout.addView(tv);
        }
    }
}
