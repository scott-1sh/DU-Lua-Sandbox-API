
screenX = 306
screenY = 153
scale = 2
ballRayon = 5
speed = 1.2
x = {17, 30, 100, 111, 70}  
y = {17, 30, 100, 111, 70}
dirx = {5, -4, 1, -2, 1}  
diry = {5, 3, -3, 2, 3}

SplitScreen4 = {screen1, screen2, screen3, screen4, screenX = 306, screenY = 153, scale = 2}
function SplitScreen4:new(o)
  o = o or {}  
  setmetatable(o, self)
  self.__index = self
  
  o.screen1 = screen1 
  o.screen2 = screen2 
  o.screen3 = screen3 
  o.screen4 = screen4 
-- img = "<img src='data:image/svg+xml;utf8,%s' width=\"306\" height=\"153\">"
-- viewbox1 = [[<svg width="306" height="153" viewBox="0 0 306 153"><g transform="scale(2) translate(0 -100)"> 
  o.viewbox1 = '<svg width="'..screenX..'" height="'..screenY..'" viewBox="0 0 '..screenX..' '..screenY..'"><g transform="scale('..scale..')">' 
  o.viewbox2 = '<svg width="'..screenX..'" height="'..screenY..'" viewBox="'..screenX..' 0 '..screenX..' '..screenY..'"><g transform="scale('..scale..')">'
  o.viewbox3 = '<svg width="'..screenX..'" height="'..screenY..'" viewBox="0 '..screenY..' '..screenX..' '..screenY..'"><g transform="scale('..scale..')">'
  o.viewbox4 = '<svg width="'..screenX..'" height="'..screenY..'" viewBox="'..screenX..' '..screenY..' '..screenX..' '..screenY..'"><g transform="scale('..scale..')">'
     
  return o
end

function SplitScreen4:Clear()
  self.screen1.clear()
  self.screen2.clear()
  self.screen3.clear()
  self.screen4.clear()
end

function SplitScreen4:Display(svg)
  self.screen1.addContent(0,0, self.viewbox1..svg..'</g></svg>')
  self.screen2.addContent(0,0, self.viewbox2..svg..'</g></svg>')
  self.screen3.addContent(0,0, self.viewbox3..svg..'</g></svg>')
  self.screen4.addContent(0,0, self.viewbox4..svg..'</g></svg>')   
end
--
ss4 = SplitScreen4:new{screen1, screen2, screen3, screen4, screenX = 306, screenY = 153, scale = 2}

-- some svg stuff
svgBackground ='<rect width="100vw" height="100vh" style="fill:rgb(0,0,255);stroke-width:10;stroke:rgb(255,255,0)" />'
picture = svgBackground..logo

function htmlBall(x,y,ballRayon)
  local str = [[
<svg width="1025" height="606">
<defs> 
   <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="0%"> 
   <stop offset="0%" style="stop-color:rgb(200,200,255);stop-opacity:1" />  
   <stop offset="100%" style="stop-color:rgb(0,15,15);stop-opacity:1" />  
   </linearGradient>  
</defs>  
<circle cx="]]..x..[[" cy="]]..y..[[" r="]]..ballRayon..[[" stroke="black" stroke-width=".5" fill="url(#grad1)" />  
</svg>]]

 return str  
end

ss4:Display(picture)


-- timer
self.setTimer('loop', .23)
