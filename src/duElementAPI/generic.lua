--[[    
    generic.lua 
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
genericElement = {}
function genericElement:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self    
  return o
end

function genericElement:show() end
function genericElement:hide() end
function genericElement:getData() end
function genericElement:getId() return self.id end
function genericElement:getMass() return self.mass end
function genericElement:getElementClass() return self.class end
function genericElement:getIntegrity() return self.integrity end
function genericElement:getHitPoints() return self.hitPoints end
function genericElement:getMaxHitPoints() return self.maxHitPoints end
function genericElement:setMass(mass) self.mass = mass end
function genericElement:setHitPoints(hitPoints) self.hitPoints = hitPoints end
function genericElement:setIntegrity(integrity) self.integrity = integrity end
function genericElement:setMaxHitPoints(maxHitPoints) self.maxHitPoints = maxHitPoints end
