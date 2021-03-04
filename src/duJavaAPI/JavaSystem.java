/*    
    JavaSystem.java 
    Copyright (C) 2020 Stephane Boivin (Discord: Nmare418#6397)
    
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

import java.awt.MouseInfo;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import sandbox.execWindow;

public class JavaSystem {
   static execWindow eWindow;
   private boolean verboseJava = true;
   
   public JavaSystem(boolean pverboseJava, execWindow peWindow) {
	 eWindow = peWindow;
	 verboseJava = pverboseJava;
	 if(verboseJava) System.out.println("[JAVA] System created");
   }

   public void print(String txt) {
		System.out.println("[lua]: "+txt);
   }   
   
   public Object get(String[] param) {
	   String pcommand = param[0];
	   LuaTable result = new LuaTable();

	   try {
		    switch (pcommand) {
				case "getActionKeyName": // todo
			        return param[1];
				case "showScreen": // todo
					// param[1] = bool
			        return "";
				case "setScreen": // todo
					/* !!!!!!!!!!!!!!!! */
					
					// param[1] = content
			        return "";
				case "createWidgetPanel": // todo
					// param[1] = label
			        return "";
				case "destroyWidgetPanel": // todo
					// param[1] = panelId
			        return "";
				case "createWidget": // todo
					// param[1] = panelId
					// param[2] = type
			        return "";
				case "destroyWidget": // todo
					// param[1] = widgetId
			        return "";		        
				case "createData": // todo
					// param[1] = dataJson
			        return 1;		        
				case "destroyData": // todo
					// param[1] = dataId
			        return 1;    
				case "updateData": // todo
					// param[1] = dataId
					// param[2] = dataJson
			        return 1;    
				case "addDataToWidget": // todo
					// param[1] = dataId
					// param[2] = widgetId
			        return 1;    
				case "removeDataFromWidget": // todo
					// param[1] = dataId
					// param[2] = widgetId
			        return 1;    
				case "getMouseWheel": 
			        return eWindow.mouseWheel;
				case "getMouseDeltaX":
					MouseInfo.getPointerInfo().getLocation().getX();
			        return MouseInfo.getPointerInfo().getLocation().getX();
				case "getMouseDeltaY": 
			        return MouseInfo.getPointerInfo().getLocation().getY();
				case "getMousePosX": 
			        return MouseInfo.getPointerInfo().getLocation().getX();
				case "getMousePosY": 
			        return MouseInfo.getPointerInfo().getLocation().getY();
				case "lockView":
					eWindow.lockedView = !eWindow.lockedView; 
			        return null;
				case "isViewLocked": 
			        return eWindow.lockedView;
				case "freeze": 
					if(param[1].equals("1")) {
					   eWindow.frozen = true;
					} else {
					   eWindow.frozen = false;
					}
			        return null;
				case "isFrozen": 
			        return eWindow.frozen;
				case "getTime": 
			        return System.currentTimeMillis()/1000;
				case "getPlayerName":
					try {
						if(verboseJava && param[1] == null) System.out.println("[JAVA] Warning getPlayerName: null parameter");
						String name = eWindow.sandbox.worldPlayer.get(Integer.valueOf(param[1])-1).name;
	
						return name;
					} catch (Exception e) { // not found
						return "unreachable";
					}
			        
				case "getPlayerWorldPos": 
					result.set(1, CoerceJavaToLua.coerce(eWindow.sandbox.worldPlayer.get(Integer.valueOf(param[1])).worldPos[0]));
					result.set(2, CoerceJavaToLua.coerce(eWindow.sandbox.worldPlayer.get(Integer.valueOf(param[1])).worldPos[1]));
					result.set(3, CoerceJavaToLua.coerce(eWindow.sandbox.worldPlayer.get(Integer.valueOf(param[1])).worldPos[2]));
			        return result;
			}
	    } catch(Exception e){
	    	// pcommand
	    	if(verboseJava) System.out.println("[JAVA] Error in commande:"+pcommand);
	    	 throw new LuaError(e);
	    }
	    
		return "";
   }
}
