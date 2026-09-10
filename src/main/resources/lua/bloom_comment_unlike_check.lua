local key = KEYS[1]
local id = ARGV[1]
if redis.call('EXISTS', key) == 0 then return -1 end
return redis.call('BF.EXISTS', key, id)
