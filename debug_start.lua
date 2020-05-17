-- Every elements have debuging functions used for testing purpose.
-- Those functions are not available ingame.  
screen1._setMass(500)
screen1._setMaxHitPoints(3500)
unit._setMass(500)
screen1._setHitPoints(3500)
screen1._setIntegrity(85)

screen1.addContent(0,0,
    'unit mass: '..unit.getMass()
  ..'<br>screen1 max hit points: '..screen1.getMaxHitPoints()
  ..'<br>screen1 hitpoints: '..screen1.getHitPoints()
  ..'<br>screen1 Integrity: '..screen1.getIntegrity())

