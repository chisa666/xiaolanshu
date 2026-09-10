local key = KEYS[1]
local id = ARGV[1]
if redis.call('EXISTS', key) == 0 then return -1 end
if redis.call('BF.EXISTS', key, id) == 1 then return 1 end
redis.call('BF.ADD', key, id)
return 0
