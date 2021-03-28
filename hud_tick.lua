--[[    
    Hud_tick.lua
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
]]
local svgBalls = ''

-- Moving balls
for i,v in ipairs(x) do 
  -- bounce?
	if x[i]+dirx[i]+ballRayon > screenX or x[i]+dirx[i]-ballRayon < 1 then
    	dirx[i] = -dirx[i]
	end  
    if y[i]+diry[i]+ballRayon > screenY or y[i]+diry[i]-ballRayon < 1 then 
  		diry[i] = -diry[i] 
  	end 
	x[i] = x[i] + (dirx[i]*speed)
	y[i] = y[i] + (diry[i]*speed)
	svgBalls = svgBalls..svgBall(x[i], y[i], ballRayon)
end
    
screen1.clear()

screen1.addContent(0,0, string.format(htmlBackground, svgBalls))


