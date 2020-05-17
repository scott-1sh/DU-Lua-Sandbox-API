--[[    
    screen.lua 
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
Iscreen = {id, name, screen, generic}

function Iscreen:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 134.064875
  o.class = 'ScreenUnit'
  o.integrity = 100
    
  o.screen = Screen:new{id = o.id, name = o.name}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }
 
  return o
end


function createInterfaceScreen(id, name)

  local obj = Iscreen:new{id=id,name=name}
  obj.activate = function() obj.screen:activate() end
  obj.addContent = function(px, py, html) obj.screen:addContent(px, py, html) end
  obj.clear = function() obj.screen:clear() end
  obj.activate = function() obj.screen:activate() end
  obj.deactivate = function() obj.screen:deactivate() end
  obj.getMouseX = function() return obj.screen:getMouseX() end  
  obj.getMouseY = function() return obj.screen:getMouseY() end  
  obj.getMouseState = function() return obj.screen:getMouseState() end
  
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

Screen = {id, name = "Screen" }

function Screen:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self
    
  if _verboseLUA == 1 then print("[LUA] Screen ["..o.name.."] created with the id ["..o.id.."]") end
    
  return o
end

function Screen:setSVG(svg)

end

function Screen:activate()
  if _verboseLUA == 1 then print("[LUA] Screen ["..self.name.."] is now active") end
  self:clear()
end

function Screen:deactivate()
  if _verboseLUA == 1 then print("[LUA] Screen ["..self.name.."] is now inactive") end
  self:clear()
end

function Screen:addContent(px, py, phtml) 
  self.html = phtml;
  -- print("[LUA] Screen ["..self.name.."] content changed")
  JavaWindow:setState(self.id, {"addContent", self.html, self.name})    
end

function Screen:getMouseX()
  return JavaWindow:getMouseX(self.id)
end

function Screen:getMouseY()
  return JavaWindow:getMouseY(self.id)
end

function Screen:getMouseState()
  return JavaWindow:getMouseState(self.id)
end


function Screen:clear()
  self.html = "";
  -- print("[LUA] Screen ["..self.name.."] cleared")
  JavaWindow:setState(self.id, {"clear", self.html, self.name})    
end
