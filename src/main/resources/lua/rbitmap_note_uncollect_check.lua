local key = KEYS[1]
local id = ARGV[1]
if redis.call('EXISTS', key) == 0 then return -1 end
if redis.call('R.GETBIT', key, id) == 0 then return 0 end
return redis.call('R.SETBIT', key, id, 0)
