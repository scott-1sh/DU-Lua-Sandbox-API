

-- local htmlText = string.format(htmlContent,mouseX,mouseY)

-- Populate arrays
local cells = " "
local staticArray = {}
local dynArray = {}
local transArray = {}
local construct={}
for i, id in ipairs( radar1.getEntries() ) do

  construct[id] = {
    id = i,
    pos = radar1.getConstructPos(id),
    size = radar1.getConstructSize(id),
    speed = radar1.getConstructVelocity(id),
    name = radar1.getConstructName(id),
    ctype = radar1.getConstructType(id),
    trans = radar1.hasMatchingTransponder(id)
  }  

  if construct[id].ctype == 'dynamic' then
	 dynArray[id] = construct[id]  
  else
	 staticArray[id] = construct[id]
  end  

  if construct[id].trans == true then
     -- staticArray[id] = {}
	 transArray[id] = construct[id] 
  end  
 
end


-- Build tables
-- (4 columns)
local output2 = ""
local col = {}
local dynCount=tablelength(dynArray)

function BuildColumns(constructArray, page)
  local c = 1
  local columns = {}
  local cnt = 0 
  local pageCnt = 0
  columns[1] = ''
  for i in pairs(constructArray) do
	cnt = cnt + 1
	pageCnt = pageCnt + 1
	columns[c] = columns[c]..'<div class="construct"><span class="rank">'..cnt..'</span> '
	if constructArray[i].trans then 
	  columns[c] = columns[c]..'<span style="color: #00FF00;">'..constructArray[i].name..'</span>'
	else
	  columns[c] = columns[c]..constructArray[i].name
	end
	columns[c] = columns[c]..' <span class="tsize">'..csize[constructArray[i].size[1]]..'</span>'
    columns[c] = columns[c]..' <span class="id">('..constructArray[i].id..')</span></div>'
	if pageCnt == colsize[math.fmod(c-1,4)+1] then
	  pageCnt = 0
	  c = c + 1  
	  columns[c] = ' '
	end
  end

  return columns
end

col = BuildColumns(dynArray, dynPage)


-- build page label
local dynPageMax = math.floor(dynCount/perPage)+1
local PagesDyn = '<div class="paging">'..dynPage..' of '..dynPageMax..'</div>' 

-- prev button
-- local prevButton = '<div class="prev">'.. ..'</div>'
-- next button

-- local text = string.format(htmlTable, cells)
-- local htmlMouseInfo = screen2.getMouseX().."/"..screen2.getMouseY()..":"..screen2.getMouseState()

local stats = 'count: '..(tablelength(dynArray))..' total'..(tablelength(construct))..' total static: '..(tablelength(staticArray)) 
output2 = output2..bmStat:Display(screen2.getMouseX(), screen2.getMouseY(), screen2.getMouseState())
output2 = output2..bmDynPage:Display(screen2.getMouseX(), screen2.getMouseY(), screen2.getMouseState())
-- print(screen2.getMouseY()*_screenDivision.." state:"..screen2.getMouseState())

if bmDynPage:OnClick('prev') then 
  if dynPage > 1 then dynPage = dynPage - 1 end 
end 
if bmDynPage:OnClick('next') then 
  if dynPage < dynPageMax then dynPage = dynPage +1 end
end 

tmpColumn = ((dynPage-1)*4)+1

screen1.clear()
screen1.addContent(0, 0, string.format(htmlContent, dynColor, string.format(htmlTable, 'Dynamic constructs [1-26]', col[tmpColumn] or '', col[tmpColumn+1]or '')))
screen2.clear()
screen2.addContent(0, 0, string.format(htmlContent, dynColor,string.format(htmlTable, 'Dynamic constructs [27-48]', col[tmpColumn+2] or '', col[tmpColumn+3] or '')..output2..PagesDyn))

if bmStat:IsToggled('setup_tab') then 
  local htmlStats = '<div class="window">'
  htmlStats = htmlStats..'<div class="windowtitle">Setup</div>'
  htmlStats = htmlStats..'<span style="padding: 2vw;">...</span>'
  htmlStats = htmlStats..'</div>'  
  screen2.addContent(0, 0, htmlStats)
end

if bmStat:IsToggled('stats_tab') then 
  local htmlStats = '<div class="window">'
  htmlStats = htmlStats..'<div class="windowtitle">Statistics</div>'
  htmlStats = htmlStats..stats
  htmlStats = htmlStats..'</div>'  
  screen2.addContent(0, 0, htmlStats)
end

