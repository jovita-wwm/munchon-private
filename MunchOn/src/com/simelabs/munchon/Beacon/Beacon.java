package com.simelabs.munchon.Beacon;




import java.text.DecimalFormat;

import com.simelabs.munchon.Beacon.internal.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
/**
 * 
 * @author Jovita
 *
 */
public class Beacon
  implements Parcelable,Comparable
{
  private final String proximityUUID;
  private final String name;
  private final String macAddress;
  private final int major;
  private final int minor;
  private final int measuredPower;
  private final int rssi;
  double accuracy;
  String table_name;
  
  
  
  public String getTable_name() {
	return table_name;
}

public void setTable_name(String table_name) {
	this.table_name = table_name;
}

public static Parcelable.Creator<Beacon> getCreator() {
	return CREATOR;
}

public double getAccuracy() {
	return accuracy;
}

public void setAccuracy(double accuracy) {
	this.accuracy = accuracy;
}

public Beacon(String proximityUUID, String name, String macAddress, int major, int minor, int measuredPower, int rssi)
  {
  //  this.proximityUUID = Utils.normalizeProximityUUID(proximityUUID);
	  this.proximityUUID=proximityUUID;
    this.name = name;
    this.macAddress = macAddress;
    this.major = major;
    this.minor = minor;
    this.measuredPower = measuredPower;
    this.rssi = rssi;
  }
  
  public String getProximityUUID()
  {
    return this.proximityUUID;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getMacAddress()
  {
    return this.macAddress;
  }
  
  public int getMajor()
  {
    return this.major;
  }
  
  public int getMinor()
  {
    return this.minor;
  }
  
  public int getMeasuredPower()
  {
    return this.measuredPower;
  }
  
  public int getRssi()
  {
    return this.rssi;
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("macAddress", this.macAddress).add("proximityUUID", this.proximityUUID).add("major", this.major).add("minor", this.minor).add("measuredPower", this.measuredPower).add("rssi", this.rssi).toString();
  }
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    Beacon beacon = (Beacon)o;
    if (this.major != beacon.major) {
      return false;
    }
    if (this.minor != beacon.minor) {
      return false;
    }
   // return this.proximityUUID.equals(beacon.proximityUUID);
    return true;
  }
  
  public int hashCode()
  {
    int result = this.proximityUUID.hashCode();
    result = 31 * result + this.major;
    result = 31 * result + this.minor;
    return result;
  }
  
  public static final Parcelable.Creator<Beacon> CREATOR = new Parcelable.Creator()
  {
    public Beacon createFromParcel(Parcel source)
    {
      return new Beacon(source);
    }
    
    public Beacon[] newArray(int size)
    {
      return new Beacon[size];
    }
  };
  
  private Beacon(Parcel parcel)
  {
    this.proximityUUID = parcel.readString();
    this.name = parcel.readString();
    this.macAddress = parcel.readString();
    this.major = parcel.readInt();
    this.minor = parcel.readInt();
    this.measuredPower = parcel.readInt();
    this.rssi = parcel.readInt();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel dest, int flags)
  {
    dest.writeString(this.proximityUUID);
    dest.writeString(this.name);
    dest.writeString(this.macAddress);
    dest.writeInt(this.major);
    dest.writeInt(this.minor);
    dest.writeInt(this.measuredPower);
    dest.writeInt(this.rssi);
  }

@Override
public int compareTo(Object b) {
	// TODO Auto-generated method stub
	
	Beacon f = (Beacon)b;
	double old = Double.parseDouble(new DecimalFormat("#.#").format(accuracy));
	double fd=Double.parseDouble(new DecimalFormat("#.#").format(f.accuracy));
	
     if (old > fd) {
         return 1;
     }
     else if (old <fd) {
         return -1;
     }
     else {
         return 0;
     }
}
}
