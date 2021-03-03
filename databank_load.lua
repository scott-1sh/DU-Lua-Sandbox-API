--[[    
    databank_load.lua 
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
verboseLua(1)
verboseJava(1)

-- Unit
UnitStart = loadScript('databank_start.lua')
UnitStop = "print('stopped')" 
obj = Unit(UnitStart, UnitStop)

-- Databank
obj = DataBankUnit('db1')

-- Screen
obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 200, 5)
