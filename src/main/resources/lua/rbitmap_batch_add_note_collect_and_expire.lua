local key = KEYS[1]
for i = 1, #ARGV - 1 do redis.call('R.SETBIT', key, ARGV[i], 1) end
redis.call('EXPIRE', key, ARGV[#ARGV])
return 0
