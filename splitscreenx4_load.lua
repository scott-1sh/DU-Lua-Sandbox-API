--[[    
    splitscreenx4_load.lua 
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
showOnScreen(1)  
verboseLua(0)
verboseJava(0)

svg = "\nlogo = [["..loadScript('./src/pictures/logo.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/warehouse.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/warehouse2.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/LogoDUAPI_SVG.svg').."]]\n"

-- Unit
UnitStart =  svg..loadScript('splitscreenx4_start.lua')
UnitTick = loadScript('splitscreenx4_tick.lua')
UnitStop = "print('stopped')" 
obj = Unit(UnitStart, UnitStop)

-- setup a timer named "loop" to use the UnitTick script
setupTimer(obj, 'loop', UnitTick)

obj = ScreenUnit('screen1', 306, 153)
moveElement(obj, 210, 5)

obj = ScreenUnit('screen2', 306, 153)
moveElement(obj, 518, 5)

obj = ScreenUnit('screen3', 306, 153)
moveElement(obj, 210, 160)

obj = ScreenUnit('screen4', 306, 153)
moveElement(obj, 518, 160)


