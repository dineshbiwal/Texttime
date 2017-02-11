package texttime.android.app.texttime.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import CustomViews.CustomTextView;
import CustomViews.CustomTextViewRegular;
import texttime.android.app.texttime.CommonClasses.CommonViewUtility;
import texttime.android.app.texttime.DataModels.Country;
import texttime.android.app.texttime.R;


public class CountryListAdapter extends BaseAdapter {

	private Context context;
	List<Country> countries;
	LayoutInflater inflater;
	CommonViewUtility cv;
	Map<String, Integer> mapIndex;
	/**
	 * Constructor
	 * 
	 * @param context
	 * @param countries
	 */
	public CountryListAdapter(Context context, List<Country> countries, Map<String, Integer> index) {
		super();
		this.context = context;
		this.countries = countries;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mapIndex = index;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return countries.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Return row for each country
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View cellView = convertView;
		Cell cell;
		Country country = countries.get(position);
		if (convertView == null) {
			cell = new Cell();
			cv = CommonViewUtility.getInstance();
			cellView = inflater.inflate(R.layout.country_item, null);
			cell.country_name = (TextView) cellView.findViewById(R.id.country_name);
			cell.iso_alpha_2 = (TextView) cellView.findViewById(R.id.isoalpha2);
			cell.iso_alpha_3 = (TextView) cellView.findViewById(R.id.isoalpha3);
			cell.dial_code = (TextView) cellView.findViewById(R.id.dial_code);
			cell.sText = (LinearLayout) cellView.findViewById(R.id.s_text);
			cell.alphabate = (CustomTextViewRegular) cellView.findViewById(R.id.alphabate);
			cv.adjustLinearMargin(cell.country_name, CommonViewUtility.LEFT, 32);
			cv.adjustLinearMargin(cell.dial_code, CommonViewUtility.RIGHT, 50 );
			cv.adjustLinearMargin(cell.sText, CommonViewUtility.LEFT, 37);
			cv.adjustLinear(cell.sText, 100, 100);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}
		String index = country.getName().substring(0, 1);
		cell.sText.setVisibility(View.INVISIBLE);
		if(position == 0){
			cell.sText.setVisibility(View.VISIBLE);
			cell.alphabate.setText("#");
		}
		if(mapIndex.get(index)==position){
			cell.sText.setVisibility(View.VISIBLE);
			cell.alphabate.setText(index);
			//cv.adjustLinearMargin(cell.country_name, CommonViewUtility.LEFT, 32);
		}
		cell.country_name.setText(country.getName());
		cell.iso_alpha_2.setText(country.getCode());
		cell.dial_code.setText(country.getDialCode());
		cell.iso_alpha_3.setText("");
		return cellView;
	}

	private void getIndexList(String[] country) {
		mapIndex = new LinkedHashMap<String, Integer>();
		//Arrays.sort(countryname);
		mapIndex.put("#", 0);
		for (int i = 0; i < country.length; i++) {
			String countries = country[i];
			String index = countries.substring(0, 1);
			if (mapIndex.get(index) == null)
				mapIndex.put(index, (i + 7));
		}
	}

	/**
	 * Holder for the cell
	 * 
	 */
	static class Cell {
		public TextView country_name;
		public TextView iso_alpha_2;
		public TextView iso_alpha_3;
		public TextView dial_code;
		public LinearLayout sText;
		public CustomTextViewRegular alphabate;
	}

}