-- Wireless modem relay, does NOT work for wired modems!
local modem = peripheral.find("modem", function(name, modem)
    return modem.isWireless() -- Check this modem is wireless.
end)
print("Wireless modem relay software")
if not modem then
	error("No wireless modem present!",0)
end
modem.closeAll()
local relayedIds = {}
local function cleanup()
	while true do
		if #relayedIds > 16 then
			table.remove(relayedIds,1)
		end
		sleep()
	end
end
local function checkMsg(msg)
	return type(msg) == "table" and msg.id and msg.channel and msg.reply and msg.msg
end
local function isNewMsg(msg)
	for i,v in pairs(relayedIds) do
		if v == msg.id then
			return false
		end
	end
	return true
end
local function relay()
	while true do
		local _, _, channel, _, msg, _ = os.pullEvent("modem_message")
		if msg and checkMsg(msg) and isNewMsg(msg) then
			table.insert(relayedIds,msg.id)
			print(msg.channel,msg.reply,msg.msg)
			modem.transmit(msg.channel,msg.reply,msg.msg)
			modem.transmit(0,0,msg)
		end
	end
end
modem.open(0)
parallel.waitForAny(cleanup,relay)
