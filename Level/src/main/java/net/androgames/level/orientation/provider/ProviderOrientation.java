package net.androgames.level.orientation.provider;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import net.androgames.level.orientation.OrientationProvider;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;

/*
 *  This file is part of Level (an Android Bubble Level).
 *  <https://github.com/avianey/Level>
 *  
 *  Copyright (C) 2012 Antoine Vianey
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
	
	private float tmp;
    private float[] R = new float[16];
    private float[] outR = new float[16];
    private float[] LOC = new float[3];
	
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
	    
//	    yaw = event.values[0];
//        pitch = event.values[1];
//        roll = event.values[2];
//
//	    switch (displayOrientation) {
//	    case Surface.ROTATION_270:
//	    	pitch = - pitch;
//	    	roll = - roll;
//	    case Surface.ROTATION_90:
//	    	tmp = pitch;
//	    	pitch = - roll;
//	    	roll = tmp;
//	    	if (roll > 90) {
//	    		roll = 180 - roll;
//	    		pitch = - pitch - 180;
//	    	} else if (roll < -90) {
//	    		roll = - roll - 180;
//	    		pitch = 180 - pitch;
//	    	}
//	    	break;
//	    case Surface.ROTATION_180:
//	    	pitch = - pitch;
//	    	roll = - roll;
//	    case Surface.ROTATION_0:
//    	default:
//	    	break;
//	    }
	    
	    

        switch (displayOrientation) {
        case Surface.ROTATION_270:
            SensorManager.remapCoordinateSystem(
                    R, 
                    SensorManager.AXIS_MINUS_Y, 
                    SensorManager.AXIS_X, 
                    outR);
            break;
        case Surface.ROTATION_180:
            SensorManager.remapCoordinateSystem(
                    R, 
                    SensorManager.AXIS_MINUS_X, 
                    SensorManager.AXIS_MINUS_Y, 
                    outR);
            break;
        case Surface.ROTATION_90:
            SensorManager.remapCoordinateSystem(
                    R, 
                    SensorManager.AXIS_Y, 
                    SensorManager.AXIS_MINUS_X, 
                    outR);
            break;
        case Surface.ROTATION_0:
        default:
            SensorManager.remapCoordinateSystem(
                    R, 
                    SensorManager.AXIS_X, 
                    SensorManager.AXIS_Y, 
                    outR);
            break;
        }
        
        SensorManager.getOrientation(outR, LOC);
        
        Log.d("Level", "rotation " +
                "x("+df.format(outR[0])+","+df.format(outR[1])+","+df.format(outR[2])+") " +
                "y("+df.format(outR[4])+","+df.format(outR[5])+","+df.format(outR[6])+") " +
                "z("+df.format(outR[8])+","+df.format(outR[9])+","+df.format(outR[10])+")");
        
//        x2+y2 = 1;
        double length = Math.sqrt(outR[8]*outR[8]+outR[9]*outR[9]);
        double newX = (length == 0 ? 0 : outR[8] / length);

        Log.d("Level", "inclination port " + df.format(Math.asin(newX) * 360 / (2 * Math.PI))); // good
        
        // [0] compass
        pitch = (float) (LOC[1] * 180 / Math.PI);
        roll = - (float) (LOC[2] * 180 / Math.PI);
        
	    
	    
	    
	    
	}


    DecimalFormat df = new DecimalFormat("0.00");
	
    @Override
	protected List<Integer> getRequiredSensors() {
		return Arrays.asList(Integer.valueOf(Sensor.TYPE_ROTATION_VECTOR));
	}

}