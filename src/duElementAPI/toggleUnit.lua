--[[    
    toggleUnit.lua 
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
Itoggleunit = {id, name, type}

function Itoggleunit:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 28.67325
  o.class = 'ManualSwitchUnit'
  o.integrity = 100

  o.toggleunit = ToggleUnit:new{id = o.id, name = o.name, type = o.type}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end

function createInterfaceToggleUnit(id, name, type)

  local obj = Itoggleunit:new{id=id,name=name,type=type}
  obj.activate = function() obj.toggleunit:activate() end
  obj.deactivate = function() obj.toggleunit:deactivate() end
  obj.toggle = function() obj.toggleunit:toggle() end
  obj.getState = function() return obj.toggleunit:getState() end

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

-- State 0 = closed
-- State 1 = open
ToggleUnit = {id, State = 0, name = "ManualSwitchUnit"}

function ToggleUnit:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self
  o.State = 0;
  if _verboseLUA == 1 then print("[LUA] "..o.type.." ["..o.name.."] created with the id ["..o.id.."]") end  
  return o
end

function ToggleUnit:activate()
  self.State = 1;
  if _verboseLUA == 1 then print("[LUA] "..self.type.." ["..self.name.."] opened") end
  JavaWindow:setState(self.id, {self.State,self.name})    
end

function ToggleUnit:deactivate()
  self.State = 0;
  if _verboseLUA == 1 then print("[LUA] "..self.type.." ["..self.name.."] closed") end
  JavaWindow:setState(self.id, {self.State,self.name})    
end

function ToggleUnit:toggle()
  if self.State == 0 then
    self:activate()
  else
    self:deactivate()
  end  
end

function ToggleUnit:getState()
  return self.State
end
