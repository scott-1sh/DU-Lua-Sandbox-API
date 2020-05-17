--[[    
    gyro.lua 
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
Igyro = {id, name}

function Igyro:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 28.67325
  o.class = 'GyroUnit'
  o.integrity = 100

  o.gyro = Gyro:new{id = o.id, name = o.name, type = o.type}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end

function createInterfaceGyro(id, name)

  local obj = Igyro:new{id=id,name=name}
  obj.activate = function() obj.gyro:activate() end
  obj.toggle = function() obj.gyro:toggle() end
  obj.getState = function() return obj.gyro:getState() end

  obj.localUp = function() return obj.gyro:localUp() end
  obj.localForward = function() return obj.gyro:localForward() end
  obj.localRight = function() return obj.gyro:localRight() end
  obj.worldUp = function() return obj.gyro:worldUp() end
  obj.worldForward = function() return obj.gyro:worldForward() end
  obj.worldRight = function() return obj.gyro:worldRight() end
  obj.getPitch = function() return obj.gyro:getPitch() end
  obj.getRoll = function() return obj.gyro:getRoll() end
  obj.getYaw = function() return obj.gyro:getYaw() end
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
Gyro = {id, State = 0, name = "GyroUnit"}

function Gyro:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self
  o.State = 1;
  if _verboseLUA == 1 then print("[LUA] Gyro ["..o.name.."] created with the id ["..o.id.."]") end  
  return o
end

function Gyro:activate()
  self.State = 1;
  if _verboseLUA == 1 then print("[LUA] Gyro ["..self.name.."] opened") end
  JavaWindow:setState(self.id, {self.State,self.name})    
end

function Gyro:deactivate()
  self.State = 0;
  if _verboseLUA == 1 then print("[LUA] Gyro ["..self.name.."] closed") end
  JavaWindow:setState(self.id, {self.State,self.name})    
end

function Gyro:toggle()

  if _verboseLUA == 1 then print("[LUA] Gyro ["..self.name.."] toggle()") end
  if self.State == 0 then
    self:activate()
  else
    self:deactivate()
  end  
end

function Gyro:getState()
  return self.State
end

function Gyro:localUp() 
  return JavaWindow:get(self.id, {"localUp"})    
end

function Gyro:localForward() 
  return JavaWindow:get(self.id, {"localForward"})    
end

function Gyro:localRight() 
  return JavaWindow:get(self.id, {"localRight"})    
end

function Gyro:worldUp() 
  return JavaWindow:get(self.id, {"worldUp"})    
end

function Gyro:worldForward() 
  return JavaWindow:get(self.id, {"worldForward"})    
end

function Gyro:worldRight() 
  return JavaWindow:get(self.id, {"worldRight"})    
end

function Gyro:getPitch() 
  return JavaWindow:get(self.id, {"getPitch"})    
end

function Gyro:getRoll() 
  return JavaWindow:get(self.id, {"getRoll"})    
end

function Gyro:getYaw() 
  return JavaWindow:get(self.id, {"getYaw"})    
end
