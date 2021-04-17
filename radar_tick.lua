--[[    
    radar_tick.lua
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
screen1.clear()

-- local htmlText = string.format(htmlContent,mouseX,mouseY)

local cells = " "

for i, id in ipairs( radar1.getEntries() ) do
 
  cells = cells.."<tr><td align=center>"..id.."</td>"
  cells = cells.."<td>"..radar1.getConstructName(id).."</td>"
  cells = cells.."<td align=center>"..radar1.getConstructSize(id)[1].."</td>"
  cells = cells.."<td align=center>"..radar1.getConstructType(id).."</td>"
  local trs = 'No';
  if radar1.hasMatchingTransponder(id) then  
    trs = 'Yes' 
  end 
  cells = cells.."<td align=center>"..trs.."</td></tr>"

end

local text = string.format(htmlTable, cells)

screen1.addContent(0, 0, htmlBackground1)
screen1.addContent(0, 0, string.format(htmlContent, text))
