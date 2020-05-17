--[[    
    core.lua 
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
Icore = {id, name}

function Icore:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 28.67325
  o.class = 'CoreUnit'
  o.integrity = 100

  o.core = Core:new{id = o.id, name = o.name}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end

function createInterfaceCore(id, name)

  local obj = Icore:new{id=id,name=name}
  obj.getConstructMass = function() return obj.core:getConstructMass() end
  obj.getConstructIMass = function() return obj.core:getConstructIMass() end
  obj.getConstructCrossSection = function() return obj.core:getConstructCrossSection() end 
  obj.getMaxKinematicsParameters = function() return obj.core:getMaxKinematicsParameters() end
  obj.getConstructWorldPos = function() return obj.core:getConstructWorldPos() end
  obj.getConstructId = function() return obj.core:getConstructId() end
  obj.getWorldAirFrictionAngularAcceleration = function() return obj.core:getWorldAirFrictionAngularAcceleration() end
  obj.getWorldAirFrictionAcceleration = function() return obj.core:getWorldAirFrictionAcceleration() end
  obj.spawnNumberSticker = function() return obj.core:spawnNumberSticker() end
  obj.spawnArrowSticker = function() return obj.core:spawnArrowSticker() end
  obj.deleteSticker = function() return obj.core:deleteSticker() end
  obj.moveSticker = function() return obj.core:moveSticker() end
  obj.rotateSticker = function() return obj.core:rotateSticker() end
  obj.deleteSticker = function() return obj.core:deleteSticker() end
  obj.getElementList = function() return obj.core:getElementList() end
  obj.getElementName = function(eid) return obj.core:getElementName(eid) end
  obj.getElementTypeById = function(eid) return obj.core:getElementType(eid) end  
  obj.getElementHitPointsById = function(eid) return obj.core:getElementHitPoints(eid) end
  obj.getElementMaxHitPointsById = function(eid) return obj.core:getElementMaxHitPoints(eid) end
  obj.getElementMassById = function(eid) return obj.core:getElementMass(eid) end
  obj.getAltitude = function() return obj.core:getAltitude() end
  obj.g = function() return obj.core:g()  end
  obj.getWorldGravity = function() return obj.core:getWorldGravity() end
  obj.getWorldVertical = function() return obj.core:getWorldVertical() end
  obj.getAngularVelocity = function() return obj.core:getAngularVelocity()  end
  obj.getWorldAngularVelocity = function() return obj.core:getWorldAngularVelocity() end
  obj.getConstructMass = function() return obj.core:getConstructMass() end
  obj.getWorldAngularAcceleration = function() return obj.core:getWorldAngularAcceleration()  end
  obj.getVelocity = function() return obj.core:getVelocity() end
  obj.getWorldVelocity = function() return obj.core:getWorldVelocity() end
  obj.getWorldAcceleration = function() return obj.core:getWorldAcceleration() end
  obj.getConstructOrientationUp = function() return obj.core:getConstructOrientationUp() end
  obj.getConstructOrientationRight = function() return obj.core:getConstructOrientationRight() end
  obj.getConstructOrientationForward = function() return obj.core:getConstructOrientationForward() end
  obj.getConstructWorldOrientationUp = function() return obj.core:getConstructWorldOrientationUp() end
  obj.getConstructWorldOrientationRight = function() return obj.core:getConstructWorldOrientationRight() end
  obj.getConstructWorldOrientationForward = function() return obj.core:getConstructWorldOrientationForward() end

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
Core = {id, State = 0, name = "core"}

function Core:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self
  o.State = 1;
  if _verboseLUA == 1 then print("[LUA] Core ["..o.name.."] created with the id ["..o.id.."]") end  
  return o
end

function Core:getConstructMass() 
  return JavaWindow:get(self.id, {"ConstructMass"})    
end

function Core:getConstructIMass() 
  return JavaWindow:get(self.id, {"ConstructIMass"})    
end

function Core:getConstructCrossSection() 
  return JavaWindow:get(self.id, {"ConstructCrossSection"})    
end

function Core:getMaxKinematicsParameters() 
  return JavaWindow:get(self.id, {"MaxKinematicsParameters"})    
end

function Core:getConstructWorldPos() 
  return JavaWindow:get(self.id, {"ConstructWorldPos"})    
end

function Core:getConstructId() 
  return JavaWindow:get(self.id, {"ConstructId"})    
end

function Core:getWorldAirFrictionAngularAcceleration() 
  return JavaWindow:get(self.id, {"WorldAirFrictionAngularAcceleration"})    
end

function Core:getWorldAirFrictionAcceleration() 
  return JavaWindow:get(self.id, {"WorldAirFrictionAcceleration"})    
end

function Core:spawnNumberSticker() 
  return JavaWindow:get(self.id, {"spawnNumberSticker"})    
end

function Core:spawnArrowSticker() 
  return JavaWindow:get(self.id, {"spawnArrowSticker"})    
end

function Core:deleteSticker() 
  return JavaWindow:get(self.id, {"deleteSticker"})    
end

function Core:moveSticker() 
  return JavaWindow:get(self.id, {"moveSticker"})    
end

function Core:rotateSticker() 
  return JavaWindow:get(self.id, {"rotateSticker"})    
end

function Core:deleteSticker() 
  return JavaWindow:get(self.id, {"deleteSticker"})    
end

function Core:getElementList() 
  return JavaWindow:get(self.id, {"ElementList"})    
end

function Core:getElementName(elementId) 
  return JavaWindow:get(self.id, {"ElementName", elementId})    
end

function Core:getElementType(elementId) 
  return JavaWindow:get(self.id, {"ElementType", elementId})    
end

function Core:getElementHitPoints(elementId) 
  return JavaWindow:get(self.id, {"ElementHitPoints", elementId})
end

function Core:getElementMaxHitPoints(elementId) 
  return JavaWindow:get(self.id, {"ElementMaxHitPoints", elementId})    
end

function Core:getElementMass(elementId) 
  return JavaWindow:get(self.id, {"ElementMass", elementId})
end

function Core:getAltitude() 
  return JavaWindow:get(self.id, {"Altitude"})    
end

function Core:g() 
  return JavaWindow:get(self.id, {"g"})    
end

function Core:getWorldGravity() 
  return JavaWindow:get(self.id, {"WorldGravity"})    
end

function Core:getWorldVertical() 
  return JavaWindow:get(self.id, {"WorldVertical"})    
end

function Core:getAngularVelocity() 
  return JavaWindow:get(self.id, {"AngularVelocity"})    
end

function Core:getWorldAngularVelocity() 
  return JavaWindow:get(self.id, {"WorldAngularVelocity"})    
end

function Core:AngularAcceleration() 
  return JavaWindow:get(self.id, {"AngularAcceleration"})    
end

function Core:getWorldAngularAcceleration() 
  return JavaWindow:get(self.id, {"WorldAngularAcceleration"})    
end

function Core:getVelocity() 
  return JavaWindow:get(self.id, {"Velocity"})    
end

function Core:getWorldVelocity() 
  return JavaWindow:get(self.id, {"WorldVelocity"})    
end

function Core:getWorldAcceleration() 
  return JavaWindow:get(self.id, {"WorldAcceleration"})    
end

function Core:getConstructOrientationUp() 
  return JavaWindow:get(self.id, {"ConstructOrientationUp"})    
end

function Core:getConstructOrientationRight() 
  return JavaWindow:get(self.id, {"ConstructOrientationRight"})    
end

function Core:getConstructOrientationForward() 
  return JavaWindow:get(self.id, {"ConstructOrientationForward"})    
end

function Core:getConstructWorldOrientationUp() 
  return JavaWindow:get(self.id, {"ConstructWorldOrientationUp"})    
end

function Core:getConstructWorldOrientationRight() 
  return JavaWindow:get(self.id, {"ConstructWorldOrientationRight"})    
end

function Core:getConstructWorldOrientationForward() 
  return JavaWindow:get(self.id, {"ConstructWorldOrientationForward"})    
end

