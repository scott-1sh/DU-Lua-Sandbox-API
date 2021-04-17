--[[    
    radar.lua
     Copyright (C) 2020 Stephane Boivin (Discord: Nmare418#6397)
    
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
IRadar = {id, name}

function IRadar:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 28.67325
  o.class = 'RadarUnit'
  o.integrity = 100
  o.range = 10000

  o.radar = Radar:new{id = o.id, name = o.name, range = o.range}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end


function createInterfaceRadar(id, name)

  local obj = IRadar:new{id=id,name=name}
  obj.getRange = function() return obj.radar:getRange() end
  obj._setRange = function() return obj.radar:setRange() end
  obj.getEntries = function() return obj.radar:getEntries() end
  obj.getConstructSize = function(id) return obj.radar:getConstructSize(id) end
  obj.getConstructType = function(id) return obj.radar:getConstructType(id) end
  obj.getConstructWorldPos = function(id) return obj.radar:getConstructWorldPos(id) end
  obj.getConstructWorldVelocity = function(id) return obj.radar:getConstructWorldVelocity(id) end
  obj.getConstructWorldAcceleration = function(id) return obj.radar:getConstructWorldAcceleration(id) end
  obj.getConstructPos = function(id) return obj.radar:getConstructPos(id) end
  obj.getConstructVelocity = function(id) return obj.radar:getConstructVelocity(id) end
  obj.getConstructAcceleration = function(id) return obj.radar:getConstructAcceleration(id) end
  obj.getConstructName = function(id) return obj.radar:getConstructName(id) end
  obj.hasMatchingTransponder = function(id) return obj.radar:hasMatchingTransponder(id) end
  
  obj.hide = function() obj.generic:hide() end
  obj.show = function() obj.generic:show() end
  obj.getData = function() obj.generic:getData() end
  obj.getId = function() return obj.generic:getId() end
  obj.getMass = function() return obj.generic:getMass() end
  obj.getElementClass = function() return obj.generic:getElementClass() end
  obj.getIntegrity = function() return obj.generic:getIntegrity() end
  obj.getHitPoints = function() return obj.generic:getHitPoints() end
  obj.getMaxHitPoints = function() return obj.generic:getMaxHitPoints() end
  obj._setMass = function(m) obj.generic:setMass(m) end
  obj._setMaxHitPoints = function(mhp) obj.generic:setMaxHitPoints(mhp) end
  obj._setHitPoints = function(hp) obj.generic:setHitPoints(hp) end
  obj._setIntegrity = function(int) obj.generic:setIntegrity(int) end

  return obj
end

Radar = {id, name = "radar"}

function Radar:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self

  self.name = name

  if _verboseLUA == 1 then print("[LUA] Radar ["..o.name.."] created with the id ["..o.id.."]") end

  return o
end

function Radar:getRange() 
  return self.range
end

function Radar:setRange(r) 
  self.range = r
end

function Radar:getEntries() 
  return JavaWindow:get(self.id, {"getEntries"})    
end

function Radar:getConstructSize(id) 
  return JavaWindow:get(self.id, {"getConstructSize", id})    
end

function Radar:getConstructType(id) 
  return JavaWindow:get(self.id, {"getConstructType", id})    
end

function Radar:getConstructWorldPos(id) 
  return JavaWindow:get(self.id, {"getConstructWorldPos", id})    
end

function Radar:getConstructWorldVelocity(id) 
  return JavaWindow:get(self.id, {"getConstructWorldVelocity", id})    
end

function Radar:getConstructWorldAcceleration(id) 
  return JavaWindow:get(self.id, {"getConstructWorldAcceleration", id})    
end

function Radar:getConstructPos(id) 
  return JavaWindow:get(self.id, {"getConstructPos", id})    
end

function Radar:getConstructVelocity(id) 
  return JavaWindow:get(self.id, {"getConstructVelocity", id})    
end

function Radar:getConstructAcceleration(id) 
  return JavaWindow:get(self.id, {"getConstructAcceleration", id})    
end

function Radar:getConstructName(id) 
  return JavaWindow:get(self.id, {"getConstructName", id})    
end

function Radar:hasMatchingTransponder(id)
  return JavaWindow:get(self.id, {"hasMatchingTransponder", id})
end
