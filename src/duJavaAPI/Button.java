/*    
    Button.java 
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

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import offlineEditor.execWindow;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Button extends BaseElement {
 	private JButton button = new JButton();
	public int status = 0;
	public String onClick = "";
	
	public Button(int pid, String pname, String plabel, int px, int py, String onClick, boolean pverboseJava) {

		name = pname;
		x = px;
		y = py;
		
		verboseJava = pverboseJava;	
		
		panel = new JPanel();		
		panel.setLayout(null);
		panel.setBounds(px,py,100,50);
		panel.setBackground(Color.black );
		panel.setBorder(LineBorder.createGrayLineBorder());
				
		// label
		JLabel  lblname = new JLabel(name);
		lblname.setForeground(Color.white );
		lblname.setBounds(8, 2, 100, 16);	
		panel.add(lblname, 1, 0);		
		
	  	// button 		
		button.setText(plabel);
		button.setBounds(3,20, 94,28);
		// button.setBorder(null);
		button.addActionListener(new ActionListener() { 
			                       @Override
								public void actionPerformed(ActionEvent e) {
			                    	sandbox.RUN(onClick, name+":click()");
			                    	if(verboseJava) System.out.println("[JAVA] Button " + name + " clicked"); 				   
				                   } 
				                });
		panel.add(button);
		
		// lua require and interface 
		urlAPI = "src/duElementAPI/button.lua";
        luaCall = pname+" = createInterfaceButton(\""+pid+"\", \""+pname+"\")";

        if(verboseJava) System.out.println("[JAVA] Button ["+name+"] created");	 		
	}
	
}
