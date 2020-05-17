--[[    
    button.lua 
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
Ibutton = {id, name, button}

function Ibutton:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 28.67325
  o.class = 'ManualButtonUnit'
  o.integrity = 100

  o.button = Button:new{id = o.id, name = o.name}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end


function createInterfaceButton(id, name)

  local obj = Ibutton:new{id=id,name=name}

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


Button = {id, State = 0, Name = "Button", x = 0, y = 0}

function Button:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self
 
  if _verboseLUA == 1 then print("[LUA] Button ["..o.name.."] created with the id ["..o.id.."]") end
    
  return o
end