local key = KEYS[1]
if redis.call('EXISTS', key) == 0 then return -1 end
if redis.call('ZCARD', key) >= 1000 then return -2 end
if redis.call('ZSCORE', key, ARGV[1]) then return -3 end
redis.call('ZADD', key, ARGV[2], ARGV[1])
return 0
