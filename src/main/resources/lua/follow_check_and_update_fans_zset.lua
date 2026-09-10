local key = KEYS[1]
if redis.call('EXISTS', key) == 0 then return -1 end
if redis.call('ZCARD', key) >= 5000 then redis.call('ZPOPMIN', key) end
redis.call('ZADD', key, ARGV[2], ARGV[1])
return 0
