local key = KEYS[1]
redis.call('R.SETBIT', key, ARGV[1], 1)
redis.call('EXPIRE', key, ARGV[2])
return 0
