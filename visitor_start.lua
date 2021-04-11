--[[    
    visitor_start.lua 
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

-- Visitor script
-- Keep 20 last visitors.  
-- Collect visitors name, time and daily visits. 
-- Notes: There no timezone detection for players in DU.  Thats why timestamp give inacurate values (timestamp follow the timezone).    
-- connect to a zone detector. Only need one execution per visit. You can connect multiple zone detector with a or sinal.

-- use dbVisitor.clear() to clear the database 
-- dbVisitor.clear()


htmlPlayers = [[
<html>
<header>
<style>
   body { background-color: #000099;}
   th {  color: #FFFFFF; font-size: 3.2vw; border: .4vw outset #0000FF; }
   table{ font-family: Verdana; font-size: 3.5vw; background-color: #000077;}
   .head { text-align: left; padding: .5vw; color: #FFFF00; font-size: 3vw;}
   td { background-color: #0000AA; color: #FFFFFF; border-left: .25vw solid #000099; border-bottom: .25vw solid #000099; }
   .ago { font-size: 2.4vw;  color: #9999FF;}
}
</style>
</header>
<body>
<TABLE style="width: 100VW;">
<thead>
  <tr>
  <tr>
    <th colspan="3">Visitors <span class="head">%s</span></th>
  </tr>
  <tr>
    <th class="head"><center>#</center></th>
    <th class="head">Name</th>
    <th class="head">last visit</th>
  </tr>
</thead>
<tbody>
%s
</tbody>
</TABLE>
</body>
</html>
]]

htmlStats = [[
<html><header>
<style>
   body { background-color: #000077;}
   th {  color: #FFFFFF; font-size: 4vw; border: .4vw outset #0000FF; }
   table{ margin-top: 9.8vw; margin-left: 2.5vw; font-family: Verdana; font-size: 4vw; background-color: #000044;}
   .head { text-align: left; padding: .5vw; color: #FFFF00; font-size: 2.5vw;}
   td { font-size: 4vw; text-align: center; background-color: #0000AA; color: #FFFFFF; border: .5vw inset #0000FF; }
   .ago { font-size: 2.4vw; color: #9999FF;}
   .value { font-size: 4.1vw; color: #FFFF00;}
}
</style>
</header><body>
<center>
<TABLE style="width: 90VW;">
<thead>
  <tr>
    <th>Visitors stats</span></th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>
	%s
    </td>
  </tr>
</tbody>
</TABLE>
</center>
</body></html>
]]

-- Compatibility: Lua-5.1
function split(str, pat)
   local t = {}  -- NOTE: use {n = 0} in Lua-5.0
   local fpat = "(.-)" .. pat
   local last_end = 1
   local s, e, cap = str:find(fpat, 1)
   while s do
      if s ~= 1 or cap ~= "" then
         table.insert(t, cap)
      end
      last_end = e+1
      s, e, cap = str:find(fpat, last_end)
   end
   if last_end <= #str then
      cap = str:sub(last_end)
      table.insert(t, cap)
   end
   return t
end

-- "time ago" format
function formatTimeLast(t)    
	local txt = ''
	local d = math.floor(t/86400)
	t = t - (d*86400)
	if d > 0 then
	  txt = d.." days " 
	end	
	local h = math.floor(t/3600)
    t = t - (h*3600);
	if h > 0 then 
	  txt = txt..h.." hour "
	end	
	local m = math.floor(t/60)
	if m > 0 and d == 0 then 
	  txt = txt..m.." min "
	end    
    if h == 0 and d == 0 and m == 0 then 
	  txt = "<1min "
	end
    return txt
end

-- transform index to arrays
function IndexToPlayerList(index)
	tmpVars = {}
	arrayVars = {}
	for i, var in pairs(split(index, ',')) do 
	   tmpVars = split(var, ':')  
	   arrayVars[i] = {}   
	   arrayVars[i]['time'] = tmpVars[1]
	   arrayVars[i]['name'] = tmpVars[2]  
	   -- print(i..")"..arrayVars[i]['time']..'-'..arrayVars[i]['name'])
	end
	return arrayVars
end

function PlayerListToIndex(plist)
  local index = ''
  local first = true
  for i, var in pairs(plist) do
	if not( var['name'] == nil or var['name'] == '' or var['name'] == 'unreachable') then
	  if not(first) then index = index..',' end
	  index = index..var['time']..':'..var['name']
	  first = false
	end  
  end
  return index
end 

-- pairs to string
function tablelength(T)
  local count 	= 0
  for _ in pairs(T) do count = count + 1 end
  return count
end


--###########
--# Visitor #
--###########
-- reset if not exist
if not dbVisitor.hasKey('[index]') then
  dbVisitor.clear()
  dbVisitor.setStringValue('[index]', ",")
  dbVisitor.setFloatValue('[MaintenanceDate]', system.getTime()-86401)
  dbVisitor.setIntValue('[Visitor]', 0)
  dbVisitor.setIntValue('[LastDayVisitor]', 0)
  dbVisitor.setIntValue('[TotalVisitor]', 0)
end

-- oldschool timestamp!
local timeStamp = system.getTime()

-- get index from database
local index = dbVisitor.getStringValue('[index]') 

-- string to array
local playerList = {{}}
playerList = IndexToPlayerList(index)

-- Who is activating the device
masterPlayerId = unit.getMasterPlayerId()
masterPlayerName = system.getPlayerName(masterPlayerId)

print(masterPlayerName)

-- new visit flag (for stats) 
local newVisit = true 

-- player is already in the index?
for i, userInfo in pairs(playerList) do
   if userInfo['name'] == masterPlayerName then      
	  -- if more then 1h, count as new visit
	  newVisit = (timeStamp-userInfo['time']>3600)
      -- remove from the list
	  playerList[i] = nil
   end
end

-- add a player
playerList[100] = {}
playerList[100]['time'] = timeStamp
playerList[100]['name'] = masterPlayerName

-- remove first visitor if more then 20
if tablelength(playerList) > 20 then
  playerList[1] = nil
end 

-- save to db
dbVisitor.setStringValue('[index]', PlayerListToIndex(playerList))


--####################
--# display visitors #
--####################
local row  = ""
local rowScreen1 = ""
local rowScreen2 = ""
local line = 1;
for i, userInfo in pairs(playerList) do
    if not( userInfo['name'] == nil or userInfo['name'] == '' or userInfo['name'] == 'unreachable')  then	
		row = "<tr>"
		row = row.."<td align=\"center\">"..line.."</td>"
		row = row.."<td style=\"color: #FFFF00;\"><b >"..userInfo['name'].."</b></td>"
		row = row.."<td> "..formatTimeLast(timeStamp-userInfo['time']).."<span class=\"ago\">ago</span></td>"
		row = row.."</tr>"
		if line <= 10 then
          rowScreen1 = rowScreen1..row
		else
          rowScreen2 = rowScreen2..row		
		end		
		line = line + 1
    end
end
screen1.activate()
screen1.clear()
screen2.activate()
screen2.clear()
screen1.addContent(0,0, string.format(htmlPlayers, '(1-10)', rowScreen1))
screen2.addContent(0,0, string.format(htmlPlayers, '(11-20)', rowScreen2))

--#########
--# stats #
--#########

-- maintenance time?
if timeStamp-dbVisitor.getFloatValue('[MaintenanceDate]') >86400 then
  dbVisitor.setIntValue('[LastDayVisitor]', dbVisitor.getIntValue('[Visitor]'))
  dbVisitor.setFloatValue('[MaintenanceDate]', timeStamp)
  dbVisitor.setIntValue('[Visitor]', 0)
  newVisit = 1
end

-- add a visit
if newVisit then
  dbVisitor.setIntValue('[Visitor]', dbVisitor.getIntValue('[Visitor]') + 1)
  dbVisitor.setIntValue('[TotalVisitor]', tonumber(dbVisitor.getIntValue('[TotalVisitor]')) + 1)  
end

--#################
--# display stats #
--#################
local lastMaintenance = dbVisitor.getFloatValue('[MaintenanceDate]') 
local todayVisitors = dbVisitor.getIntValue('[Visitor]')
local yesterdayVisitors = dbVisitor.getIntValue('[LastDayVisitor]')
local totalVisitors = dbVisitor.getIntValue('[TotalVisitor]')

local txtOut = 'Last maintenance: <span class="value">'..formatTimeLast(timeStamp-lastMaintenance)..'</span><span class=\"ago\">ago</span><br/>'
txtOut = txtOut..'<br/>Visitors today: <span class="value">'..todayVisitors..'</span>'
txtOut = txtOut..'<br/>Visitors yesterday: <span class="value">'..yesterdayVisitors..'</span>'
txtOut = txtOut..'<br/><br/>Total visitors: <span class="value">'..totalVisitors..'</span><br/>'

screen3.activate()
screen3.clear()
screen3.addContent(0,0, string.format(htmlStats,txtOut))


--#########
--# Close #
--#########
unit.exit()

