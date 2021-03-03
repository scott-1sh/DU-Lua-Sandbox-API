--[[    
    buttonManager_tick.lua 
    Copyright (C) 2021 Stephane Boivin aka Nmare418 (Discord: Nmare418#639)
    
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

screen1.clear()

local mouseX = screen1.getMouseX()
local mouseY = screen1.getMouseY()

local htmlMouseInfo = screen1.getMouseX().."/"..screen1.getMouseY()..":"..screen1.getMouseState()
local content = ''

-- buttons events
if bmLight:OnClick('light1') == 1 then Light1.toggle() end
if bmLight:OnClick('light2') == 1 then Light2.toggle() end
if bmLight:OnClick('light3') == 1 then Light3.toggle() end
if bmDoor:OnClick('door1') == 1 then Door1.toggle() end
if bmDoor:OnClick('door2') == 1 then Door2.toggle() end
if bmDoor:OnClick('door3') == 1 then Door3.toggle() end

-- display tabbs buttons
content = content..bm:Display(screen1.getMouseX(),screen1.getMouseY(),screen1.getMouseState())

-- Tabbs
if bm:IsToggled('door_tab') then 
  content = content..string.format(svgPanel,'door')
  -- display door buttons
  content = content..bmDoor:Display(screen1.getMouseX(),screen1.getMouseY(),screen1.getMouseState())
end
if bm:IsToggled('light_tab') then
  content = content..string.format(svgPanel,'light')
  -- display light buttons
  content = content..bmLight:Display(screen1.getMouseX(),screen1.getMouseY(),screen1.getMouseState())
end

-- build up the screen
local htmlText = string.format(htmlContent, content, htmlMouseInfo)

background1 = screen1.addContent(0,0,svgBackground)
background1 = screen1.addContent(0,0,htmlText) 
