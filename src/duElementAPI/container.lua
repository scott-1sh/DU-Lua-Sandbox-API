--[[    
    container.lua 
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
Icontainer = {id, name, container, generic}

function Icontainer:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 14000
  o.hitPoints = 14000
  o.mass = 9855.01
  o.class = 'ContainerUnit'
  o.integrity = 100

  o.container = Container:new{id = o.id, name = o.name, mass = o.mass}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end

function createInterfaceContainer(id, name)

  local obj = Icontainer:new{id=id,name=name}
  obj.getItemsMass = function() return obj.container:getItemsMass() end
  obj.getSelfMass = function() return obj.container:getSelfMass() end
  obj._setItemsMass = function(mass) obj.container:setItemsMass(mass) end

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
Container = {id, name = "Container"}

function Container:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.itemMass = 0
  
  if _verboseLUA == 1 then print("[LUA] Container ["..o.name.."] created with the id ["..o.id.."]") end

  return o
end

function Container:getItemsMass()
  return self.itemMass    
end

function Container:setItemsMass(mass)
  self.itemMass = mass
  JavaWindow:setState(self.id, {mass})
end

function Container:getSelfMass()
  return self.mass    
end
