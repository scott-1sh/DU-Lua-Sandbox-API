--[[    
    emitter_start.lua
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
screen1.activate()
screen1.clear()

htmlBackground1 = [[
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

text = '<br/> click on buttons to send messages<br/><br/>'

-- timer
unit.setTimer("loop", 0.07)
