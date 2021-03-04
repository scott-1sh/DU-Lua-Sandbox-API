
item = {} 
-- rack 1
promo = '<span style="font-size: 6.5vw; color: #FF3300;text-shadow: 2px 0 0 #000, -2px 0 0 #000, 0 2px 0 #000, 0 -2px 0 #000, 1px 1px #000, -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000;">New price!</span>'
item[1] = {name = 'Vertical light', size = 'S', price = 8500, unit = "per unit", mass =  79.34, quantity = 0}
item[2] = {name = 'Vertical light', size = 'M', price = 16000, unit = "per unit", mass = 79.34, quantity = 0}
item[3] = {name = 'Vertical light', size = 'L', price = 30000, unit = "per unit", mass = 371.8, quantity = 0}

local outofstockLibel = '<span style="color: #FF0000;">Out of stock</span>'

-- debug
-- box1._setItemsMass(2544.26)
-- box2._setItemsMass(0)
-- box3._setItemsMass(553.1)

-- Update quantities
item[1]['quantity'] = math.floor(box1.getItemsMass()/item[1]['mass']) or 0
item[2]['quantity'] = math.floor(box2.getItemsMass()/item[2]['mass']) or 0
item[3]['quantity'] = math.floor(box3.getItemsMass()/item[3]['mass']) or 0

function infoText(unitText, quantity) 
  if quantity == nil or quantity == 0 then
     return outofstockLibel
  else
     return unitText
  end
end

function priceFormat(amount) 
  local formatted = amount
  while true do  
    formatted, k = string.gsub(formatted, "^(-?%d+)(%d%d%d)", '%1 %2')
    if (k==0) then
      break
    end
  end
  return formatted 
end

svgBackground = [[
<svg width="100vw" height="100vh">
  <rect width="100vw" height="100vh" style="fill:rgb(0,0,55)	;stroke-width:10;stroke:rgb(0,0,255)" />
</svg>
]]

htmlTemplate1 = [[
<!DOCTYPE html>
<html>
<head>
<style>
  .name {   text-align: center;
            top: 0vw; 
			font-family: Verdana;
            color: yellow;			 
			font-size: 8vw; }
  .size {   
			font-family: Verdana;
            color: white;			 
			font-size: 8vw; }
  .price {  white-space: nowrap;
			font-family: Verdana;
            color: white;			 
			font-size: 8vw;
			text-align: center; }	           
  .unit { 	width: 98%%;
            margin-right: 3vh;
			font-family: Verdana;
            color: #FFFF33;			 
			font-size: 5.7vw;
			text-align: right;	}
  .prcbox { padding-top: 3vh;
            padding-bottom: 3vh;
            width: 97%%;
 	        border: 2px solid #AAAA00;
			background-color: green;  }
  .content { margin-left: -19.5vh;
	         width: 94vh; max-width: 94vh; 
		     height: 70vw; 
			 overflow: hidden; }
  .rotate {  transform: rotate(90deg);margin-left: 34vh;
             transform-origin: center;  }		  
   </style>
</head>
<body>
<div class="rotate" >
  <center>
   <div class="content">
   <span class="name"><i>%s</i></span><br/><br/><br/><br/>
   <span class="size" ><b><i>%s</i></b></span><br/><br/><br/>
   <div class="prcbox">
     <span class="price"><b>%s</b></span><br/>
     <div class="unit">%s</div>
   </div>
  </center>
  </div>
</div>   
  
</body>
</html>
]]

htmlMonitor = [[
<html>
<head>
 <style>
   body { background-color: #000044; }
   th { text-align: left; font-size: 3vw; border: 1px solid blue; background-color: #000000;}
   table, td { font-family: Verdana;  color: #FFFFFF; font-size: 3vw; border: .8vw ridge #0000FF; #000088; text-align: left;}
   .mname { margin-left: 1.5vw; color: #FFFF00; font-size: 7.5vw;}
   .msize { margin-left: 1.7vw; color: #FFFFFF; font-size: 5.7vw;}
   .mprice { color: #00FF00; font-size: 5vw;}
   .munit { margin-left: 1.5vw; color: #A0A0FF; font-size: 5vw;}
   .mqte { font-family: arial; text-align: center; background-color: #000055; color: #FFFFFF; font-size: 7.7vw; border: 1vw ridge #0000EE;}
</style>
</head>
<body>
<TABLE style="width: 100vw;">
<thead>
  <tr>
    <th>Name</th><th>qte</th>
  </tr>
</thead>
<tbody>
 %s
</tbody>
</TABLE>
</body>
</html>
]]


-- <div class="content"><div class="rotatecontent">%s</div></div>
screen1.activate()
screen2.activate()
screen3.activate()
monitor.activate()

screen1.clear()

screen1.addContent(0,0, svgBackground)
screen1.addContent(0,0, string.format(htmlTemplate1, item[1]['name'], 
                                                     item[1]['size'], 
													 priceFormat(item[1]['price']), 
													 infoText(item[1]['unit'], item[1]['quantity'])))

screen2.clear()
screen2.addContent(0,0, svgBackground)
screen2.addContent(0,0, string.format(htmlTemplate1, item[2]['name'], 
                                                     item[2]['size'], 
													 priceFormat(item[2]['price']), 
													 infoText(item[2]['unit'], item[2]['quantity'])))

screen3.clear()
screen3.addContent(0,0, svgBackground)
screen3.addContent(0,0, string.format(htmlTemplate1, item[3]['name'], 
                                                     item[3]['size'], 
													 priceFormat(item[3]['price']), 
													 infoText(item[3]['unit'], item[3]['quantity'])))

local cells = "<tr><td align=center><span class=\"mname\">"..item[1]['name'].."</span><span class=\"msize\"> <b>"..item[1]['size'].."</b></span><br/>"
cells = cells.."Price: <span class=\"mprice\">"..priceFormat(item[1]['price'])
cells = cells.." <span class=\"munit\">"..infoText(item[1]['unit'], item[1]['quantity']).."</span></td>"
cells = cells.."<td class=\"mqte\">"..item[1]['quantity'].."</td></tr>"

cells = cells.."<tr><td align=center><span class=\"mname\">"..item[2]['name'].."</span><span class=\"msize\"> <b>"..item[2]['size'].."</b></span><br/>"
cells = cells.."Price: <span class=\"mprice\">"..priceFormat(item[2]['price'])
cells = cells.." <span class=\"munit\">"..infoText(item[2]['unit'], item[2]['quantity']).."</span></td>"
cells = cells.."<td class=\"mqte\">"..item[2]['quantity'].."</td></tr>"

cells = cells.."<tr><td align=center><span class=\"mname\">"..item[3]['name'].."</span><span class=\"msize\"> <b>"..item[3]['size'].."</b></span><br/>"
cells = cells.."Price: <span class=\"mprice\">"..priceFormat(item[3]['price'])
cells = cells.." <span class=\"munit\">"..infoText(item[3]['unit'], item[3]['quantity']).."</span></td>"
cells = cells.."<td class=\"mqte\">"..item[3]['quantity'].."</td></tr>"
               
local text = string.format(htmlMonitor, cells)
monitor.clear()
-- monitor.addContent(0,0, svgBackground)
monitor.addContent(0,0, text)

-- unit.exit()

