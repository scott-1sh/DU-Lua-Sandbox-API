
showOnScreen(1)  
verboseLua(1)
verboseJava(0)

svg = "\nlogo = [["..loadScript('./src/pictures/logo.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/warehouse.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/warehouse2.svg').."]]\n"
-- svg = "\nlogo = [["..loadScript('./src/pictures/LogoDUAPI_SVG.svg').."]]\n"

-- Unit
UnitStart =  svg..loadScript('splitscreenx4_start.lua')
UnitTick = loadScript('splitscreenx4_tick.lua')
UnitStop = "print('stopped')" 
obj = Unit(UnitStart, UnitStop)

-- setup a timer named "loop" to use the UnitTick script
setupTimer(obj, 'loop', UnitTick)

obj = ScreenUnit('screen1', 306, 153)
moveElement(obj, 200, 5)

obj = ScreenUnit('screen2', 306, 153)
moveElement(obj, 508, 5)

obj = ScreenUnit('screen3', 306, 153)
moveElement(obj, 200, 160)

obj = ScreenUnit('screen4', 306, 153)
moveElement(obj, 508, 160)


