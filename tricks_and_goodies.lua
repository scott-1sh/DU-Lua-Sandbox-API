-- usefull tricks and goodies
-- Thx to every contributors
-- Want to add something cool? contact me on discord at nmare418#6397


-- Parse a position string (::pos{0,2,-4.5824,127.3228,27.1807})
-- thx to Archaego from DU Open Source Initiative 
local pos = '::pos{0,2,-4.5824,127.3228,27.1807}'
local num = ' *([+-]?%d+%.?%d*e?[+-]?%d*)'
local posPattern = '::pos{' .. num .. ',' .. num .. ',' ..  num .. ',' .. num ..  ',' .. num .. '}'    
local systemId, bodyId, latitude, longitude, altitude = string.match(pos, posPattern);


-- Check if in safe zone
local function checkZone()
  if (vec3(core.getConstructWorldPos()) - vec3(13771471, 7435803, -128971)):len2 < 324e12 then
    return "Safe zone"
  else
    return "PvP zone"
  end
end


-- Check in safe zone (for all planets)
-- thx to SeM (SeM#8188) from DU Open Source Initiative 
local safeZones = {
  {id = 1, name = "Madis", center = {17465536,22665536,-34464}},
  {id = 10, name = "Madis Moon 1", center = {17448118.224,22966846.286,143078.82}},
  {id = 11, name = "Madis Moon 2", center = {17194626,22243633.88,-214962.81}},
  {id = 12, name = "Madis Moon 3", center = {17520614,22184730,-309989.99}},
  {id = 2, name = "Alioth", center = {-8,-8,-126303}},
  {id = 21, name = "Alioth Moon 1", center = {457933,-1509011,115524}},
  {id = 22, name = "Alioth Moon 4", center = {-1692694,729681,-411464}},
  {id = 26, name = "Sanctuary Moon", center = {-1404835,562655,-285074}},
  {id = 3, name = "Thades", center = {29165536,10865536,65536}},
  {id = 30, name = "Thades Moon 1", center = {29214402,10907080.695,433858.2}},
  {id = 31, name = "Thades Moon 2", center = {29404193,10432768,19554.131}},
  {id = 4, name = "Talemai", center = {-13234464,55765536,465536}},
  {id = 42, name = "Talemai Moon 1", center = {-13058408,55781856,740177.76}},
  {id = 40, name = "Talemai Moon 2", center = {-13503090,55594325,769838.64}},
  {id = 41, name = "Talemai Moon 3", center = {-12800515,55700259,325207.84}},
  {id = 5, name = "Feli", center = {-43534464,22565536,-48934464}},
  {id = 50, name = "Feli Moon 1", center = {-43902841.78,22261034.7,-48862386}},
  {id = 6, name = "Sicari", center = {52765536,27165538,52065535}},
  {id = 7, name = "Sinnen", center = {58665538,29665535,58165535}},
  {id = 70, name = "Sinnen Moon 1", center = {58969616,29797945,57969449}},
  {id = 8, name = "Teoma", center = {80865538,54665536,-934463.94}},
  {id = 9, name = "Jago", center = {-94134462,12765534,-3634464}},
  {id = 100, name = "Lacobus", center = {98865536,-13534464,-934461.99}},
  {id = 102, name = "Lacobus Moon 1", center = {99180968,-13783862,-926156.4}},
[10:51]
  {id = 103, name = "Lacobus Moon 2", center = {99250052,-13629215,-1059341.4}},
  {id = 101, name = "Lacobus Moon 3", center = {98905288.17,-13950921.1,-647589.53}},
  {id = 110, name = "Symeon", center = {14165536,-85634465,-934464.3}},
  {id = 120, name = "Ion", center = {2865536.7,-99034464,-934462.02}},
  {id = 121, name = "Ion Moon 1", center = {2472916.8,-99133747,-1133582.8}},
  {id = 122, name = "Ion Moon 2", center = {2995424.5,-99275010,-1378480.7}}
}

local function safeZone(WorldPos)
  local radius = 500000
  local min_dist, dist, key = math.huge
  local safe = false
  local safeWorldPos = vec3({13771471,7435803,-128971})
  local safeRadius = 18000000 
  if vec3(WorldPos):dist(safeWorldPos) < safeRadius then 
    return true, distanceFormat(math.abs(vec3(WorldPos):dist(safeWorldPos) - safeRadius)), "Safe Zone", nil
  end
  for k, v in pairs(safeZones) do
    dist = vec3(WorldPos):dist(vec3(v.center))
    if dist < radius then safe = true end
    if dist < min_dist then
      min_dist = dist 
      key = k
    end
  end
  return safe, distanceFormat(math.abs(min_dist - radius)), safeZones[key].name, safeZones[key].id
end