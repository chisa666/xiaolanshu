local key = KEYS[1]
if redis.call('EXISTS', key) == 0 then return -1 end
if not redis.call('ZSCORE', key, ARGV[1]) then return -4 end
redis.call('ZREM', key, ARGV[1])
return 0
