--[[
    mapview_tick.lua by Stephane Boivin (aka Nmare418), 2019
    
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

local svgPoint = ''
local distance = 0
local cells = '';

for i, id in ipairs( radar1.getEntries() ) do
  owner = database.getPlayer(radar1.getConstructOwner(id))['name']
  pos = radar1.getConstructWorldPos(id)
  ctype = radar1.getConstructType(id)
  name = radar1.getConstructName(id)  
  size = radar1.getConstructSize(id)[1]
  speed = radar1.getConstructWorldVelocity(id)[1]+radar1.getConstructWorldVelocity(id)[2]+radar1.getConstructWorldVelocity(id)[3]
               
  local color = "#FFFFFF" 

  local centerX = 305
  local centerY = 305
  
  -- calculate
  local xx = ((pos[1] * 305) / range)
  local yy = ((pos[2] * 305) / range)
  local x = centerX+xx-2
  local y = centerY-yy-2
  
  -- player is offline
  if owner == 'unreachable' then color = '#808080' end  

  local distance = math.sqrt((math.pow(0 - pos[1],2) + math.pow(0 - pos[2],2) + math.pow(0 - pos[3],2)));  

  -- display dot
  if ctype == "static" then
     svgPoint = svgPoint..' <rect x="'..(x-2)..'" y="'..(y-2)..'" rx="1" rx="1" width="9" height="9" stroke="black" stroke-width="1" fill="'..color..'" />'
  else  
     svgPoint = svgPoint..' <circle cx="'..(x-2)..'" cy="'..(y-2)..'" r="4" stroke="black" stroke-width="1" fill="'..color..'"/>';
  end
  -- Libel
  svgPoint = svgPoint..string.format('<text x="'..(x+5)..'" y="'..y..'" fill="black" font-family="Arial" font-size="11">%s - %s</text>',id,name);
  
  -- entries table
  if ctype == "static" then
    activity = "static"
  else
    if speed > 0 then
      activity = "Flight"
    else
      activity = "Station"
    end
  end
  cells = cells..'<tr><td align="center">'..id..'</td><td align="left">'..name..'</td><td align="left">'..owner..'</td><td>'..size..'</td><td align="left">'..activity..'</td></tr>'

end

display = string.format(svgRadar, svgPoint)  
display = display..htmlTableHeader..cells..htmlTableFooter

screen1.addContent(0,0,display)

