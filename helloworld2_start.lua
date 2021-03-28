--[[    
    helloworld2_start.lua
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

-- background 
svgBackground = [[
<svg width="100vw" height="100vh">
  <rect width="99vw" height="99vh" style="fill:rgb(0,0,55);stroke-width:2vw;stroke:rgb(100,100,175)" />
</svg>
]]

htmlContent = [[
<!DOCTYPE html>
<html>
<head>
<style>
  .hello { border-radius:3vw 3vw 3vw 3vw;
           background: #000055;
           color:#FFFFFF;
           font: bold 10vh Verdana;
           border:1vw solid #ccc;
           text-align: center;
           position: relative;}
   </style>
</head>
<body>
<div class="hello" style="left: 27.5vw; top: 32.5vh; width: 40vw;" > Hello world! </div>
</body>
</html>
]]

screen1.addContent(0, 0, svgBackground)
screen1.addContent(0, 0, htmlContent)
