/*    
    Container.java 
    Copyright (C) 2020 Stephane Boivin (Discord: Nmare418#6397)
    
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.h2.jdbcx.JdbcDataSource;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import sandbox.execWindow;

public class Container extends BaseElement {
    public String html = "";
    int sizeX = 200;
    int sizeY = 100;
    
	public Container(int pid, String pname, int px, int py, boolean pverboseJava) {
		name = pname;
		x = px;
		y = py;		
		id = pid;

		verboseJava = pverboseJava;		

		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px, py, sizeX, sizeY);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(1,1, sizeX, sizeY);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();	
	 	setPicture(lblPic, "src/pictures/elements/Storage.png", 5, 5, 60, 70);
		panel.add(lblPic, 1, 0);
		
		// stats panel
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		panel.add(stats, 1, 0);		
		AddtoStatPanel("Content mass:", "0.0Kg");
		// AddtoStatPanel("Type:", coreType);		
			    
		// lua require and interface 
		urlAPI = "src/duElementAPI/container.lua";
        luaCall = name+" = createInterfaceContainer(\""+pid+"\", \""+name+"\")";		
        if(verboseJava) System.out.println("[JAVA] Container ["+name+"] created");	 		
	}

	@Override	
    public void update(String[] param) {
		Float weight = Float.valueOf(param[0]);
        
		// update stats
		panel.remove(stats);
		CreateStatPanel(name+" ("+id+")", sizeX, sizeY);		
		AddtoStatPanel("Cargo:", String.format("%.2fKg", weight));
		panel.add(stats, 1, 0);
	}

}
