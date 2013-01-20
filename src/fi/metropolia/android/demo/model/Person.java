package fi.metropolia.android.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

	private String mName = null;
	private int mAge = -1;
	
	public Person(String name, int age){
		mName = name;
		mAge = age;
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public int getAge() {
		return mAge;
	}

	public void setAge(int age) {
		mAge = age;
	}

	@Override
	public String toString() {
		return mName + " : " + String.valueOf(mAge);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mAge);
		dest.writeString(mName);
	}

	
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
		public Person createFromParcel(Parcel in) {
			return new Person(in);
		}

		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
	
	private Person(Parcel in){
		mAge = in.readInt();
		mName = in.readString();
	}
	
}
