local key = KEYS[1]
local value = ARGV[1]
if redis.call('EXISTS', key) == 0 then
  redis.call('BF.ADD', key, '')
  redis.call('EXPIRE', key, 20 * 60 * 60)
end
return redis.call('BF.EXISTS', key, value)
