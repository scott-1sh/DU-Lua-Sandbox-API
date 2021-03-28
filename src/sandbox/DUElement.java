/*    
    DUElement.java 
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
package sandbox;

import duJavaAPI.BaseElement;

public  class DUElement { 
	public int id;
    public String name;
    public int X, Y; // moved X and Y.  Default = 0, 0
    public BaseElement element;
    
    public DUElement(int pid, String pname, BaseElement pelement) {
    	this.id = pid;
    	this.name = pname;
    	this.element = pelement;
    	// System.out.println(pid+") "+name+" type="+GetType());  -- return duJavaAPI.Screen
    }  
        
    public String GetType() {     	
    	return element.getClass().getName();    	
    }


}




