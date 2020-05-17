--[[    
    databank_start.lua 
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
screen1.activate()

svgBackground = [[
<svg width="1024" height="612">
  <rect width="1024" height="612" style="fill:rgb(0,0,55);stroke-width:10;stroke:rgb(100,100,175)" />
</svg>
]]

htmlContent = [[
<!DOCTYPE html>
<html>
<head>
<style>
  .txt { color:#FFFFFF;
         font: bold 18px Verdana;
         top: 25px;
         left: 25px;
         position: absolute;}
   </style>
</head>
<body>
<div class="txt">%s</div>
</body>
</html>
]]

local text = 'Clearing the database<br/>'
db1.clear()

text = text..'<br/>db1: '..db1.getNbKeys()..' record(s)<br/>'

text = text..'<br/> Inserting String'
db1.setStringValue('teststring', 'Hello world!') 

text = text..'<br/> Inserting Int'
db1.setIntValue('testint', 50000) 

text = text..'<br/> Inserting Float'
db1.setFloatValue('testfloat', 500.453366) 

text = text..'<br/><br/>db1: '..db1.getNbKeys()..' record(s)<br/>'

text = text..'<br/> Record "teststring" contain: '..db1.getStringValue('teststring')
text = text..'<br/> Record "testint" contain: '..db1.getIntValue('testint')
text = text..'<br/> Record "testfloat" contain: '..db1.getFloatValue('testfloat')
text = text..'<br/> Record "nothing" contain: '..(db1.getStringValue('nothing') or 'nil') -- inexistant key "nothing"

if db1.hasKey('testint') then 
   text = text..'<br/><br/> hasKey("testint") = true'
end

if db1.hasKey('nothing') == 0 then 
   text = text..'<br/> hasKey("nothing") = false'
end

text = text.."<br/><br/>getKeys() [json] ="..db1.getKeys()
text = text..'<br/><br/>double click the "console SQL" button to manage your database (in your web browser).'
text = text.."<br/> (default user/passwd for H2 is sa/sa)"

htmlText = string.format(htmlContent, text)

screen1.clear()
screen1.addContent(0,0,svgBackground)
screen1.addContent(0,0,htmlText)
