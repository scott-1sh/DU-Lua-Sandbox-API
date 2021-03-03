--[[    
    emitter_load.lua 
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
verboseJava(0)

-- Unit
UnitStart = loadScript('emitter_start.lua')
UnitTick = loadScript('emitter_tick.lua')
UnitStop = "print('stopped')"

obj = Unit(UnitStart, UnitStop)
setupTimer(obj, 'loop', UnitTick)

if UnitStart == -1 or UnitTick == -1 then
  die("Script not found")
end

-- Setup a receiver
obj = ReceiverUnit('receiver1')

-- Add a channel/script to a receiver
addChannel(obj,'channel1', "text = text..'<br/><br/>'..channel..' - '..message")
addChannel(obj,'channel2', "text = text..'<br/><br/>'..channel..' - '..message")

-- Setup a emitter
obj = EmitterUnit('emitter1')

-- screen1
obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 205, 5)

obj = ButtonUnit('btnChannel1', 'test', 'emitter1.send("channel1", "Hello world!")')
obj = ButtonUnit('btnChannel2', 'test', 'emitter1.send("channel2", "Bonjour le monde!")')

