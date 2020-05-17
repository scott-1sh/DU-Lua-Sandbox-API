--[[    
    radar_tick.lua 
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

screen1.clear()

-- local htmlText = string.format(htmlContent,mouseX,mouseY)

local cells = " "

for i, id in ipairs( radar1.getEntries() ) do
 
  local pos = "x:"..radar1.getConstructPos(id)[1].." y:"..radar1.getConstructPos(id)[2].." z:"..radar1.getConstructPos(id)[3]
  cells = cells.."<tr><td align=center>"..id.."</td><td>"..radar1.getConstructName(id).."</td><td>"..database.getPlayer(radar1.getConstructOwner(id))['name'].."</td><td>"..radar1.getConstructSize(id)[1].."</td><td>"..pos.."</td><td align=center>"..radar1.getConstructType(id).."</td></tr>"

end

local text = string.format(htmlTable, cells)

htmlText = string.format(htmlContent, text)

screen1.addContent(0,0,htmlBackground1)
screen1.addContent(0,0,htmlText)
