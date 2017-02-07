package texttime.android.app.texttime.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import texttime.android.app.texttime.DataModels.Country;
import texttime.android.app.texttime.R;


public class CountryListAdapter extends BaseAdapter {

	private Context context;
	List<Country> countries;
	LayoutInflater inflater;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param countries
	 */
	public CountryListAdapter(Context context, List<Country> countries) {
		super();
		this.context = context;
		this.countries = countries;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			cellView = inflater.inflate(R.layout.country_item, null);
			cell.country_name = (TextView) cellView.findViewById(R.id.country_name);
			cell.iso_alpha_2 = (TextView) cellView.findViewById(R.id.isoalpha2);
			cell.iso_alpha_3 = (TextView) cellView.findViewById(R.id.isoalpha3);
			cell.dial_code = (TextView) cellView.findViewById(R.id.dial_code);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}

		cell.country_name.setText(country.getName());
		cell.iso_alpha_2.setText(country.getCode());
		cell.dial_code.setText(country.getDialCode());
		cell.iso_alpha_3.setText("");
		return cellView;
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
	}

}