--[[    
    Hud_load.lua
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
verboseJava(1)

-- Unit
startScript =  loadScript('Hud_start.lua')
obj = Unit(startScript, '')

-- hud
updateScript =  loadScript('Hud_update.lua')

-- Large hud
-- speed 25ms
setHUD(updateScript, 45, 210, 1, 1100, 800)

-- To setup a hud at the default position
-- setHUD(updateScript) 


playerList = {} -- also used as owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0,0,0}}
playerList[2] = {id = 1, name = 'Ben', worldPos = {131,-33,36}}
playerList[3] = {id = 2, name = 'Jp', worldPos = {123,-23,64}}
playerList[4] = {id = 3, name = 'Bob', worldPos = {113,-623,46}}
playerList[5] = {id = 4, name = 'Marie', worldPos = {114,-633,56}}
playerList[6] = {id = 5, name = 'Claude', worldPos = {124,-63,26}}
playerList[7] = {id = 6, name = 'Sandra', worldPos = {125,-33,66}} 
playerList[8] = {id = 7, name = 'Albert', worldPos = {125,-33,66}} 

constructList = {}
constructList[1] = {id = 1, owner = 7, name = 'Base 1', ctype='static',  pos = {133,-6233,66}, size = {115,134,122}, speed = {0,0,0}, mass = 2101.323 }
constructList[2] = {id = 2, owner = 2, name = 'Ship 1', ctype='dynamic',  pos = {4353,3233,59}, size = {15,6,12}, speed = {25,34,0}, mass = 12.43 }
constructList[3] = {id = 3, owner = 3, name = 'hover 1', ctype='dynamic',  pos = {-233,1233,36}, size = {12,13,22}, speed = {60,-125,0}, mass = 21.323 }
constructList[3] = {id = 3, owner = 1, name = 'hover 3', ctype='dynamic',  pos = {-233,1233,36}, size = {12,13,22}, speed = {-60,125,0}, mass = 21.323 }
constructList[4] = {id = 4, owner = 4, name = 'Base 2', ctype='static',  pos = {1145,-2233,55}, size = {124,124,112}, speed = {0,0,0}, mass = 4201.323 }
constructList[5] = {id = 5, owner = 5, name = 'ship3', ctype='dynamic',  pos = {-3855,-1233,59}, size = {45,34,23}, speed = {-114,-155,0}, mass = 101.323 }
constructList[6] = {id = 6, owner = 0, name = 'hover 2', ctype='dynamic',  pos = {-6233, 3400, 67}, size = {14,13,6}, speed = {0,0,0}, mass = 31.323 } 
constructList[7] = {id = 7, owner = 0, name = 'Base 3', ctype='static',  pos = {-6033,6033,66}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323 }
constructList[8] = {id = 8, owner = 6, name = 'Base 4', ctype='static',  pos = {-5000,5000,0}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323 }
constructList[9] = {id = 9, owner = 0, name = 'Base 5', ctype='static',  pos = {2300,4000,0}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323 }
constructList[10] = {id = 10, owner = 0, name = 'Base 6', ctype='static',  pos = {4000,-400,0}, size = {44,34,45}, speed = {0,0,0}, mass = 2101.323 }

-- setup the internal database. playerlist, constructlist, main player
setupDatabase(playerList, constructList, 3)

-- Setup a core with the construct id: 2
obj = Core(16, 'dynamic', 7.3, 2)

