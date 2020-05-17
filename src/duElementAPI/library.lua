--[[    
    library.lua 
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
ilibrary = {id, 'Library'}
function ilibrary:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self

  if _verboseLUA == 1 then print("[LUA] Library loaded") end
    
  return o
end

function ilibrary:systemResolution3(vec_c1, vec_c2, vec_c3, vec_c0)
   return JavaLibrary:get({"systemResolution3", vec_c1, vec_c2, vec_c3, vec_c0})    
end

function ilibrary:systemResolution2(vec_c1, vec_c2, vec_c0)
   return JavaLibrary:get({"systemResolution2", vec_c1, vec_c2, vec_c0})    
end

Library = ilibrary:new{0, 'Library'}
Library.systemResolution3 = function(vec_c1, vec_c2, vec_c3, vec_c0) return ilibrary:systemResolution3(vec_c1, vec_c2, vec_c3, vec_c0) end
Library.systemResolution2 = function(vec_c1, vec_c2, vec_c0) return ilibrary:systemResolution2(vec_c1, vec_c2, vec_c0) end

