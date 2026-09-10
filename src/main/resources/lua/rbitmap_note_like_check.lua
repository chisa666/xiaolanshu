local key = KEYS[1]
local id = ARGV[1]
if redis.call('EXISTS', key) == 0 then return -1 end
if redis.call('R.GETBIT', key, id) == 1 then return 1 end
redis.call('R.SETBIT', key, id, 1)
return 0
