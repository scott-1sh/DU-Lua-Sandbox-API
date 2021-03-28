--[[    
    unit.lua 
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
Iunit = {id, name, unit}

function Iunit:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 34.06475
  o.class = 'Unit'
  o.integrity = 100
  
  o.unit = Unit:new{id = o.id, name = o.name}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end

function createInterfaceUnit(id, name)
  local obj = Iunit:new{id=id,name=name}
  
  obj.setTimer = function(ptimername, psec) obj.unit:setTimer(ptimername, psec) end;
  obj.exit = function() obj.unit:exit() end;
  obj.getMasterPlayerId = function() return obj.unit:getMasterPlayerId() end;
  obj.getMasterRelativePosition = function() return obj.navigator:getMasterRelativePosition() end;

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

Unit = {id, name = "unit", x = 0, y = 0}

function Unit:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self

  self.name = name

  if _verboseLUA == 1 then print("[LUA] Unit ["..o.name.."] created with the id ["..o.id.."]") end
    
  return o
end

function Unit:exit()
  JavaWindow:setState(self.id, {"exit"})    
end

function Unit:getMasterPlayerId() 
  return JavaWindow:get(self.id, {"getMasterPlayerId"})   
end

function Unit:setTimer(ptimername, psec) 
  if _verboseLUA == 1 then print("[LUA] Timer ["..ptimername.."] activated every "..psec.."sec") end
  JavaWindow:setState(self.id, {"setTimer", ptimername, psec})    
end
