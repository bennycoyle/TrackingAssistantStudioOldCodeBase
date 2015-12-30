package com.bc.datepreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

@SuppressLint("SimpleDateFormat")
public class DatePreference extends DialogPreference implements DatePicker.OnDateChangedListener {

	  private String dateString;
	  private String changedValueCanBeNull;
	  private DatePicker datePicker;

	  public DatePreference(Context context, AttributeSet attrs, int defStyle) {
		  super(context, attrs, defStyle);
	  }

	  public DatePreference(Context context, AttributeSet attrs) {
		  super(context, attrs);
	  }

	  @Override
	  protected View onCreateDialogView() {
		  this.datePicker = new DatePicker(getContext());
		  datePicker.setCalendarViewShown(false);
		  Calendar calendar = getDate();
		  datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
		  return datePicker;
	  }

	  public Calendar getDate() {
		  try {
			  Date date = formatter().parse(defaultValue());
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(date);
			  return cal;
		  } catch (java.text.ParseException e) {
			  return defaultCalendar();
		  }
	  }

	  public void setDate(String dateString) {
		  this.dateString = dateString;
	  }

	  @SuppressLint("SimpleDateFormat")
	  public static SimpleDateFormat formatter() {
		  return new SimpleDateFormat("yyyy.MM.dd");
	  }

	  public static SimpleDateFormat summaryFormatter() {
		  return new SimpleDateFormat("MMMM dd, yyyy");
	  }

	  @Override
	  protected Object onGetDefaultValue(TypedArray a, int index) {
		  return a.getString(index);
	  }

	  @Override
	  protected void onSetInitialValue(boolean restoreValue, Object def) {
		  if (restoreValue) {
			  this.dateString = getPersistedString(defaultValue());
			  setTheDate(this.dateString);
		  } else {
			  boolean wasNull = this.dateString == null;
			  setDate((String) def);
			  if (!wasNull)
				  persistDate(this.dateString);
		  }
	  }

	  @Override
	  protected Parcelable onSaveInstanceState() {
		  if (isPersistent())
			  return super.onSaveInstanceState();
		  else
			  return new SavedState(super.onSaveInstanceState());
	  }

	  @Override
	  protected void onRestoreInstanceState(Parcelable state) {
		  if (state == null || !state.getClass().equals(SavedState.class)) {
			  super.onRestoreInstanceState(state);
			  setTheDate(((SavedState) state).dateValue);
		  } else {
			  SavedState s = (SavedState) state;
			  super.onRestoreInstanceState(s.getSuperState());
			  setTheDate(s.dateValue);
		  }
	  }

	  public void onDateChanged(DatePicker view, int year, int month, int day) {
		  Calendar selected = new GregorianCalendar(year, month, day);
		  this.changedValueCanBeNull = formatter().format(selected.getTime());
	  }

	  @Override
	  protected void onDialogClosed(boolean shouldSave) {
		  if (shouldSave && this.changedValueCanBeNull != null) {
			  setTheDate(this.changedValueCanBeNull);
			  this.changedValueCanBeNull = null;
		  }
	  }

	  private void setTheDate(String s) {
		  setDate(s);
		  persistDate(s);
	  }

	  private void persistDate(String s) {
		  persistString(s);
		  setSummary(summaryFormatter().format(getDate().getTime()));
	  }

	  public static Calendar defaultCalendar() {
		  return new GregorianCalendar(1970, 0, 1);
	  }

	  public static String defaultCalendarString() {
		  return formatter().format(defaultCalendar().getTime());
	  }

	  private String defaultValue() {
		  if (this.dateString == null)
			  setDate(defaultCalendarString());
		  return this.dateString;
	  }

	  @SuppressWarnings("deprecation")
	  @Override
	  public void onClick(DialogInterface dialog, int which) {
		  super.onClick(dialog, which);
		  datePicker.clearFocus();
		  onDateChanged(datePicker, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		  onDialogClosed(which == DialogInterface.BUTTON1);
	  }

	  public static Calendar getDateFor(SharedPreferences preferences, String field) {
		  Date date = stringToDate(preferences.getString(field, defaultCalendarString()));
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	  }

	  private static Date stringToDate(String dateString) {
		  try {
			  return formatter().parse(dateString);
		  } catch (ParseException e) {
			  return defaultCalendar().getTime();
		  }
	  }

	  private static class SavedState extends BaseSavedState {
		  String dateValue;

		  public SavedState(Parcel p) {
			  super(p);
			  dateValue = p.readString();
		  }

		  public SavedState(Parcelable p) {
			  super(p);
		  }

		  @Override
		  public void writeToParcel(Parcel out, int flags) {
			  super.writeToParcel(out, flags);
			  out.writeString(dateValue);
		  }

		  @SuppressWarnings("unused")
		  public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
		      public SavedState createFromParcel(Parcel in) {
		    	  return new SavedState(in);
		      }
	
		      public SavedState[] newArray(int size) {
		    	  return new SavedState[size];
		      }
		  };
	  }	
}