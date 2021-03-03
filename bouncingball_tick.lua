--[[    
    bouncingball_tick.lua 
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

-- Bounce?
if x+dirx+ballRayon > 1025 or x+dirx-ballRayon < 1 then
  dirx = -dirx
end

if y+diry+ballRayon > 606 or y+diry-ballRayon < 1 then 
  diry = -diry 
end

-- Moving the ball
x = x + dirx
y = y + diry

-- Mouse status 
local htmlMouseInfo = screen1.getMouseX().."/"..screen1.getMouseY()..":"..screen1.getMouseState()

-- Display
background = screen1.addContent(0,0, string.format(htmlBackground, htmlMouseInfo, htmlBall(x, y, ballRayon)))
