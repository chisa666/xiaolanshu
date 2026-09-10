local key = KEYS[1]
if redis.call('EXISTS', key) == 0 then return {-1} end
local result = {}
for i = 1, #ARGV do result[i] = redis.call('R.GETBIT', key, ARGV[i]) end
return result
