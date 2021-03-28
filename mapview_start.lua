--[[    
    mapview_start.lua 
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

range = radar1.getRange();

svgRadar = [[
    <svg x="2vw" y="0vh" width="100vh" height="100vh" style="background-color: rgba(0,190,0,1);">
        <g x="2vw" y="0px" width="99vh" height="99vh">
        <line x1="2vh"  y1="50vh" x2="98vh" y2="50vh" stroke-dasharray="1, 1" stroke="#000000"/>
        <line x1="50vh"  y1="2vh" x2="50vh" y2="98vh" stroke-dasharray="1, 1" stroke="#000000"/>
        <circle fill="none" stroke="#000000" stroke-width="0.5" cx="50vh" cy="50vh" r="2vh"/> 
        <circle fill="none" stroke="#000000" stroke-width="0.5" cx="50vh" cy="50vh" r="10vh"/> 
        <circle fill="none" stroke="#000000" stroke-width="0.5" cx="50vh" cy="50vh" r="18vh"/> 
        <circle fill="none" stroke="#000000" stroke-width="0.5" cx="50vh" cy="50vh" r="26vh"/> 
        <circle fill="none" stroke="#000000" stroke-width="0.5" cx="50vh" cy="50vh" r="32vh"/> 
        <circle fill="none" stroke="#000000" stroke-width="0.5" cx="50vh" cy="50vh" r="40vh"/>
        <circle fill="none" stroke="#000000" stroke-width="0.5" cx="50vh" cy="50vh" r="48vh"/>
        <circle fill="none" stroke="#FFFFFF" stroke-width="0.5" cx="50vh" cy="50vh" r="50vh"/>
        <text x="585" y="300" font-family="Verdana" font-size="15">x</text>
        <text x="310" y="24" font-family="Verdana" font-size="15">y</text>
        <text x="17" y="20" font-family="Verdana" font-size="13">Map view</text>
        <text x="510" y="600" font-family="Verdana" font-size="10">Range: ]]..(range/1000)..[[km</text>
        </g>%s</svg>
]]


htmlTableHeader = [[
<style>
   th { font-family: Arial; border: 1px solid blue; background-color: #0033FF; font-size: 2.2vh; color: #FFFFFF;}
   tr { font-family: Arial; background-color: #000044; font-size: 2.2vh; color: #FFFFFF;}
   table {border: 1px solid black; background-color: #0033FF;}
   div { background-color: #000428;}
}
</style>
<div style="width: 41.2vw; left:59vw; top:0vh; height:100vh; position: absolute;">
<b style="font-family: Arial; font-size: 2.5vh; color: #FFFFFF;">Entries list<b>
<table width="100%">
<thead>
  <tr>
    <th>id</th>
    <th style="text-align: left;">Name</th>
    <th style="text-align: left;">owner</th>
    <th>size</th>
    <th>state</th>
  </tr>
</thead>
<tbody>
]]

htmlTableFooter = [[</tbody>
</TABLE>
</div>
]]


-- timer
unit.setTimer("loop", 0.07)