local key = KEYS[1]
redis.call('BF.ADD', key, ARGV[1])
redis.call('EXPIRE', key, ARGV[2])
return 0
