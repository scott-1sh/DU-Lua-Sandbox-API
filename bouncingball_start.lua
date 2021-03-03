--[[    
    bouncingball_start.lua 
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
]]

screen1.clear() 

-- timer
self.setTimer('loop', 0.06)

-- html
htmlBackground = [[ 
<svg width="1020" height="605">
  <rect width="1020" height="605" style="fill:rgb(0,0,255);stroke-width:10;stroke:rgb(255,255,0)" />        
  <text x="5" y="35" fill="white" font-family="Arial" font-size="15">%s</text>
  %s
</svg> 
]] 

function htmlBall(x,y,ballRayon)
  local str = [[
<svg width="1025" height="606">
<defs> 
   <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="0%"> 
   <stop offset="0%" style="stop-color:rgb(200,200,255);stop-opacity:1" />  
   <stop offset="100%" style="stop-color:rgb(0,15,15);stop-opacity:1" />  
   </linearGradient>  
</defs>  
<circle cx="]]..x..[[" cy="]]..y..[[" r="]]..ballRayon..[[" stroke="black" stroke-width="2" fill="url(#grad1)" />  
</svg>]]

 return str  
end

ballRayon = 25  
x = 17  
y = 17  
dirx = 15  
diry = 15  

-- background = screen1.addContent(0,0,string.format(htmlBackground,"" ,htmlBall(x,y,ballRayon)))
