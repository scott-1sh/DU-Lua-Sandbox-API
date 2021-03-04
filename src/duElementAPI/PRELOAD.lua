--[[    
    PRELOAD.lua 
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

_verboseLua = 0

function setHUD(updateScript, x, y, sizeX, sizeY)
  JavaLoader:set('setHUD', {updateScript, x or 0, y or 0, sizeX or 0, sizeY or 0})
end

function showOnScreen(screen) 
  JavaLoader:set("showOnScreen", {screen})    
end

function verboseJava(mode)
  JavaLoader:set("verboseJava", {mode}) 
end

function verboseLua(mode)
  _verboseLua = mode  
  JavaLoader:set("verboseLua", {mode}) 
end

function pause(time)
  -- pause in ms
  JavaLoader:set("pause", {time}) 
end

function loadScript(file)
  return JavaLoader:get("loadScript", {file}) 
end

function setupDatabase(player, construct, MasterPlayerId)
  local jsPlayer = json.encode(player)
  local jsConstruct = json.encode(construct)
  JavaLoader:set('database', {jsPlayer, jsConstruct, MasterPlayerId}) 
end

function abort(msg)
  JavaLoader:set("abort", {msg}) 
end

function die(msg)
  if _verboseLua == 1 then print('killed: '..msg) end
  JavaLoader:set('die', {msg}) 
end

-- database functions should be initialized
function Core(size, constructType, g, selfConstructId)
  return JavaLoader:add('CoreUnit', 'core', {size, constructType, g, selfConstructId})
end

function Unit(scriptStart, scriptStop)
  return JavaLoader:add('Unit', '', {scriptStart, scriptStop})
end

function Navigator()
  return JavaLoader:add('Navigator', 'Navigator', {})
end

function setupTimer(elementId, timerName, script)
  return JavaLoader:set('setupTimer', {elementId, timerName, script})
end

function ButtonUnit(name, label, scriptOnClick)
  return JavaLoader:add('ButtonUnit', name, {label, scriptOnClick})
end

function ContainerUnit(name)
  return JavaLoader:add('ContainerUnit', name, {})  
end

function DataBankUnit(name)
  return JavaLoader:add('DataBankUnit', name, {})  
end

function ScreenUnit(name, psizeX, psizeY)
  sizeX = psizeX or 1024
  sizeY = psizeY or 612
  return JavaLoader:add('ScreenUnit', name, {sizeX, sizeY})  
end

function ReceiverUnit(name)
  return JavaLoader:add('ReceiverUnit', name, {})  
end

function addChannel(id, channel, script)
  return JavaLoader:set('addChannel', {id, channel, script})  
end

function EmitterUnit(name)
  return JavaLoader:add('EmitterUnit', name, {})  
end

function GyroUnit(name)
  return JavaLoader:add('GyroUnit', name, {})  
end

function DoorUnit(name)
  return JavaLoader:add('DoorUnit', name, {})  
end

function LandingGearUnit(name)
  return JavaLoader:add('LandingGearUnit', name, {})  
end

function SwitchUnit(name)
  return JavaLoader:add('SwitchUnit', name, {})  
end

function LightUnit(name)
  return JavaLoader:add('LightUnit', name, {})  
end

function ForceFieldUnit(name)
  return JavaLoader:add('ForceFieldUnit', name, {})  
end

function RadarUnit(name, range, scriptEnter, scriptExit)
  return JavaLoader:add('RadarUnit', name, {range, scriptEnter, scriptExit})  
end

function moveElement(obj, x, y)
  return JavaLoader:set('moveElement', {obj, x, y})
end

