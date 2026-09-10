local key = KEYS[1]
if redis.call('EXISTS', key) == 0 then return -1 end
return redis.call('R.GETBIT', key, ARGV[1])
