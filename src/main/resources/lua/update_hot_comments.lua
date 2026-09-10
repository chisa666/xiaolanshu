local key = KEYS[1]
if redis.call('EXISTS', key) == 0 then return -1 end
for i = 1, #ARGV, 2 do
  local member = ARGV[i]
  local score = tonumber(ARGV[i + 1])
  if redis.call('ZCARD', key) < 500 then
    redis.call('ZADD', key, score, member)
  else
    local min = redis.call('ZRANGE', key, 0, 0, 'WITHSCORES')
    if #min > 1 and score > tonumber(min[2]) then
      redis.call('ZREM', key, min[1])
      redis.call('ZADD', key, score, member)
    end
  end
end
return 0
