--[[    
    navigator.lua 
    Copyright (C) 2020 Stephane Boivin (Discord: Nmare418#6397)
    
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

Inavigator = {id, name}

function Inavigator:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.maxHitPoints = 2000
  o.hitPoints = 2000
  o.mass = 34.06475
  o.class = 'CockpitUnit'
  o.integrity = 100
  
  o.navigator = Navigator:new{id = o.id, name = 'Navigator'}
  o.generic = genericElement:new{id = o.id, maxHitPoints = o.maxHitPoints, hitPoints = o.hitPoints, mass = o.mass, class = o.class, integrity = o.integrity }

  return o
end

function createInterfaceNavigator(id)
  local obj = Inavigator:new{id=id}

  obj.new = function(system, core, unit) return obj end -- notice: system, core and unit are already connected on the java engine side
  obj.getAtmosphereDensity = function() return obj.navigator:getAtmosphereDensity() end
  obj.getClosestPlanetInfluence = function() return obj.navigator:getClosestPlanetInfluence() end
  
  obj.setEngineCommand = function(taglist, acceleration, angularAcceleration, keepForceCollinearity, keepTorqueCollinearity, 
                                  priority1SubTags, priority2SubTags, priority3SubTags, toleranceRatioToStopCommand)
  obj.navigator:setEngineCommand(taglist, acceleration, angularAcceleration, keepForceCollinearity, keepTorqueCollinearity, 
                                   priority1SubTags, priority2SubTags, priority3SubTags, toleranceRatioToStopCommand)
  end         
  obj.setEngineThrust = function(taglist, thrust) obj.navigator:setEngineThrust(taglist, thrust) end
  obj.setAxisCommandValue = function(axis, commandValue) obj.navigator:setAxisCommandValue(axis, commandValue) end
  obj.getAxisCommandValue = function(axis) return obj.navigator:getAxisCommandValue(axis) end
  obj.setupAxisCommandProperties = function(axis, commandType) obj.navigator:setupAxisCommandProperties(axis, commandType) end
  obj.getControlMasterModeId = function() return obj.nagivator:getControlMasterModeId() end
  obj.cancelCurrentControlMasterMode = function() obj.navigator:cancelCurrentControlMasterMode() end
  obj.setupControlMasterModeProperties = function(controlMasterModeId, displayName) obj.navigator:setupControlMasterModeProperties(controlMasterModeId, displayName) end 
  obj.isAnyLandingGearExtended = function() return obj.navigator:isAnyLandingGearExtended() end
  obj.extendLandingGears = function() obj.navigator:extendLandingGears() end
  obj.isRemoteControlled = function() return obj.navigator:isRemoteControlled() end
  obj.isMouseControlActivated = function() return obj.navigator:isMouseControlActivated() end
  obj.switchOnHeadlights = function() obj.navigator:switchOnHeadlights() end
  obj.switchOffHeadlights = function() obj.navigator:switchOffHeadlights() end
  obj._setRemoteControlled = function(remoteControlled) obj.navigator:_setRemoteControlled(remoteControlled) end -- 0 false/1 true
  -- base elements
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

Navigator = {id, name = "Navigator"}

function Navigator:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self

  self.name = "Navigator"

  if _verboseLUA == 1 then print("[LUA] Navigator ["..o.name.."] created with the id ["..o.id.."]") end
    
  return o
end

function Navigator:_setRemoteControlled() 
  JavaWindow:setState(self.id, {"_setRemoteControlled"})   
end

function Navigator:isRemoteControlled() 
  return JavaWindow:get(self.id, {"isRemoteControlled"})   
end

function Navigator:isMouseControlActivated() 
  return JavaWindow:get(self.id, {"isMouseControlActivated"})   
end

function Navigator:switchOnHeadlights() 
  JavaWindow:setState(self.id, {"switchOnHeadlights"})   
end
function Navigator:switchOffHeadlights() 
  JavaWindow:setState(self.id, {"switchOffHeadlights"})   
end

function Navigator:retractLandingGears() 
  JavaWindow:setState(self.id, {"retractLandingGears"})   
end

function Navigator:extendLandingGears() 
  JavaWindow:setState(self.id, {"extendLandingGears"})   
end

function Navigator:isAnyLandingGearExtended() 
  return JavaWindow:get(self.id, {"isAnyLandingGearExtended"})   
end

function Navigator:setupControlMasterModeProperties(controlMasterModeId, displayName)
  JavaWindow:setState(self.id, {"setupControlMasterModeProperties", controlMasterModeId, displayName})    
end

function Navigator:getControlMasterModeId() 
  return JavaWindow:get(self.id, {"getControlMasterModeId"})   
end

function Navigator:setupAxisCommandProperties(axis, commandType)
  JavaWindow:setState(self.id, {"setupAxisCommandProperties", axis, commandType})    
end

function Navigator:setAxisCommandValue(axis, commandValue)
  JavaWindow:setState(self.id, {"setAxisCommandValue", axis, commandValue})    
end

function Navigator:getAxisCommandValue(axis) 
  return JavaWindow:get(self.id, {"getAxisCommandValue", axis})   
end

function Navigator:setEngineThrust(taglist, thrust)
  JavaWindow:setState(self.id, {"setEngineThrust", taglist, thrust})    
end

function Navigator:setEngineCommand(taglist, acceleration, angularAcceleration, keepForceCollinearity, keepTorqueCollinearity, 
                                    priority1SubTags, priority2SubTags, priority3SubTags, toleranceRatioToStopCommand)
  JavaWindow:setState(self.id, {"setEngineCommand",taglist, acceleration, angularAcceleration, keepForceCollinearity, keepTorqueCollinearity, 
                                    priority1SubTags, priority2SubTags, priority3SubTags, toleranceRatioToStopCommand})    
end                                    

function Navigator:getAtmosphereDensity() 
  return JavaWindow:get(self.id, {"getAtmosphereDensity"})   
end

function Navigator:getClosestPlanetInfluence() 
  return JavaWindow:get(self.id, {"getClosestPlanetInfluence"})   
end
