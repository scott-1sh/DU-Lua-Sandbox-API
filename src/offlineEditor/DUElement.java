/*    
    DUElement.java 
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
package offlineEditor;

import duJavaAPI.BaseElement;

public  class DUElement { 
	public int id;
    public String name;
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



