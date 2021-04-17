--[[    
    radar_load.lua
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

-- Unit
UnitStart = loadScript('flymart_radar_pod_start.lua')
UnitTick = loadScript('flymart_radar_pod_tick.lua')
UnitStop = "print('stopped')" 
obj = Unit(UnitStart, UnitStop)

-- setup a timer/tick event
setupTimer(obj, 'loop', UnitTick)


playerList = {} -- also used for owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0,0,0}}
playerList[2] = {id = 1, name = 'Nmare418', worldPos = {131,-33,36}}
playerList[3] = {id = 2, name = 'Roger', worldPos = {123,-23,64}}
playerList[4] = {id = 3, name = 'Robert', worldPos = {113,-623,46}}
playerList[5] = {id = 4, name = 'Marie', worldPos = {114,-633,56}}
playerList[6] = {id = 5, name = 'Claude', worldPos = {124,-63,26}}
playerList[7] = {id = 6, name = 'Sandra', worldPos = {125,-33,66}} 
playerList[8] = {id = 7, name = 'Albert', worldPos = {125,-33,66}} 
local owner = 2

constructList = {}
-- add ships
for i = 9, 155 do
  local x = math.random(200000)-100000
  local y = math.random(200000)-100000
  local speedx = math.random(120)-60
  local speedy = math.random(120)-60   
  constructList[i] = {id = i, owner = 1, name = 'ship '..i, ctype='dynamic',  pos = {x,y,0}, size = {64,128,122}, speed = {speedx,speedy,0}, mass = 2101.323 }
end
-- Transponder activated contructs
constructList[1] = {id = 1, owner = 7, name = 'Base 1', ctype='static',  pos = {133,-46233,66}, size = {256,256,122}, speed = {0,0,0}, mass = 2101.323, transponder= true }
constructList[2] = {id = 2, owner = 2, name = 'Ship 1', ctype='dynamic',  pos = {14353,3233,59}, size = {16,16,12}, speed = {125,134,0}, mass = 12.43, transponder= true}
constructList[3] = {id = 3, owner = 3, name = 'hover 1', ctype='dynamic',  pos = {-80033,61233,36}, size = {16,16,22}, speed = {10,-125,0}, mass = 21.323, transponder= true }
constructList[3] = {id = 3, owner = 1, name = 'hover 3', ctype='dynamic',  pos = {-233,11233,36}, size = {32,13,22}, speed = {-160,125,0}, mass = 21.323, transponder= true }
constructList[4] = {id = 4, owner = 4, name = 'Base 2', ctype='static',  pos = {11145,-89233,55}, size = {128,124,112}, speed = {0,0,0}, mass = 4201.323, transponder= true }
constructList[5] = {id = 5, owner = 5, name = 'ship3', ctype='dynamic',  pos = {-73855,-1233,59}, size = {32,34,23}, speed = {-44,-155,0}, mass = 101.323, transponder= true }
constructList[6] = {id = 6, owner = 0, name = 'Flymart mobile shop 3', ctype='dynamic',  pos = {-76233, 13400, 67}, size = {16,13,6}, speed = {0,0,0}, mass = 31.323, transponder= true } 
constructList[7] = {id = 7, owner = 0, name = 'Base 3', ctype='static',  pos = {-16033,66033,66}, size = {256,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= true }
constructList[8] = {id = 8, owner = 6, name = 'Base 4', ctype='static',  pos = {-75000,60000,0}, size = {256,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= true }
constructList[9] = {id = 9, owner = 0, name = 'Base 5', ctype='static',  pos = {62300,64000,0}, size = {256,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= true }
constructList[10] = {id = 10, owner = 0, name = 'Base 6', ctype='static',  pos = {41000,-31400,0}, size = {64,34,45}, speed = {0,0,0}, mass = 2101.323, transponder= true }
--[[
-- add static bases
for i = 11, 140 do
  local x = math.random(200000)-100000
  local y = math.random(200000)-100000
  constructList[i] = {id = i, owner = 1, name = 'b'..i, ctype='static',  pos = {x,y,0}, size = {128,134,122}, speed = {0,0,0}, mass = 2101.323 }
end
]]
-- Screen
obj = ScreenUnit('screen1', 512, 307)
obj = ScreenUnit('screen2', 512, 307)
-- obj = ScreenUnit('screen3', 512, 307)
-- obj = ScreenUnit('screen4', 512, 307)
-- obj = ScreenUnit('screen5', 512, 307)
-- obj = ScreenUnit('screen6', 512, 307)


-- playerlist, constructlist, main player
setupDatabase(playerList, constructList, owner)


obj = RadarUnit('radar1', 100000, scriptEnter, scriptExit)
obj = LightUnit('SignalLight')

-- A json lib is included in the PRELOAD session (https://github.com/rxi/json.lua)
-- print(json.encode(constructList))

