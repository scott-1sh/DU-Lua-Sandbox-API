--[[    
    databank.lua 
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
IDataBank = {id, name}

function IDataBank:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 28.67325
  o.class = 'DataBankUnit'
  o.integrity = 100

  o.databank = DataBank:new{id = o.id, name = o.name}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end


function createInterfaceDataBank(id, name)

  local obj = IDataBank:new{id=id,name=name}
  obj.clear = function() obj.databank:clear() end
  obj.getNbKeys = function() return obj.databank:getNbKeys() end
  obj.hasKey = function(key) return obj.databank:hasKey(key) end
  obj.getKeys = function() return obj.databank:getKeys() end
  obj.setStringValue = function(key, val) obj.databank:setStringValue(key, val) end
  obj.setIntValue = function(key, val) obj.databank:setIntValue(key, val) end
  obj.setFloatValue = function(key, val) obj.databank:setFloatValue(key, val) end
  obj.getStringValue = function(key) return obj.databank:getStringValue(key) end
  obj.getIntValue = function(key) return obj.databank:getIntValue(key) end
  obj.getFloatValue = function(key) return obj.databank:getFloatValue(key) end

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

DataBank = {id, name = "databank"}

function DataBank:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self

  self.name = name

  if _verboseLUA == 1 then print("[LUA] Databank ["..o.name.."] created with the id ["..o.id.."]") end
    
  return o
end

function DataBank:clear() 
  JavaWindow:setState(self.id, {"clear"})
end

function DataBank:getKeys()
  return JavaWindow:get(self.id, {"getKeys"})    
end

function DataBank:getNbKeys() 
  return JavaWindow:get(self.id, {"getNbKeys"})    
end

function DataBank:hasKey(key) 
  return JavaWindow:get(self.id, {"hasKey", key})    
end

function DataBank:setStringValue(key, val) 
  JavaWindow:setState(self.id, {"setStringValue", key, val})    
end

function DataBank:setIntValue(key, val) 
  JavaWindow:setState(self.id, {"setIntValue", key, val})    
end

function DataBank:setFloatValue(key, val) 
  JavaWindow:setState(self.id, {"setFloatValue", key, val})    
end

function DataBank:getStringValue(key) 
  return JavaWindow:get(self.id, {"getStringValue", key})    
end

function DataBank:getIntValue(key) 
  return JavaWindow:get(self.id, {"getIntValue", key})    
end

function DataBank:getFloatValue(key) 
  return JavaWindow:get(self.id, {"getFloatValue", key})    
end


