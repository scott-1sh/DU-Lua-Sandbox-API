--[[    
    bouncingball_load.lua
    Copyright (C) 2021 Stephane Boivin (Discord: Nmare418#6397)
    
    This file is part of "DU lua sandbox API".

    "DU lua sandbox API" is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    "DU lua sandbox API" is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with "DU lua sandbox API".  If not, see <https://www.gnu.org/licenses/>.
]]
showOnScreen(1)  
verboseLua(1)
verboseJava(0)

-- Unit
UnitStart = loadScript('bouncingball_start.lua')
UnitTick = loadScript('bouncingball_tick.lua')
UnitStop = "print('stopped')" 
obj = Unit(UnitStart, UnitStop)

-- setup a timer named loop, using the UnitTick script
setupTimer(obj, 'loop', UnitTick)

obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 200, 5)
