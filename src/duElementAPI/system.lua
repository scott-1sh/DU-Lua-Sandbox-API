--[[    
    system.lua
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


isystem = {id, 'System'}
function isystem:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self

  if _verboseLUA == 1 then print("[LUA] System loaded") end
    
  return o
end

function isystem:getActionKeyName(actionName)
   return 'test'
   -- return JavaSystem:get({"getActionKeyName", actionName})    
end

function isystem:showScreen(bool)
   return JavaSystem:get({"showScreen", bool})    
end

function isystem:showScreen(content)
   return JavaSystem:get({"setScreen", content})
end

function isystem:createWidgetPanel(label)
   return JavaSystem:get({"createWidgetPanel", label})    
end

function isystem:destroyWidgetPanel(panelId)
   return JavaSystem:get({"destroyWidgetPanel", panelId})
end

function isystem:createWidget(panelId, type)
   return JavaSystem:get({"createWidget", panelId, type})    
end

function isystem:destroyWidget(widgetId)
   return JavaSystem:get({"destroyWidget", widgetId})    
end

function isystem:createData(dataJson)
   return JavaSystem:get({"createData", dataJson})    
end

function isystem:destroyData(dataId)
   return JavaSystem:get({"destroyData", dataId})    
end

function isystem:updateData(dataId, dataJson)
   return JavaSystem:get({"updateData", dataId, dataJson})    
end

function isystem:addDataToWidget(dataId, dataJson)
   return JavaSystem:get({"addDataToWidget", dataId, widgetId})    
end

function isystem:removeDataFromWidget(dataId, widgetId)
   return JavaSystem:get({"removeDataFromWidget", dataId, widgetId})    
end

function isystem:getMouseWheel()
   return JavaSystem:get({"getMouseWheel"})    
end

function isystem:getMouseDeltaX()
   return JavaSystem:get({"getMouseDeltaX"})    
end

function isystem:getMouseDeltaY()
   return JavaSystem:get({"getMouseDeltaY"})    
end

function isystem:getMousePosX()
   return JavaSystem:get({"getMousePosX"})    
end

function isystem:getMousePosY()
   return JavaSystem:get({"getMousePosY"})    
end

function isystem:lockView()
   return JavaSystem:get({"lockView"})
end

function isystem:isViewLocked()
   return JavaSystem:get({"isViewLocked"})    
end

function isystem:freeze(bool)
   return JavaSystem:get({"freeze", bool})    
end

function isystem:isFrozen()
   return JavaSystem:get({"isFrozen"})    
end

function isystem:getTime()
   return JavaSystem:get({"getTime"})    
end

function isystem:getPlayerName(id)
   return JavaSystem:get({"getPlayerName", id})    
end

function isystem:getPlayerWorldPos(id)
   return JavaSystem:get({"freeze", id})    
end

System = isystem:new{0, 'System'}
System.getActionKeyName = function(actionName) return isystem:getActionKeyName(actionName) end
System.showScreen = function(bool) return isystem:showScreen(bool) end
System.setScreen = function(content) return isystem:setScreen(content) end
System.createWidgetPanel = function(label) return isystem:createWidgetPanel(label) end
System.destroyWidgetPanel = function(panelId) return isystem:destroyWidgetPanel(panelId) end
System.createWidget = function(panelId, type) return isystem:createWidget(panelId, type) end
System.destroyWidget = function(widgetId) return isystem:destroyWidget(widgetId) end
System.createData = function(dataJson) return isystem:createData(dataJson) end
System.destroyData = function(dataId) return isystem:destroyData(dataId) end
System.updateData = function(dataId, dataJson) return isystem:updateData(dataId, dataJson) end
System.addDataToWidget = function(dataId, widgetId) return isystem:addDataToWidget(dataId, widgetId) end
System.removeDataFromWidget = function(dataId, widgetId) return isystem:removeDataFromWidget(dataId, widgetId) end
System.getMouseWheel = function() return isystem:getMouseWheel() end
System.getMouseDeltaX = function() return isystem:getMouseDeltaX() end
System.getMouseDeltaY = function() return isystem:getMouseDeltaY() end
System.getMousePosX = function() return isystem:getMousePosX() end
System.getMousePosY = function() return isystem:getMousePosY() end
System.lockView = function() isystem:lockView() end
System.isViewLocked = function() return isystem:isViewLocked() end
System.freeze = function(bool) isystem:freeze(bool) end
System.isFrozen = function() return isystem:isFrozen() end
System.getTime = function() return isystem:getTime() end
System.getPlayerName = function(id) return isystem:getPlayerName(id) end
System.getPlayerWorldPos = function(id) return isystem:getPlayerWorldPos(id) end
System.print = function(msg) print(msg) end

