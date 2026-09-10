local key = KEYS[1]
local args = {}
for i = 1, #ARGV - 1, 2 do table.insert(args, ARGV[i]); table.insert(args, ARGV[i + 1]) end
if #args > 0 then redis.call('ZADD', key, unpack(args)) end
redis.call('EXPIRE', key, ARGV[#ARGV])
return 0
