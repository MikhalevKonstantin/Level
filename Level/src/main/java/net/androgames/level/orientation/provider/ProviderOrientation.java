package net.androgames.level.orientation.provider;

import java.util.Arrays;
import java.util.List;

import net.androgames.level.orientation.OrientationProvider;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

/*
 *  This file is part of Level (an Android Bubble Level).
 *  <https://github.com/avianey/Level>
 *  
 *  Copyright (C) 2014 Antoine Vianey
 *  
 *  Level is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Level is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Level. If not, see <http://www.gnu.org/licenses/>
 */
public class ProviderOrientation extends OrientationProvider {
	
	private static OrientationProvider provider;
	
	private ProviderOrientation() {
	    super();
	}
	
	public static OrientationProvider getInstance() {
		if (provider == null) {
			provider = new ProviderOrientation();
		}
		return provider;
	}

	protected void handleSensorChanged(SensorEvent event) {
	    SensorManager.getRotationMatrixFromVector(R, event.values);
	}
	
    @Override
	protected List<Integer> getRequiredSensors() {
		return Arrays.asList(Integer.valueOf(Sensor.TYPE_ROTATION_VECTOR));
	}

}