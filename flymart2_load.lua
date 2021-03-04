
showOnScreen(1)  
verboseLua(1)
verboseJava(0)

-- svg = "\nlogo = [["..loadScript('./src/pictures/logo.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/warehouse.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/warehouse2.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/LogoDUAPI_SVG.svg').."]]\n"

-- Unit
UnitStart =  loadScript('flymart2_start.lua')
-- UnitTick = loadScript('flymart2_tick.lua')
UnitStop = "print('stopped')" 
obj = Unit(UnitStart, UnitStop)

-- setup a timer named "loop" to use the UnitTick script
-- setupTimer(obj, 'loop', UnitTick)

obj = ScreenUnit('screen1', 512, 305)
moveElement(obj, 220, 5)

obj = ScreenUnit('screen2', 512, 305)
moveElement(obj, 220, 333)

obj = ScreenUnit('screen3', 512, 305)
moveElement(obj, 220, 660)

obj = ScreenUnit('monitor', 512, 305)
moveElement(obj, 750, 5)

obj = ContainerUnit('box1')
obj = ContainerUnit('box2')
obj = ContainerUnit('box3')
