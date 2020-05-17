--[[
    debug_load.lua by Nmare418, 2019
    
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
verboseLua(1)
verboseJava(1)

-- Load a script from file system
UnitStart = loadScript('debug_start.lua') -- will show you how to use DUos's debugging functions
UnitStop = "print('stopped')"

obj = Unit(UnitStart, UnitStop)

obj = ScreenUnit('screen1', 1024, 612)