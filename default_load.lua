
-- Tips, use dofile to load a specific script as default 
-- dofile('visitor_load.lua')

-- json lib included (https://github.com/rxi/json.lua)

-- start on screen [0-1]
showOnScreen(1)
 
verboseLua(1)
verboseJava(1)


-- Unit
UnitStart = loadScript('default_start.lua')
UnitStop = "print('stopped')" 

obj = Unit(UnitStart, UnitStop)


setHUD()

playerList = {} -- also used as owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0,0,0}}
playerList[2] = {id = 1, name = 'Bob', worldPos = {131,-33,36}}
playerList[3] = {id = 2, name = 'Roger', worldPos = {123,-23,64}}
playerList[4] = {id = 3, name = 'Robert', worldPos = {113,-623,46}}
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
setupDatabase(playerList, constructList, 2)

-- radar
local scriptEnter = " "
local scriptExit = " "
-- obj = RadarUnit('radar1', 10000, scriptEnter, scriptExit)

-- screen
obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 220, 5)

-- Construct id 2 related to the constructList table (used in setupDatabase) 
obj = Core(16, 'dynamic', 7.3, 1)

obj = DataBankUnit('db1')

obj = ContainerUnit('Box1')

obj = GyroUnit('gyro')


obj = LightUnit('Light1')
obj = DoorUnit('Door3')
obj = ForceFieldUnit('Forcefield1')  
obj = SwitchUnit('Button1')
-- obj = LandingGearUnit('landing1')
obj = ButtonUnit('button1', 'toggle', "Light1.toggle() Door3.toggle() Forcefield1.toggle() Button1.toggle()")
-- obj = ReceiverUnit('receiver1')
-- addChannel(obj,'channel1', "print('hello')")
-- addChannel(obj,'channel2', "print('hello')")
-- addChannel(obj,'channel2', "print('hello')")
-- obj = EmitterUnit('emitter1') 
-- obj = Navigator()


--[[
for i, constr in ipairs( constructList) do
    print(i.." -"..constr['name'])
end
die('dead')
]]

