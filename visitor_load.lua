--[[    
    visitor_load.lua 
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
verboseLua(0)
verboseJava(0)

setName('Visitor')

-- Unit
UnitStart = loadScript('visitor_start.lua')
UnitStop = "print('stopped')" 
obj = Unit(UnitStart, UnitStop)

-- Databank
obj = DataBankUnit('dbVisitor')

-- Screens
obj = ScreenUnit('screen1', 512, 306)
-- moveElement(obj, 200, 5)

obj = ScreenUnit('screen2', 512, 306)
-- moveElement(obj, 200, 335)

obj = ScreenUnit('screen3', 512, 306)
-- moveElement(obj, 800, 5)


playerList = {} -- also used as owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0,0,0}}
playerList[2] = {id = 1, name = 'Nmare418', worldPos = {131,-33,36}}
playerList[3] = {id = 2, name = 'Julie', worldPos = {0,0,0}}
playerList[4] = {id = 3, name = 'Roger', worldPos = {123,-23,64}}
playerList[5] = {id = 4, name = 'Robert', worldPos = {113,-623,46}}
playerList[6] = {id = 5, name = 'Marie', worldPos = {114,-633,56}}
playerList[7] = {id = 6, name = 'Claude', worldPos = {124,-63,26}}
playerList[8] = {id = 7, name = 'Sandra', worldPos = {125,-33,66}} 
playerList[9] = {id = 8, name = 'Bob', worldPos = {125,-33,66}} 
playerList[10] = {id = 9, name = 'Jean', worldPos = {113,-623,46}}
playerList[11] = {id = 10, name = 'Alice', worldPos = {114,-633,56}}
playerList[12] = {id = 11, name = 'Eric', worldPos = {124,-63,26}}
playerList[13] = {id = 12, name = 'Jhon', worldPos = {125,-33,66}} 
playerList[14] = {id = 13, name = 'Alphonse', worldPos = {125,-33,66}} 
playerList[15] = {id = 14, name = 'Rick', worldPos = {125,-33,66}} 
playerList[16] = {id = 15, name = 'Marie-claude', worldPos = {125,-33,66}} 
playerList[17] = {id = 16, name = 'Mario', worldPos = {125,-33,66}} 
playerList[18] = {id = 17, name = 'Donald', worldPos = {125,-33,66}} 
playerList[19] = {id = 18, name = 'Xavier', worldPos = {125,-33,66}} 
playerList[20] = {id = 19, name = 'Line', worldPos = {125,-33,66}} 
playerList[21] = {id = 20, name = 'Maude', worldPos = {125,-33,66}} 
playerList[22] = {id = 21, name = 'Bart', worldPos = {125,-33,66}} 
playerList[23] = {id = 22, name = 'Lisa', worldPos = {125,-33,66}} 


constructList = {}
constructList[1] = {id = 1, owner = 7, name = 'Base 1', ctype='static',  pos = {133,-6233,66}, size = {115,134,122}, speed = {0,0,0}, mass = 2101.323, transponder= false }
constructList[2] = {id = 2, owner = 1, name = 'Ship 1', ctype='dynamic',  pos = {4353,3233,59}, size = {15,6,12}, speed = {0,0,0}, mass = 12.43, transponder= true }
constructList[3] = {id = 3, owner = 3, name = 'hover 1', ctype='dynamic',  pos = {-233,1233,36}, size = {12,13,22}, speed = {60,-125,0}, mass = 21.323, transponder= false }
constructList[3] = {id = 3, owner = 1, name = 'hover 3', ctype='dynamic',  pos = {-233,1233,36}, size = {12,13,22}, speed = {-60,125,0}, mass = 21.323, transponder= false }
constructList[4] = {id = 4, owner = 4, name = 'Base 2', ctype='static',  pos = {1145,-2233,55}, size = {124,124,112}, speed = {0,0,0}, mass = 4201.323, transponder= false }
constructList[5] = {id = 5, owner = 5, name = 'ship3', ctype='dynamic',  pos = {-3855,-1233,59}, size = {45,34,23}, speed = {-114,-155,0}, mass = 101.323, transponder= true }
constructList[6] = {id = 6, owner = 0, name = 'hover 2', ctype='dynamic',  pos = {-6233, 3400, 67}, size = {14,13,6}, speed = {0,0,0}, mass = 31.323, transponder= false } 
constructList[7] = {id = 7, owner = 1, name = 'Base 3', ctype='static',  pos = {-6033,6033,66}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= false }
constructList[8] = {id = 8, owner = 6, name = 'Base 4', ctype='static',  pos = {-5000,5000,0}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= false }
constructList[9] = {id = 9, owner = 1, name = 'Base 5', ctype='static',  pos = {2300,4000,0}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= false }
constructList[10] = {id = 10, owner = 0, name = 'Base 6', ctype='static',  pos = {4000,-400,0}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= false }

owner = 8
setupDatabase(playerList, constructList, owner)

