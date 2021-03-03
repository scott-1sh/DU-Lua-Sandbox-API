--[[    
    debug_start.lua 
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

-- Every elements have debuging functions used for testing purpose.
-- Those functions are not available ingame.  
screen1._setMass(500)
screen1._setMaxHitPoints(3500)
unit._setMass(500)
screen1._setHitPoints(3500)
screen1._setIntegrity(85)

local pos = '::pos{0,2,-4.5824,127.3228,27.1807}'
local num = ' *([+-]?%d+%.?%d*e?[+-]?%d*)'
local posPattern = '::pos{' .. num .. ',' .. num .. ',' ..  num .. ',' .. num ..  ',' .. num .. '}'    
local systemId, bodyId, latitude, longitude, altitude = string.match(pos, posPattern);
screen1.addContent(0,0, 'getTime: '..system.getTime()..'<br>'..string.format('string: %s<br>systemId: %f   <br>bodyId: %f  <br>latitude: %f  <br>longitude: %f <br>altitude: %f', pos,systemId, bodyId, latitude, longitude, altitude))
