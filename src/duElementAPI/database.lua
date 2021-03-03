--[[    
    database.lua
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
iDatabase = {id, 'database'}
function iDatabase:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self

  if _verboseLUA == 1 then print("[LUA] database loaded") end
    
  return o
end

function iDatabase:getPlayer(id)
   jsonArray = {}
   jsonArray = json.decode(DUDatabase:get({"database.getPlayer", id}))
   jsonArray['worldPos'] = {}
   jsonArray['worldPos'][1] = jsonArray['worldPos0']  
   jsonArray['worldPos'][2] = jsonArray['worldPos1']
   jsonArray['worldPos'][3] = jsonArray['worldPos2']
   return jsonArray
end

function iDatabase:getConstruct(radar, id)
    local construct = {}
    construct.owner = radar.getConstructOwner(id)
    construct.type = radar.getConstructType(id)
    construct.size = radar.getConstructSize(id)
    construct.worldPos = radar.getConstructWorldPos(id)
    construct.worldSpeed = radar.getConstructAcceleration(id)
    construct.worldAcceleration = radar.getConstructWorldAcceleration(id)
    construct.pos = radar.getConstructPos(id)
    construct.speed = radar.getConstructAcceleration(id)
    construct.acceleration = radar.getConstructAcceleration(id)
    construct.name = radar.getConstructName(id)
    return construct
end

function iDatabase:getElement(core, id)
    local element = {}
    element.id = id
    element.name = core.getElementName(id)
    element.hitPoints = core.getElementHitPoints(id)
    element.maxHitPoints = core.getElementMaxHitPoints(id)
    element.mass = core.getElementMass(id)
    element.type = core.getElementType(id)
    return element
end

database = iDatabase:new{0, 'database'}
database.getPlayer = function(id) return iDatabase:getPlayer(id) end
database.getConstruct = function(radar, id) return iDatabase:getConstruct(radar, id) end
database.getElement = function(core, id) return iDatabase:getElement(core, id) end
