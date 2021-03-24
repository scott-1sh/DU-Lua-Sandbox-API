

system.showScreen(1)

htmlBox = [[
<style>
   .box { position: absolute;   
         top: 40px; left: 30px; 
		 width: 400px; 
		 color: #FFFFFF;
         background-color:rgba(10, 10, 180, 0.6);
		 border:2px ridge #555555;} 
 
</style>
<div class="box">
<center>
<b>Info</b>
<br/><br/>
Estimated earth time: %s<br/><br/>
Construct mass: %s<br/>
gravity: %sg<br/>
Altitude: %sm<br/> 
<br/><br/>
</center>
<div style="margin-left: 15px;">timer</div> 
<svg width="400" height="80" >
<rect x="10" y="0" width="380" height="60" style="fill:rgb(5,5,5);" />
<rect x="12" y="2" width="375" height="56" style="fill:rgb(0,0,80);" />
<rect x="12" y="2" width="%i" height="56" style="fill:rgb(10,55,230);" />
</svg>

</div>
]]

-- "TRY" to calculate earth time
function earthDate(timestamp)

  local seconds, minutes, hours, days, year, month
  local dayOfWeek
  local tm = {}

    -- calculate minutes
  minutes = math.floor(timestamp / 60)
  seconds = timestamp - (minutes*60)
  -- calculate hours
  hours = math.floor(minutes / 60)
  minutes = minutes - (hours * 60)
  -- calculate days
  days = math.floor(hours / 24)
  hours = hours - (days * 24)

  year = 12390
  dayOfWeek = 1

  while 1==1 do

    local leapYear = (year % 4 == 0 and (year%100 ~= 0 and year%400 == 0))
    local daysInYear

    if leapYear then daysInYear = 366 else daysInYear = 365 end

    if days >= daysInYear then
      if leapYear then
        dayOfWeek = dayOfWeek + 2
      else
        dayOfWeek = dayOfWeek + 1
      end

      days = days - daysInYear
      if dayOfWeek >= 7 then
        dayOfWeek = dayOfWeek - 7
      end
      year = year + 1

    else

      dayOfWeek = dayOfWeek + days;
      dayOfWeek = dayOfWeek % 7;

      -- calculate the month and day
      daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
      for imonth = 1,12,1 do
        local dim = daysInMonth[imonth];
        -- add a day to feburary if this is a leap year
        if imonth == 2 and leapYear then dim = dim + 1 end
        if days >= dim then
          days = days - dim
        else
          month = imonth
          break
        end
        month = imonth
      end -- end for
      break
    end -- else
  end -- while

  tm = {
   sec = math.floor(seconds),
   min = minutes,
   hour = hours,
   day = days + 1,
   month = month,
   year = year,
   wday = dayOfWeek
  }

  return tm

end

