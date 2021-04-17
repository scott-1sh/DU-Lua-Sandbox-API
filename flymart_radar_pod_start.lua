
buttonManager = {toggle=0, timerClick=1, x={}, y={}, width={}, height={}, label={}, toggled={}, clicked={}, cssbutton, cssActive}                                               

function buttonManager:new(o)
  o = o or {}
  setmetatable(o, self)
  self.__index = self

  o.timerClick = 1
  o.x={}
  o.y={}
  o.width={}
  o.height={}
  o.label={}
  o.toggled={}
  o.clicked={}
  
  return o
end

function buttonManager:AddButton(id, x, y, width, height, label)                                                                                                                       
  self.x[id] = x                                                                                                                                                                       
  self.y[id] = y                                                                                                                                                                       
  self.label[id] = label                                                                                                                                                               
  self.width[id] = width                                                                                                                                                               
  self.height[id] = height                                                                                                                                                             
  self.clicked[id] = 0                                                                                                                                                                 
end                                                                                                                                                                                    

function buttonManager:IsHover(id, mouseX, mouseY)                                                                                                                                     
  if mouseX >= self.x[id] and mouseX <= self.x[id]+self.width[id]                                                                                                                      
    and mouseY >= self.y[id] and mouseY <= self.y[id]+self.height[id] then                                                                                                             
    return 1                                                                                                                                                                           
  end                                                                                                                                                                                  
  return 0                                                                                                                                                                             
end                                                                                                                                                                                    

function buttonManager:ResetAll()                                                                                                                                                      
  for k in pairs(self.x) do                                                                                                                                                            
    self.clicked[k] = 0                                                                                                                                                                
  end                                                                                                                                                                                  
end                                                                                                                                                                                    

function buttonManager:OnClick(id)
  if system.getTime()-self.timerClick > 1 then                                                                                                                                                         
    if self.clicked[id] == 1 then
        self.timerClick = system.getTime()  
        self.clicked[id] = 0 -- consume the onclick action
       return 1                                                                                                                                                                          
    end                                           
  end   
end                                                                                                                                                                                    

function buttonManager:IsToggled(id)                                                                                                                                                   
  if self.clicked[id] == 1 then                                                                                                                                                        
     return 1                                                                                                                                                                          
  end                                                                                                                                                                                  
end                                                                                                                                                                                    

function buttonManager:Display(mouseX, mouseY, mouseState)                                                                                                                             
  local btnHtml = '' 
  for k in pairs(self.x) do
    local btncss = self.cssbutton                                                                                                                                                      
    if self.toggle == 1 then                                                                                                                                                           
      -- toggle mode                                                                                                                                                                   
      if self:IsHover(k, mouseX, mouseY) == 1  then                                                                                                                                    
         if mouseState == 1 then                                                                                                                                                       		
           self:ResetAll()                                                                                                                                                             
           self.clicked[k] = 1                                                                                                                                                         
           btncss = self.cssActive 
         else		   
			if self.clicked[k] == 1 then                                                                                                                                                   
			  btncss = self.cssActive                                                                                                                                                      
			else                                                                                                                                                                           
			  ibtncss = self.cssbutton                                                                                                                                                      
			end                                                                                                                                                                                       
		 end
      else                                                                                                                                                                             
        if self.clicked[k] == 1 then                                                                                                                                                   
          btncss = self.cssActive                                                                                                                                                      
        else                                                                                                                                                                           
          ibtncss = self.cssbutton                                                                                                                                                      
        end                                                                                                                                                                            
      end                                                                                                                                                                              
  else                                                                                                                                                                                 
    if self:IsHover(k, mouseX, mouseY) == 1 then                                                                                                                                       
      if mouseState == 1 then                                                                                                                                                          
        self.clicked[k] = 1   
        btncss = self.cssActive                                                                                                                                                        
      end                                                                                                                                                                              
    end                                                                                                                                                                                
  end                                                                                                                                                                                  

    btnHtml = btnHtml..'<div class="'..btncss..'" style="position: absolute; left: '..self.x[k]..'px; top: '..self.y[k]..'px; height: '..self.height[k]..'; width: '..self.width[k]..'px;">'..self.label[k]..'</div>'
  end                                                                                                                                                                                  

  return btnHtml                                                                                                                                                                       
end                                                                                                                                                                                    

htmlTable = [[
<TABLE class="table">
<thead>
  <tr>
   <th colspan="2">%s</th>
  </tr>  
</thead>
<tbody>
<tr>
  <td> 
  %s
  </td>
  <td>
  %s
  </td>
</tr>
</tbody>
</TABLE>
]]

bg1 = 'background: radial-gradient(circle, rgba(2,0,36,1) 0%%, rgba(20,20,235,1) 0%%, rgba(3,23,46,1) 100%%, rgba(36,6,172,1) 100%%);'
bg2 = 'background: linear-gradient(to left, rgba(2,0,36,1) 0%%, rgba(20,20,235,1) 0%%, rgba(3,23,46,1) 100%%, rgba(36,6,172,1) 100%%););'
-- background: linear-gradient(45deg, #00f 12.5%, #fff 12.5%, #fff 37.5%, #00f 37.5%, #00f 62.5%, #fff 62.5%, #fff 87.5%, #00f 87.5%);
-- background-size: 100px 100px;
--   background-position: 50px 50px;}


htmlContent = [[
<!DOCTYPE html>
<html>
<head>
<style>
   th { height: 4.2vw; background-color: rgba(5, 5, 5, .9); padding: 0; margin: 0;}
   table { white-space: nowrap; }
   td { max-width: 44.6vw;
        min-width: 44.6vw;
        background-color: rgba(0, 5, 105, 0.6);
        padding: 1vw; 
		vertical-align: top; 
		font: bold 5vh Verdana;
		border: .3vw solid #000000;
		overflow: hidden;}

   body { background-color: %s;
		 background-color: #0000f6;
		 background: radial-gradient(ellipse at center, #4c4c4c 0%%,#595959 12%%,#666666 25%%,#474747 39%%,#2c2c2c 50%%,#000000 51%%,#111111 60%%,#2b2b2b 76%%,#1c1c1c 91%%,#131313 100%%);
		  color: #FFFFFF; } 
  .tsize { color: #FFFF00;
           font: bold 4vh Verdana; }
  .rank { color: #888888;
          padding-right: .5vw;
          font: normal 3.5vh Verdana;}
  .id { color: #8080FF; font: normal 3vh Verdana;}
  .construct { font: normal 5vh Verdana; 
               padding: .2vw;
			   vertical-align: middle;
			   max-width: 44.6vw;
			   overflow: hidden;}
   .paging {  position: absolute;
            border: .2vw inset #000000;
            background-color: rgba(0, 5, 75, 0.8);			
			color: #FFFFFF;
            top: 89vh;
			margin-left: 70vw;
			width: 18vw;
            color: #7070FF;
            font: bold 6vh Verdana;
			padding: .2vw;
			text-align: center;
			}
  .arrowb {  border-radius:3vh 3vh 3vh 3vh;                                                                                                                                                
            background: #4040A5;                                                                                                                                                       
            color:#00A033;                                                                                                                                                             
            
			font: bold 6vh Verdana;
			height: 8vh;
            padding:.2vw 0 .2vw 0;                                                                                                                                                     
            border: .6vw solid #000044;                                                                                                                                                
            vertical-align: middle!important; 
			text-align: center;                                                                                                                                                        
            }                                                                                                                                                                          
  .statsActif {  border-radius:3vh 6vh 3vh 6vh;                                                                                                                                                
            background: #000099;                                                                                                                                                       
            font: normal 6.2vh Verdana;
            padding:.2vw 0 .2vw 0;                                                                                                                                                     
            border: .6vw solid #000044;                                                                                                                                                
            text-align: center;                                                                                                                                                        
            }                                                                                                                                                                          
  .stats {  border-radius:3vh 6vh 3vh 6vh;                                                                                                                                                
            background: #000055;                                                                                                                                                       
            color:#AAAAFF;                                                                                                                                                             
            font: normal 6.5vh Verdana;
            padding:.2vw 0 .2vw 0;                                                                                                                                                     
            border: .6vw solid #000044;                                                                                                                                                
            text-align: center;                                                                                                                                                        
            }                                                                                                                                                                          
  .statsActif {  border-radius:3vh 6vh 3vh 6vh;                                                                                                                                                
            background: #000099;                                                                                                                                                       
            border:none;                                                                                                                                                               
            color:#FFFFFF;                                                                                                                                                             
            font: normal 6.5vh Verdana;
            font-weight: 900;
            padding:.2vw 0 .2vw 0;                                                                                                                                                     
            border: .6vw solid #000044;                                                                                                                                                
            text-align: center;                                                                                                                                                        
            }                                                                                                                                                                          
   .window {position: absolute;
            border: .5vw solid black; 
            background-color: rgba(0, 5, 185, 0.9);
			font: normal 6vh Verdana;
			color: rgba(255, 255, 255, 0.95);
            border-radius:3vh 6vh 3vh 6vh;      
            top: 2vw;
			left: 2.5vh;
			widht: 95vw;
			min-width: 95vw;
			height: 80.6vh;}
	.windowtitle { text-align: center; 
	             color: #EEEEEE; 
				 margin: 0vw;
	             background-color: rgba(50, 52, 85, 1);
	               border-bottom: .3vw solid black;  }
	.table {
	        font: normal 4vh Verdana;
			color: rgba(255, 255, 255, 0.99);
			widht: 99vw;
			min-width: 99vw;
			height: 87vh;}
</style>
</head>
<body>
%s
</body>
</html>
]]

function tablelength(T)
  local count = 0
  for _ in pairs(T) do count = count + 1 end
  return count
end


transColor = '#00B000'
staticColor = '#BB0000'
dynColor = '#0000FF'

_screenDivision = 2;

function resizeX(x) 
  return x/_screenDivision
end 
function resizeY(y) 
  return y/_screenDivision
end 


-- tabs use toggle buttons (toggle=1)                                                                                                                        
bmStat = buttonManager:new{toggle=1, cssbutton='stats', cssActive='statsActif'}                                                                                   
bmStat:AddButton('stats_tab', resizeX(1), resizeY(542), resizeX(180), resizeY(66), 'Stats')                                                                                                                                     
bmStat:AddButton('setup_tab', resizeX(200), resizeY(542), resizeX(180), resizeY(66), 'Setup')                                                                                                                                     
bmStat:AddButton('detail_tab', resizeX(400), resizeY(542), resizeX(180), resizeY(66), 'Detail')                                                                                                                                     

bmDynPage = buttonManager:new{cssbutton='arrowb', cssActive='statsActif'}                                                                                   
bmDynPage:AddButton('prev', resizeX(606), resizeY(540), resizeX(100), resizeY(66), '<')                                                                                                                                     
bmDynPage:AddButton('next', resizeX(906), resizeY(540), resizeX(100), resizeY(66), '>')     
                                                                
                                                                                                                                

-- Some configs used by timer tick
csize = {}
csize[16] = 'XS'
csize[32] = 'XS'
csize[64] = 'S'
csize[128] = 'M'
csize[256] = 'L'
colsize = {}
colsize[1] = 13
colsize[2] = 13
colsize[3] = 11
colsize[4] = 11
perPage = colsize[1]+colsize[1]+colsize[3]+colsize[4] 
dynPage = 1


screen1.activate()
screen1.addContent(0,0, string.format(htmlContent, dynColor, '', ''))
screen2.activate()
screen2.addContent(0,0, string.format(htmlContent, dynColor, '', ''))
--[[
screen3.activate()
screen3.addContent(0,0, string.format(htmlContent, transColor, ''))
screen4.activate()
screen4.addContent(0,0, string.format(htmlContent, transColor, ''))
screen5.activate()
screen5.addContent(0,0, string.format(htmlContent, staticColor, ''))
screen6.activate()
screen6.addContent(0,0, string.format(htmlContent, staticColor, ''))
]]

-- setup a timer/tick event
self.setTimer('loop', 0.15)
