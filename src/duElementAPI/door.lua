-- Deprecated by toggleUnit.lua

Idoor = {id, name, door}

function Idoor:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 28.67325
  o.class = 'ManualSwitchUnit'
  o.integrity = 100

  o.door = Door:new{id = o.id, name = o.name}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end

function createInterfaceDoor(id, name)

  local obj = Idoor:new{id=id,name=name}
  obj.activate = function() obj.door:activate() end
  obj.toggle = function() obj.door:toggle() end
  obj.getState = function() return obj.door:getState() end

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
Door = {id, State = 0, name = "ManualSwitchUnit"}

function Door:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  if _verboseLUA == 1 then print("[LUA] ManualSwitchUnit ["..o.name.."] created with the id ["..o.id.."]") end

  return o
end

function Door:activate()
  self.State = 1;
  if _verboseLUA == 1 then print("[LUA] ManualSwitchUnit ["..self.name.."] opened") end
  JavaWindow:setState(self.id, {self.State,self.name})    
end

function Door:deactivate()
  self.State = 0;
  if _verboseLUA == 1 then print("[LUA] ManualSwitchUnit ["..self.name.."] closed") end
  JavaWindow:setState(self.id, {self.State,self.name})    
end

function Door:toggle()
  if self.State == 0 then
    self:activate()
  else
    self:deactivate()
  end  
end

function Door:getState()
  return self.State
end
