
screen1.activate()
screen1.clear()

-- debug functions
unit._setMass(5600)
unit._setMaxHitPoints(1000)
unit._setHitPoints(999)
unit._setIntegrity(50)
Light1.toggle()
Light1.toggle()
Box1._setItemsMass(1350.344)

text = '<br/>unit mass: '..unit.getMass()
text = text..'<br/>unit max hp: '..unit.getMaxHitPoints()
text = text..'<br/>unit hp: '..unit.getHitPoints()
text = text..'<br/>unit max hp: '..unit.getIntegrity()
-- text = text..'<br/><br/>(gyro) Pitch: '..gyro.getPitch()
-- text = text..'<br/>(gyro) WorldUp: ['..gyro.worldUp()[1]..','..gyro.worldUp()[2]..','..gyro.worldUp()[3]..']'
text = text..'<br/><br/>(library) systemResolution3: ['..library.systemResolution3({1,1,1}, {1,1,1}, {1,1,1}, {1,2,3})[1]..','..library.systemResolution3({1,1,1}, {1,1,1}, {1,1,1}, {1,2,3})[2]..','..library.systemResolution3({1,1,1}, {1,1,1}, {1,1,1}, {1,2,3})[3]..']'
text = text..'<br/>(core) Construct Mass: '..core.getConstructMass()
text = text..'<br/>(core) g: '..core.g()
text = text..'<br/>(core) ConstructId: '..core.getConstructId()

text = text..'<br/>Elements list:'
for i, elem in ipairs(core.getElementList()) do
    text= text.."<br/>&nbsp;&nbsp;&nbsp;  "..i.." - "..core.getElementName(elem).." ["..core.getElementTypeById(elem) .."]"
end

-- text = text..'Core (self): '..database.getPlayer(3)['name']

-- text = text..'Core (self): '..database.getPlayer(3)['name']
text = text..'<br/><br/>Player id 3 [name]: '..database.getPlayer(3)['name']
-- ztext = text..'<br/><br/>Radar1 getRange: '..radar1.getRange()
text = text..'<br/><br/>db1: '..db1.getNbKeys()..' record(s)'

screen1.addContent(0,0,'<b>default_load.lua</b><br/>'..text)
