/*    
    Database.java 
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
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class Database {
   public static List<Player> player = new ArrayList<>();	
   public static List<Player> unreachable = new ArrayList<>();	
   public static List<Construct> construct = new ArrayList<>();	
   
   public Database(List<Construct> pConstruct, List<Player> pPlayer,  boolean pverboseJava) {
      
	  construct = pConstruct;
      player = pPlayer;	   
	  if(pverboseJava) System.out.println("[JAVA] Database created");

  }

  @SuppressWarnings("unchecked")
  public Object get(String[] param) {    	
	String pcommand = param[0];
	int id = Integer.valueOf(param[1]);
 
	if(pcommand.equals("database.getPlayer")) {
		// if not found return "unreachable"
		for(int i=0; i<player.size(); i++) {
            if(player.get(i).id == id) {
                JSONObject obj = new JSONObject();
                obj.put("id", player.get(i).id );
                obj.put("name", player.get(i).name);
                obj.put("worldPos0", player.get(i).worldPos[0]);
                obj.put("worldPos1", player.get(i).worldPos[1]);
                obj.put("worldPos2", player.get(i).worldPos[2]); 
                // System.out.println(obj.toJSONString()) ;                    
            	return obj.toJSONString();
            }                
		}		
        JSONObject obj = new JSONObject();
        obj.put("id", unreachable.get(1).id );
        obj.put("name", unreachable.get(1).name);
        obj.put("worldPos0", unreachable.get(1).worldPos[0]);
        obj.put("worldPos1", unreachable.get(1).worldPos[1]);
        obj.put("worldPos2", unreachable.get(1).worldPos[2]); 
        return obj.toString();
	}
 
	return "";
   }

}
