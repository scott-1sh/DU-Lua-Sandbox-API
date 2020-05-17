--[[    
    helloworld2_load.lua 
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

showOnScreen(1)
verboseLua(1)
verboseJava(1)

-- Load a script from file system
UnitStart = loadScript('helloworld2_start.lua')

-- loadScript return -1 in case of error
if UnitStart == -1 then
  abort("Script not found")
end

UnitStop = "print('stopped')"

-- Unit
obj = Unit(UnitStart, UnitStop)

-- Remember to use vh and vw annotation if you change the size of the screen
obj = ScreenUnit('screen1', 512, 306)
