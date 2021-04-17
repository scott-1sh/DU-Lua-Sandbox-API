/*    
    Construct.java 
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

public class Construct {
	public int id;
	public double[] pos = new double[3];  
	public double[] size = new double[3];
	public double[] speed = new double[3];
	public int owner;
	public String name;
	public String ctype;
	public double mass;
	public boolean transponder;

	// events
    public boolean outOfRange = false;
	
	public Construct(int pid, double[] ppos, double[] psize, double[] pspeed, int powner, String pname, String pctype, double pmass, boolean ptransponder) {
		this.id = pid;
		this.pos = ppos;  
		this.size = psize;
		this.speed = pspeed;
		this.owner = powner;
		this.name = pname;
		this.ctype = pctype;
		this.mass = pmass;
		this.transponder = ptransponder;
		this.outOfRange = false;
	}

}
