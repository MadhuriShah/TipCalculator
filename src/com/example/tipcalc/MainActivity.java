package com.example.tipcalc;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();	
	private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
	private double billAmount = 0.0;
	private double customPercent = 0.18;
	private double taxPercent = 13.99;
	private int tperson = 1;
	private EditText taxV,personV;
	private TextView tax15TextView;
	boolean tipBefore = true;
	private TextView amountDisplayTextView;
	private TextView percentCustomTextView;
	private TextView tip15TextView, sp15TextView, total15TextView;
	private TextView totalCustomTextView,tipCustomTextView, taxCustomTextView, spCustomTextView;
	private RadioGroup taxchoice;
	private RadioButton before, after;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
		percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
		tip15TextView = (TextView)findViewById(R.id.tip15TextView);
		total15TextView = (TextView)findViewById(R.id.total15TextView);
		tipCustomTextView =(TextView)findViewById(R.id.tipCustomTextView);
		totalCustomTextView = (TextView)findViewById(R.id.totalCustomTextView);
		sp15TextView = (TextView)findViewById(R.id.sp15TextView);
		spCustomTextView = (TextView)findViewById(R.id.spCustomTextView);
		taxchoice = (RadioGroup)findViewById(R.id.tax);
		before = (RadioButton)findViewById(R.id.before);
		after = (RadioButton)findViewById(R.id.after);
		taxV = (EditText) findViewById(R.id.taxPercentText);
		tax15TextView = (TextView) findViewById(R.id.tax15TextView);
		taxCustomTextView =(TextView)findViewById(R.id.taxCustomTextView);
		
		
		amountDisplayTextView.setText(currencyFormat.format(billAmount));
		
		taxchoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.after){
					tipBefore=false;
				}
				else{
					tipBefore=true;
				}
				updateStandard();
				updateCustom();
			}
		});
		
		updateStandard();
		updateCustom();
		
		EditText amountEditText = (EditText)findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(amountEditTextWatcher);
		
		SeekBar customTipSeekbar = (SeekBar) findViewById(R.id.customTipSeekBar);
		customTipSeekbar.setOnSeekBarChangeListener(customSeekBarListener);
		
		taxV.addTextChangedListener(taxEditTextWatcher);
		
		personV = (EditText)findViewById(R.id.tpersonEditText);
		personV.addTextChangedListener(personTextWatcher);
		
	}
	
	private TextWatcher personTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			try{
				tperson = Integer.parseInt(s.toString());
			}
			catch(NumberFormatException e)
			{
				tperson = 1;			
			}
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private TextWatcher taxEditTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			try{
				taxPercent = Double.parseDouble(s.toString());
			}
			catch(NumberFormatException e)
			{
				taxPercent = 0.0;			
			}
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private TextWatcher amountEditTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			try{
				billAmount = Double.parseDouble(s.toString()) / 100.0;
			}
			catch(NumberFormatException e)
			{
				billAmount = 0.0;			
			}
			
			amountDisplayTextView.setText(currencyFormat.format(billAmount));
			updateStandard();
			updateCustom();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};

	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			customPercent = progress / 100.0;
			updateCustom();
			
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void updateStandard(){
		double tax = (billAmount * taxPercent)/100 ;
		double fifteenTip;
		if(tipBefore==true){
			fifteenTip = (billAmount) * 0.15;
			
		}
		else{
			fifteenTip = (billAmount + tax) * 0.15;
		}
		double fifteenTotal = billAmount + fifteenTip + tax;
		tip15TextView.setText(currencyFormat.format(fifteenTip));
		tax15TextView.setText(currencyFormat.format(tax));
		total15TextView.setText(currencyFormat.format(fifteenTotal));
		sp15TextView.setText(currencyFormat.format(fifteenTotal/tperson));
	}
	
	private void updateCustom(){
		double customTip;
		percentCustomTextView.setText(percentFormat.format(customPercent));
		double tax = (billAmount * taxPercent)/100 ;
		if(tipBefore==true){
			customTip = billAmount * customPercent;	
		}
		else{		
			customTip = (billAmount + tax) * customPercent;
		}
		double customAmount = billAmount + customTip + tax;
		tipCustomTextView.setText(currencyFormat.format(customTip));
		taxCustomTextView.setText(currencyFormat.format(tax));
		totalCustomTextView.setText(currencyFormat.format(customAmount));
		spCustomTextView.setText(currencyFormat.format(customAmount/tperson));
	}
}
