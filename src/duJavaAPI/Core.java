/*    
    Core.java 
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

import sandbox.DUElement;
import sandbox.PreLoad;
import sandbox.execLUA;
import sandbox.execWindow;

public class Core extends BaseElement {
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
	private int constructId;
    private int sizeX = 200;
    private int sizeY = 100;
    private String coreType = "";
    private int coreSize = 16;
    public double g = 9;
    public float mass; 
	
	public Core(PreLoad preload, int pid, int pconstructId, int pcoreSize, String pcoreType, Double pg, int px, int py, boolean pverboseJava) {
		constructId = pconstructId;
		name = "core";
		x = px;
		y = py;
		id = pid;
		coreSize = pcoreSize;
		coreType = pcoreType;
		g = pg;
		verboseJava = pverboseJava;
		mass = (660*coreSize);
			
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px, py, sizeX, sizeY);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(1, 1, sizeX, sizeY);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();		
	 	setPicture(lblPic, "src/pictures/elements/Core.png", 5, 0, 80, 80);
		panel.add(lblPic, 1, 0);

		// stats panel
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		panel.add(stats, 1, 0);
		AddtoStatPanel("size:", Integer.toString(coreSize));
		AddtoStatPanel("Type:", coreType);
		// AddtoStatPanel("Elements:", Integer.toString(GetElementsCount()));
 	   AddtoStatPanel("Construct: ", pconstructId +" - "+ preload.worldConstruct.get(pconstructId).name);

		
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/core.lua";
        luaCall = "core = createInterfaceCore(\""+pid+"\", \"core\")";

        if(verboseJava) System.out.println("[JAVA] Core created");	 		
	}

	private int GetElementsCount() {
		int count = 0;
		for (DUElement elem : execWindow.  sandbox.elements ) {
			   if(elem == null) continue;
			   count++;
			 }		
		return count;
	}
	
	
	@Override
	public void update(String[] param) {
		// {State,name}		
		if(verboseJava) System.out.println("[JAVA] "+param[1]+" state set to  "+param[0]); 
	}

	@Override
    public Object get(String[] param) {    	
		String pcommand = param[0];
		String objName = "";
        LuaTable result = new LuaTable();

        switch (pcommand) {
			case "ConstructMass": // to upgrade,  
		        return this.mass*coreSize;
			case "ConstructIMass": // to upgrade
		        return 100;
			case "ConstructCrossSection": // to upgrade
		        return coreSize;
			case "MaxKinematicsParameters": // todo
				result.set(1, CoerceJavaToLua.coerce(1));
				result.set(2, CoerceJavaToLua.coerce(1));
				result.set(3, CoerceJavaToLua.coerce(1));
				result.set(4, CoerceJavaToLua.coerce(1));
		        return result;
			case "ConstructWorldPos":
				if(execLUA.worldConstruct == null) {
					System.out.println("[JAVA] Critical error.  ConstructWorldPos need to setup world constructs. Use the SetupsetupDatabase function in the preload file. ");
					return null;
				}
				result.set(1, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).pos[0]));
				result.set(2, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).pos[1]));
				result.set(3, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).pos[2]));
				return result;
			case "ConstructId":
		        return constructId;
			case "WorldAirFrictionAngularAcceleration": // todo
				result.set(1, CoerceJavaToLua.coerce(1));
				result.set(2, CoerceJavaToLua.coerce(1));
				result.set(3, CoerceJavaToLua.coerce(1));
		        return result;
			case "WorldAirFrictionAcceleration": // todo
				result.set(1, CoerceJavaToLua.coerce(1));
				result.set(2, CoerceJavaToLua.coerce(1));
				result.set(3, CoerceJavaToLua.coerce(1));
		        return result;
			case "spawnNumberSticker": // low priority
		        return 0;
			case "spawnArrowSticker": // low priority
		        return 0;
			case "deleteSticker": // low priority
		        return 0;
			case "moveSticker": // low priority
		        return 0;
			case "rotateSticker": // low priority
		        return 0;
			case "deleteSticker(index)": // low priority
		        return 0;
			case "ElementList":
				 for (DUElement elem : sandbox.elements ) {
				   if(elem == null) continue;
				   result.set(elem.id, CoerceJavaToLua.coerce(elem.id));
				 }
		        return result;
			case "ElementName":				
				return sandbox.elements[Integer.valueOf(param[1])].element.name;		
			case "ElementType":
				objName = sandbox.elements[Integer.valueOf(param[1])].element.name;
				return execLUA.globals.get(objName).get("class").toString();
			case "ElementHitPoints":
				objName = sandbox.elements[Integer.valueOf(param[1])].element.name;
				return execLUA.globals.get(objName).get("hitPoints").toString(); 
			case "ElementMaxHitPoints":
				objName = sandbox.elements[Integer.valueOf(param[1])].element.name;
				return execLUA.globals.get(objName).get("maxHitPoints").toString(); 
			case "ElementMass":
				objName = sandbox.elements[Integer.valueOf(param[1])].element.name;
				return execLUA.globals.get(objName).get("mass").toString(); 
			case "Altitude":
				return execLUA.worldConstruct.get(constructId).pos[2];
			case "g":
		        return this.g;
			case "WorldGravity":
		        return this.g;
			case "WorldVertical":
		        return this.g;
			case "AngularVelocity": // todo
		        return this.g;
			case "WorldAngularVelocity": // todo
		        return this.g;
			case "AngularAcceleration": // todo
		        return this.g;
			case "WorldAngularAcceleration": // todo
		        return this.g;
			case "Velocity":
				if(execLUA.worldConstruct == null) {
					System.out.println("[JAVA] Critical error.  core.Velocity need to setup world constructs. Use the SetupsetupDatabase function in the preload file. ");
					return null;
				}				
				result.set(1, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[0]));
				result.set(2, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[1]));
				result.set(3, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[2]));
		        return result;
			case "WorldVelocity":
				if(execLUA.worldConstruct == null) {
					System.out.println("[JAVA] Critical error.  core.worldConstruct need to setup world constructs. Use the SetupsetupDatabase function in the preload file. ");
					return null;
				}				
				result.set(1, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[0]));
				result.set(2, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[1]));
				result.set(3, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[2]));
		        return result;
			case "WorldAcceleration":
				if(execLUA.worldConstruct == null) {
					System.out.println("[JAVA] Critical error.  core.worldAcceleration need to setup world constructs. Use the SetupsetupDatabase function in the preload file. ");
					return null;
				}						
				result.set(1, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[0]));
				result.set(2, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[1]));
				result.set(3, CoerceJavaToLua.coerce(execLUA.worldConstruct.get(constructId).speed[2]));
		        return result;
			case "ConstructOrientationUp":
				result.set(1, CoerceJavaToLua.coerce(worldUp[0]));
				result.set(2, CoerceJavaToLua.coerce(worldUp[1]));
				result.set(3, CoerceJavaToLua.coerce(worldUp[2]));
		        return result;
			case "ConstructOrientationRight":
				result.set(1, CoerceJavaToLua.coerce(worldRight[0]));
				result.set(2, CoerceJavaToLua.coerce(worldRight[1]));
				result.set(3, CoerceJavaToLua.coerce(worldRight[2]));
		        return result;
			case "ConstructOrientationForward":
				result.set(1, CoerceJavaToLua.coerce(worldForward[0]));
				result.set(2, CoerceJavaToLua.coerce(worldForward[1]));
				result.set(3, CoerceJavaToLua.coerce(worldForward[2]));
		        return result;
			case "ConstructWorldOrientationUp":
				result.set(1, CoerceJavaToLua.coerce(worldUp[0]));
				result.set(2, CoerceJavaToLua.coerce(worldUp[1]));
				result.set(3, CoerceJavaToLua.coerce(worldUp[2]));
		        return result;
			case "ConstructWorldOrientationRight":
				result.set(1, CoerceJavaToLua.coerce(worldRight[0]));
				result.set(2, CoerceJavaToLua.coerce(worldRight[1]));
				result.set(3, CoerceJavaToLua.coerce(worldRight[2]));
		        return result;
			case "ConstructWorldOrientationForward":
				result.set(1, CoerceJavaToLua.coerce(worldForward[0]));
				result.set(2, CoerceJavaToLua.coerce(worldForward[1]));
				result.set(3, CoerceJavaToLua.coerce(worldForward[2]));
		        return result;
		}

	return "";
  }
	
}
