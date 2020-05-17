--[[    
    buttonManager_start.lua 
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
]]

screen1.activate()                                                                                                                                                                     

svgBackground = [[                                                                                                                                                                     
<svg width="100vw" height="100vh">                                                                                                                                                     
  <rect width="100vw" height="100vh" style="fill:rgb(0,0,200);stroke-width:10;stroke:rgb(0,0,135)" />                                                                                  
</svg>                                                                                                                                                                                 
]]                                                                                                                                                                                     

htmlContent = [[                                                                                                                                                                       
<html>                                                                                                                                                                                 
<head>                                                                                                                                                                                 
<style>                                                                                                                                                                                
  .btn { border-radius:1.7vh 0 1.7vh 0;                                                                                                                                                
            background: #000055;                                                                                                                                                       
            border:none;                                                                                                                                                               
            color:#AAAAFF;                                                                                                                                                             
            font: normal 3vh Verdana;                                                                                                                                                  
            padding:.2vw 0 .2vw 0;                                                                                                                                                     
            border: .6vw solid #000044;                                                                                                                                                
            text-align: center;                                                                                                                                                        
            box-shadow: .4vh .4vw .4vh rgba(0, 0, 0, 0.35);                                                                                                                            
            }                                                                                                                                                                          
  .btnHover { border-radius:1.7vh 0 1.7vh 0;                                                                                                                                           
            background: #000055;                                                                                                                                                       
            border:none;                                                                                                                                                               
            color:#FFFFFF;                                                                                                                                                             
            font: normal 3vh Verdana;                                                                                                                                                  
            padding:.2vw 0 .2vw 0;                                                                                                                                                     
            border: .6vw solid #4444FF;                                                                                                                                                
            text-align: center;                                                                                                                                                        
            box-shadow: .4vh .4vw .4vh rgba(0, 0, 0, 0.35);                                                                                                                            
           }                                                                                                                                                                           
  .btnActif { border-radius:1.7vh 0 1.7vh 0;                                                                                                                                           
            background: #000021;                                                                                                                                                       
            border:none;                                                                                                                                                               
            color:#FFFFFF;                                                                                                                                                             
            font: normal 3vh Verdana;                                                                                                                                                  
            padding:.2vw 0 .2vw 0;                                                                                                                                                     
            border: .6vw solid #000000;                                                                                                                                                
            text-align: center;                                                                                                                                                        
            box-shadow: .2vh .2vw .2vh rgba(0, 0, 0, 0.35);                                                                                                                            
           }                                                                                                                                                                           
   </style>                                                                                                                                                                            
</head>                                                                                                                                                                                
<body>                                                                                                                                                                                 
<br/><br/>                                                                                                                                                                             
%s                                                                                                                                                                                     
<div style="position: absolute; left: 90vw; top: 95vh;" >%s</div>                                                                                                                      
</body>                                                                                                                                                                                
</html>                                                                                                                                                                                
]]                                                                                                                                                                                     

svgPanel = [[                                                                                                                                                                          
<svg width="98vw" height="84vh">                                                                                                                                                       
  <rect x="1vw" y="4vh" width="98vw" height="84vh" style="fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0);opacity:0.5" />                                                           
  <rect x="1.1vw" y="4.1vh" width="98vw" height="5vh" style="fill:rgb(0,0,155);stroke-width:2;stroke:rgb(0,0,0);opacity:0.8" />                                                        
  <text x="20" y="45" font-family="Arial" fill="white" font-decoration="bold" font-size="16">%s</text>                                                                                 
</svg>                                                                                                                                                                                 
]]                                                                                                                                                                                     

buttonManager = {name, toggle,  x={}, y={}, width={}, height={}, label={}, toggled={}, clicked={}, cssbutton, cssHover, cssActive}                                               

function buttonManager:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.x={}
  o.y={}
  o.width={}
  o.height={}
  o.label={}
  o.toggled={}
  o.clicked={}
  
  -- o.name = name
  print("buttonmanager created name ["..o.name.."]")                                                                                                                              
  
  return o
end

function buttonManager:AddButton(id, x, y, width, height, label)                                                                                                                       
  print('bouton '..id..'ajouté a '..self.name)
  self.x[id] = x                                                                                                                                                                       
  self.y[id] = y                                                                                                                                                                       
  self.label[id] = label                                                                                                                                                               
  self.width[id] = width                                                                                                                                                               
  self.height[id] = height                                                                                                                                                             
  self.clicked[id] = 0                                                                                                                                                                 
end                                                                                                                                                                                    

function buttonManager:IsHover(id, mouseX, mouseY)                                                                                                                                     
  if mouseX >= self.x[id] and mouseX <= self.x[id]+self.width[id]                                                                                                                      
    and mouseY >= self.y[id] and mouseY <= self.y[id]+self.height[id] then                                                                                                             
    return 1                                                                                                                                                                           
  end                                                                                                                                                                                  
  return 0                                                                                                                                                                             
end                                                                                                                                                                                    

function buttonManager:ResetAll()                                                                                                                                                      
  for k in pairs(self.x) do                                                                                                                                                            
    self.clicked[k] = 0                                                                                                                                                                
  end                                                                                                                                                                                  
end                                                                                                                                                                                    

function buttonManager:OnClick(id)                                                                                                                                                     
  if self.clicked[id] == 1 then                                                                                                                                                              
      self.clicked[id] = 0 -- consume the onclick action
     return 1                                                                                                                                                                          
  end                                                                                                                                                                                  
end                                                                                                                                                                                    

function buttonManager:IsToggled(id)                                                                                                                                                   
  if self.clicked[id] == 1 then                                                                                                                                                        
     return 1                                                                                                                                                                          
  end                                                                                                                                                                                  
end                                                                                                                                                                                    

function buttonManager:Display(mouseX, mouseY, mouseState)                                                                                                                             
  local btnHtml = '' 
  for k in pairs(self.x) do
    local btncss = self.cssbutton                                                                                                                                                      
    if self.toggle == 1 then                                                                                                                                                           
      -- toggle mode                                                                                                                                                                   
      if self:IsHover(k, mouseX, mouseY) == 1  then                                                                                                                                    
         if mouseState == 1 then                                                                                                                                                       
           self:ResetAll()                                                                                                                                                             
           self.clicked[k] = 1                                                                                                                                                         
           btncss = self.cssActive                                                                                                                                                     
         else                                                                                                                                                                          
           btncss = self.cssHover                                                                                                                                                      
         end                                                                                                                                                                           
      else                                                                                                                                                                             
        if self.clicked[k] == 1 then                                                                                                                                                   
          btncss = self.cssActive                                                                                                                                                      
        else                                                                                                                                                                           
          btncss = self.cssbutton                                                                                                                                                      
        end                                                                                                                                                                            
      end                                                                                                                                                                              
  else                                                                                                                                                                                 
    -- click mode                                                                                                                                                                      
    self.clicked[k] = 0                                                                                                                                                                
    if self:IsHover(k, mouseX, mouseY) == 1 then                                                                                                                                       
      if mouseState == 1 then                                                                                                                                                          
        self.clicked[k] = 1                                                                                                                                                            
        btncss = self.cssActive                                                                                                                                                        
      else                                                                                                                                                                             
           btncss = self.cssHover                                                                                                                                                      
      end                                                                                                                                                                              
    end                                                                                                                                                                                
  end                                                                                                                                                                                  

    btnHtml = btnHtml..'<div class="'..btncss..'" style="position: absolute; left: '..self.x[k]..'px; top: '..self.y[k]..'px; width: '..self.width[k]..'px;">'..self.label[k]..'</div>'
  end                                                                                                                                                                                  

  return btnHtml                                                                                                                                                                       
end                                                                                                                                                                                    


-- tabs or toggle button (toggle=1)                                                                                                                        
bm = buttonManager:new{name = 'tabb', toggle=1, cssbutton='btn', cssHover='btnHover', cssActive='btnActif'}                                                                                   
bm:AddButton('door_tab', 10, 10, 140, 40, 'Doors')                                                                                                                                     
bm:AddButton('light_tab', 170, 10, 140, 40, 'Lights')                                                                                                                                  

-- normal buttons (toggle=0)
bmLight = buttonManager:new{name = 'light', toggle=0, cssbutton='btn', cssHover='btnHover', cssActive='btnActif'}                                                                             
bmLight:AddButton('light1', 200, 300, 140, 40, 'Light 1')                                                                                                                             
bmLight:AddButton('light2', 400, 300, 140, 40, 'Light 2')                                                                                                                             
bmLight:AddButton('light3', 600, 300, 140, 40, 'Light 3')                                                                                                                             

bmDoor = buttonManager:new{name = 'door', toggle=0, cssbutton='btn', cssHover='btnHover', cssActive='btnActif'}                                                                             
bmDoor:AddButton('door1', 200, 300, 140, 40, 'Door 1')       
bmDoor:AddButton('door2', 400, 300, 140, 40, 'Door 2')
bmDoor:AddButton('door3', 600, 300, 140, 40, 'Door 3')

-- timer                                                                                                                                                                               
self.setTimer('loop', 0.07)                                                                                                                                                            
