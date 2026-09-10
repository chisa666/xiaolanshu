local key = KEYS[1]
redis.call('ZADD', key, ARGV[2], ARGV[1])
redis.call('EXPIRE', key, ARGV[3])
return 0
