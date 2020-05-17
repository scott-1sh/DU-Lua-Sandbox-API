--[[
    radar_start.lua 
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

htmlTable = [[
<style>
   th { border: 1px solid blue; background-color: #000000;}
   table, td {border: 1px solid black; background-color: #0000FF;}
  
}
</style>
<TABLE style="width: 900px;">
<thead>
  <tr>
    <th>id</th><th>Name</th><th>owner</th><th>size</th><th>pos</td><th>Type</th>
  </tr>
</thead>
<tbody>
<div class="txt">%s</div>
</tbody>
</TABLE>
]]

screen1.addContent(0,0,htmlBackground1)

-- timer
self.setTimer("loop", 0.25)