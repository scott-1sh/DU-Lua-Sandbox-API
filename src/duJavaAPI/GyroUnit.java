/*    
    GyroUnit.java 
    Copyright (C) 2021 Stephane Boivin (Discord: Nmare418#6397)
    
    This file is part of "DU lua sandbox API".

    "DU lua sandbox API" is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    "DU lua sandbox API" is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with "DU lua sandbox API".  If not, see <https://www.gnu.org/licenses/>.
*/
package duJavaAPI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import sandbox.execTimer;

public class GyroUnit extends BaseElement {
 	private JLabel lblPic = new JLabel();
	public int status = 0;
	public int[] localUp = {0,0,0};
	public int[] localForward = {0,0,0};
	public int[] localRight = {0,0,0};
	public int[] worldUp = {0,0,0};
	public int[] worldForward = {0,0,0};
	public int[] worldRight = {0,0,0};
	public float pitch = 0;
	public float roll = 0;
	public float yaw = 0;
    int sizeX = 200;
    int sizeY = 100;
    
	public GyroUnit(int pid, String pname, int px, int py, boolean pverboseJava) {
		
		name = pname;
		x = px;
		y = py;
		id = pid;

		verboseJava = pverboseJava;
			
		// create panel
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);	
		panel.setBounds(x, y, sizeX, sizeY);
		
		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(1, 1, sizeX-3, sizeY-3);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();
	 	setPicture(lblPic, "src/pictures/elements/Gyro.png", 7, 7, 55, 65);
		panel.add(lblPic, 1, 0);

		// stats panel
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		panel.add(stats, 1, 0);
		AddtoStatPanel("Local up:", "0, 0, 0");
		AddtoStatPanel("World up:", "0, 0, 0");
		AddtoStatPanel("p/r/y:", "0/0/0 deg");
		// AddtoStatPanel("Elements:", Integer.toString(GetElementsCount()));
				
		// lua require and interface 
		urlAPI = "src/duElementAPI/gyro.lua";		                    
        luaCall = pname+" = createInterfaceGyro(\""+pid+"\", \""+pname+"\")";
		
        if(verboseJava) System.out.println("[JAVA] Gyro ["+pname+"] created");	 		
	}

	@Override
	public void update(String[] param) {
		// {State,name}		
		if(verboseJava) System.out.println("[JAVA] "+param[1]+" state set to  "+param[0]); 
	}

	@Override	
    public Object get(String[] param) {    	
		String pcommand = param[0];
        LuaTable result = new LuaTable();

        switch (pcommand) {
			case "localUp":
				result.set(1, CoerceJavaToLua.coerce(localUp[0]));
				result.set(2, CoerceJavaToLua.coerce(localUp[1]));
				result.set(3, CoerceJavaToLua.coerce(localUp[2]));
		        return result;
			case "localForward":
				result.set(1, CoerceJavaToLua.coerce(localForward[0]));
				result.set(2, CoerceJavaToLua.coerce(localForward[1]));
				result.set(3, CoerceJavaToLua.coerce(localForward[2]));
		        return result;
			case "localRight":
				result.set(1, CoerceJavaToLua.coerce(localRight[0]));
				result.set(2, CoerceJavaToLua.coerce(localRight[1]));
				result.set(3, CoerceJavaToLua.coerce(localRight[2]));
		        return result;
			case "worldUp":
				result.set(1, CoerceJavaToLua.coerce(worldUp[0]));
				result.set(2, CoerceJavaToLua.coerce(worldUp[1]));
				result.set(3, CoerceJavaToLua.coerce(worldUp[2]));
		        return result;
			case "worldForward":
				result.set(1, CoerceJavaToLua.coerce(worldForward[0]));
				result.set(2, CoerceJavaToLua.coerce(worldForward[1]));
				result.set(3, CoerceJavaToLua.coerce(worldForward[2]));
		        return result;
			case "worldRight":
				result.set(1, CoerceJavaToLua.coerce(worldRight[0]));
				result.set(2, CoerceJavaToLua.coerce(worldRight[1]));
				result.set(3, CoerceJavaToLua.coerce(worldRight[2]));
		        return result;
			case "getPitch":
				return pitch;
			case "getRoll":
				return roll;
			case "getYaw":
				return yaw;
		}

	return "";
  }
	
}
