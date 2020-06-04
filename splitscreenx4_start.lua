
screen1.activate()
screen2.activate()
screen2.clear()
-- 306, 153
picture = '<svg width="306" height="153" viewBox="0 0 612 306">'..logo..'</svg>' 
--[[
img = "<img src='data:image/svg+xml;utf8,%s' width=\"306\" height=\"153\">"
viewbox2 = '<svg width="306" height="153" viewBox="0 0 153 76">'
-- viewbox3 = '<svg width="200vw" height="200vh" viewBox="0 50vw 100vw 100vh">'
viewbox4 = '<svg  width="306" height="153" viewBox="0 0 100% 100%">'
]]
-- <object type="image/svg+xml" data="kiwi.svg" class="logo">
--  Kiwi Logo <!-- fallback image in CSS -->
-- </object>
-- html
svgBackground = [[ 
  <rect width="100vw" height="100vh" style="fill:rgb(0,0,255);stroke-width:10;stroke:rgb(255,255,0)" />        
  <text x="15vw" y="35vh" fill="white" font-family="Arial" font-size="15">%s</text>
  ]]..logo 


-- viewbox1 = '<svg width="100vw" height="100vh" viewBox="0 0 100vw 100vh">'
div = '<div style="width:100px;height:100px;display:inline-block;border:solid 1px green;">'
viewbox2 = '<svg width="306" height="153" viewBox="0 0 306 153"><g width="306" height="153">'


screen1.addContent(0,0, div..logo..'</div>')
screen2.addContent(0,0,viewbox2..logo..'</g></svg>')
-- screen3.addContent(0,0, string.format(img, logo))
-- screen4.addContent(0,0, viewbox4..string.format(svgBackground, 'Screen4')..'</svg>')

-- background = screen1.addContent(0,0, '<div style="font-family: Arial; color: #FFFFFF; font-size:15; padding-top: 7vw; padding-left: 7vw;">screen1</div>')
-- background = screen2.addContent(0,0, 'screen2')
-- background = screen3.addContent(0,0, 'screen3')
-- background = screen4.addContent(0,0, 'screen4')