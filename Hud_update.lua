
t = earthDate(system.getTime())['hour']..':'..earthDate(system.getTime())['min']..":"..earthDate(system.getTime())['sec']

system.setScreen(string.format(htmlBox, t, core.getConstructMass(), core.g(),
										core.getAltitude(), (earthDate(system.getTime())['sec']*375)/60 ));
