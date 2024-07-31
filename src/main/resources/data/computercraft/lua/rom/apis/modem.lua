-- Modem helper for Lumina CC
-- This helps with the fact you need to manually send messages to relays
local expect = dofile("rom/modules/main/cc/expect.lua").expect

local function getModem()
	local modems = {peripheral.find("modem") or error("No modems present!",0)}
	return modems[1]
end

function getWirelessModems()
    return peripheral.find("modem", function(name, router) return router.isWireless() end)
end

function getWiredModems()
    return peripheral.find("modem", function(name, router) return not router.isWireless() end)
end

function send(modem,channel,reply,msg)
	if msg == nil and modem and channel and reply then
		return send(getModem(),modem,channel,reply)
	end
	expect(2,channel,"number")
	expect(3,reply,"number")
	if type(modem) == "string" then
		modem = peripheral.wrap(modem)
	end
	if type(modem) ~= "table" then
		error("A valid wrapped peripheral or name is required!",0)
	end
	local relayMsg = {
		channel = channel,
		reply = reply,
		msg = msg
	}
	relayMsg.id = tostring(relayMsg)
	modem.transmit(channel,reply,msg)
	modem.transmit(0,0,relayMsg)
end
transmit = send
function open(modem,channel)
	if channel == nil and modem then
		return open(getModem(),modem)
	end
	if type(modem) == "string" then
		modem = peripheral.wrap(modem)
	end
	if type(modem) ~= "table" then
		error("A valid wrapped peripheral or name is required!",0)
	end
	expect(2,channel,"number")
	modem.open(channel)
end

function isOpen(modem,channel)
	if channel == nil and modem then
		return isOpen(getModem(),modem)
	end
	if type(modem) == "string" then
		modem = peripheral.wrap(modem)
	end
	if type(modem) ~= "table" then
		error("A valid wrapped peripheral or name is required!",0)
	end
	expect(2,channel,"number")
	return modem.isOpen(channel)
end

function close(modem,channel)
	if channel == nil and modem then
		return close(getModem(),modem)
	end
	if type(modem) == "string" then
		modem = peripheral.wrap(modem)
	end
	if type(modem) ~= "table" then
		error("A valid wrapped peripheral or name is required!",0)
	end
	expect(2,channel,"number")
	modem.close(channel)
end

function closeAll(modem)
	if modem == nil then
		return closeAll(getModem())
	end
	if type(modem) == "string" then
		modem = peripheral.wrap(modem)
	end
	if type(modem) ~= "table" then
		error("A valid wrapped peripheral or name is required!",0)
	end
	modem.closeAll()
end

function isWireless(modem)
	if modem == nil then
		return isWireless(getModem())
	end
	if type(modem) == "string" then
		modem = peripheral.wrap(modem)
	end
	if type(modem) ~= "table" then
		error("A valid wrapped peripheral or name is required!",0)
	end
	return modem.isWireless()
end