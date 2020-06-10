
svgBalls = ''

-- Moving balls
for i,v in ipairs(x) do 
  -- bounce?
	if x[i]+dirx[i]+ballRayon > screenX or x[i]+dirx[i]-ballRayon < 1 then
    	dirx[i] = -dirx[i]
	end  
    if y[i]+diry[i]+ballRayon > screenY or y[i]+diry[i]-ballRayon < 1 then 
  		diry[i] = -diry[i] 
  	end 
	x[i] = x[i] + (dirx[i]*speed)
	y[i] = y[i] + (diry[i]*speed)
	
	svgBalls = svgBalls..htmlBall(x[i], y[i], ballRayon)
end


ss4:Clear()

ss4:Display(picture..svgBalls)

-- identify screen
--[[ text = '<svg x="0" y="0" width="200" height="100"><text x="15" y="23" fill="black" font-family="arial" font-size="16">%s</text></svg>'
screen1.addContent(0,0, string.format(text,'screen1'))
screen2.addContent(0,0, string.format(text,'screen2'))
screen3.addContent(0,0, string.format(text,'screen3'))
screen4.addContent(0,0, string.format(text,'screen4'))
]]

