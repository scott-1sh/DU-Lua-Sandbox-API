

showOnScreen(1)
verboseLua(1)
verboseJava(1)

-- Load a script from file system
UnitStart = loadScript('debug_start.lua') -- will show you how to use DUos's debugging functions
UnitStop = "print('stopped')"

obj = Unit(UnitStart, UnitStop)

obj = ScreenUnit('screen1', 1024, 612)
