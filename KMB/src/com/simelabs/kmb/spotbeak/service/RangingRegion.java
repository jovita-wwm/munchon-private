package com.simelabs.kmb.spotbeak.service;

import android.os.Messenger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.simelabs.kmb.spotbeak.Beacon;
import com.simelabs.kmb.spotbeak.Region;
import com.simelabs.kmb.spotbeak.Utils;
import com.simelabs.kmb.spotbeak.utils.L;


/**
 * 
 * @author Jovita
 *
 */
class RangingRegion
{
  private static final Comparator<Beacon> BEACON_ACCURACY_COMPARATOR = new Comparator()
  {
    public int compare(Beacon lhs, Beacon rhs)
    {
      return Double.compare(Utils.computeAccuracy(lhs), Utils.computeAccuracy(rhs));
    }

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
  };
  private final ConcurrentHashMap<Beacon, Long> beacons;
  final Region region;
  final Messenger replyTo;
  
  RangingRegion(Region region, Messenger replyTo)
  {
    this.region = region;
    this.replyTo = replyTo;
    this.beacons = new ConcurrentHashMap();
  }
  
  public final List<Beacon> getSortedBeacons()
  {
    ArrayList<Beacon> sortedBeacons = new ArrayList(this.beacons.keySet());
    Collections.sort(sortedBeacons, BEACON_ACCURACY_COMPARATOR);
    return sortedBeacons;
  }
  

  
  public final void processFoundBeacons(Map<Beacon, Long> beaconsFoundInScanCycle)
  {
    for (Map.Entry<Beacon, Long> entry : beaconsFoundInScanCycle.entrySet()) {
      if (Utils.isBeaconInRegion((Beacon)entry.getKey(), this.region))
      {
        this.beacons.remove(entry.getKey());
        this.beacons.put(entry.getKey(), entry.getValue());
      }
    }
  }
  
  public final void removeNotSeenBeacons(long currentTimeMillis)
  {
    Iterator<Map.Entry<Beacon, Long>> iterator = this.beacons.entrySet().iterator();
    while (iterator.hasNext())
    {
      Map.Entry<Beacon, Long> entry = (Map.Entry)iterator.next();
      if (currentTimeMillis - ((Long)entry.getValue()).longValue() > BeaconService.EXPIRATION_MILLIS)
      {
        L.v("Not seen lately: " + entry.getKey());
        iterator.remove();
      }
    }
  }
}
