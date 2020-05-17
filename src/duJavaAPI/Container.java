/*    
    Container.java 
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
import offlineEditor.execWindow;

public class Container extends BaseElement {
    public String html = "";
    int sizeX = 104;
    int sizeY = 100;
    
	public Container(int pid, String pname, int px, int py, boolean pverboseJava) {
		name = pname;
		x = px;
		y = py;		
		
		verboseJava = pverboseJava;		

		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px, py, sizeX, sizeY+24);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);

		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white);
		lblname.setBounds(8, 2, sizeX, 16);	
		panel.add(lblname, 1, 0);		
		
		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(2,22,100,100);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);

	 	// picture
	 	JLabel lblPic = new JLabel();		
		lblPic.setBounds(2,22,100,100);
		lblPic.setIcon(new ImageIcon("src/pictures/elements/Storage.png")); 
		panel.add(lblPic, 1, 0);
		
			    
		// lua require and interface 
		urlAPI = "src/duElementAPI/container.lua";
        luaCall = name+" = createInterfaceContainer(\""+pid+"\", \""+name+"\")";		
        if(verboseJava) System.out.println("[JAVA] Container ["+name+"] created");	 		
	}

}
