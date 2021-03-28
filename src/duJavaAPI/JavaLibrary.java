/*    
    JavaLibrary.java 
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
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class JavaLibrary {

   public JavaLibrary(boolean pverboseJava) {
    	
      if(pverboseJava) System.out.println("[JAVA] Library created");	 		    	
    	
   }

   public Object get(String[] param) {    	
		String pcommand = param[0];
	    LuaTable result = new LuaTable();

	    switch (pcommand) {
			case "systemResolution3":
				result.set(1, CoerceJavaToLua.coerce(3));
				result.set(2, CoerceJavaToLua.coerce(2));
				result.set(3, CoerceJavaToLua.coerce(1));
		        return result;
			case "systemResolution2":
				result.set(1, CoerceJavaToLua.coerce(1));
				result.set(2, CoerceJavaToLua.coerce(2));
		        return result;
		}

		return "";
   }
}