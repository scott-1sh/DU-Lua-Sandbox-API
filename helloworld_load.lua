--[[    
    helloworld_load.lua 
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

-- this script will setup a new sandbox where you can emulate a DU programming board with
-- the elements you created in this script 

-- start your sandbox on your computer screen [0-1]
showOnScreen(1) 

-- verbose lua debug info [0 false 1 true] 
verboseLua(1)
-- verbose java debug info [0-1]
verboseJava(0)


-- #### UNIT ####
-- start event script (see next tutorials (helloworld2_load) to learn how load scripts from files system)
UnitStart = [[ 
  screen1.addContent(0,0,"<b>hello world</b>")
]]
UnitStop = "print('closing')"

-- insert a new unit
-- *** UNIT is a MANDATORY element if you want to use a start event. ***
obj = Unit(UnitStart, UnitStop)

-- #####################################################################################################
-- # Elements are placed on the first free position on the screen (from top left to bottom right)      #
-- # You can use moveElement(object, x, y) to manually place elements on the sandbox.                  #
-- #####################################################################################################

-- #### SCREEN ####
-- insert a screen named screen1 with a size of 1024/612  
-- You can use smaller/bigger screen if you use virtual width/height annotation (vw and vh) 
obj = ScreenUnit('screen1', 1024, 612)

-- move the screen widget at 300, 5
moveElement(obj, 300, 5)


-- You sandbox will be created with a unit and a screen (named screen1) at the end of this script.
-- Preload's (LUA) State and environment (memory and objects) will be reset so you start with a fresh 
-- environment with all elements you created.