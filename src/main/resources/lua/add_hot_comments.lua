local key = KEYS[1]
if redis.call('EXISTS', key) == 0 then return -1 end
local size = redis.call('ZCARD', key)
for i = 1, #ARGV, 2 do
  if size >= 500 then break end
  redis.call('ZADD', key, ARGV[i + 1], ARGV[i])
  size = size + 1
end
return 0
