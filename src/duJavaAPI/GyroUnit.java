/*    
    GyroUnit.java 
    Copyright (C) 2020 Stephane Boivin (Devgeek studio enr.)
    
    This file is part of "DU offline sandbox API".

    "DU offline sandbox API" is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    "DU offline sandbox API" is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with "DU offline sandbox API".  If not, see <https://www.gnu.org/licenses/>.
*/
package duJavaAPI;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import offlineEditor.execTimer;

public class GyroUnit extends BaseElement {
 	private JLabel lblPic = new JLabel();
	public int status = 0;
	public int id = 0;
	public int[] localUp = {0,0,0};
	public int[] localForward = {0,0,0};
	public int[] localRight = {0,0,0};
	public int[] worldUp = {0,0,0};
	public int[] worldForward = {0,0,0};
	public int[] worldRight = {0,0,0};
	public float pitch = 0;
	public float roll = 0;
	public float yaw = 0;
	
	public GyroUnit(int pid, String pname, int px, int py, boolean pverboseJava) {
		
		name = pname;
		x = px;
		y = py;
		id = pid;

		verboseJava = pverboseJava;
			
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px,py,104,154);
//		panel.setBackground(Color.white );
		panel.setBorder(LineBorder.createGrayLineBorder());
		panel.setBackground(Color.black);
		
		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, 105, 16);	
		panel.add(lblname, 1, 0);		
		
		// picture
		// lblPic.setBounds(3,21,94,75);
		// lblPic.setIcon(new ImageIcon("src/pictures/elements/Gyro.png")); 			
		// panel.add(lblPic);
		
		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(2,22,100,130);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0); 

	 	// picture
	 	JLabel lblPic = new JLabel();		
		lblPic.setBounds(2,22,100,130);
		lblPic.setIcon(new ImageIcon("src/pictures/elements/Gyro.png")); 
		panel.add(lblPic, 1, 0);
		
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
