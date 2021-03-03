/*    
    Button.java 
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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import sandbox.execWindow;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Button extends BaseElement {
 	private JButton button = new JButton();
	public int status = 0;
	public String onClick = "";
    int sizeX = 100;
    int sizeY = 50;
    
	public Button(int pid, String pname, String plabel, int px, int py, String onClick, boolean pverboseJava) {

		name = pname;
		x = px;
		y = py;
		id = pid;
		
		verboseJava = pverboseJava;	
		
		// create panel
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBorder(LineBorder.createBlackLineBorder());
		panel.setBackground(Color.black);		
		panel.setBounds(x, y, sizeX, sizeY);
		
		// white frame
		JPanel lblPicWhite = new JPanel();		
	 	lblPicWhite.setBounds(1, 1, sizeX-3, sizeY-3);
	 	lblPicWhite.setBackground(Color.white);
		panel.add(lblPicWhite, 1, 0);
		
	  	// button 		
		button.setText(plabel);
		button.setBounds(5, 5, sizeX-16, 20);
		button.setBackground(new Color(59, 59, 59));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setFont(new Font("Arial", Font.BOLD, 12));
		button.addActionListener(new ActionListener() { 
			                       @Override
								   public void actionPerformed(ActionEvent e) {
			                    	   sandbox.RUN(onClick, name+":click()");
			                    	   if(verboseJava) System.out.println("[JAVA] Button " + name + " clicked"); 				   
				                   } 
				                });
		panel.add(button, 1, 0);
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/button.lua";
        luaCall = pname+" = createInterfaceButton(\""+pid+"\", \""+pname+"\")";

        if(verboseJava) System.out.println("[JAVA] Button ["+name+"] created");	 		
	}
	
}
