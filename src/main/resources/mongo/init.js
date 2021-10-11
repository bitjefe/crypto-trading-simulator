db.createCollection('app_cache');

db.getCollection('app_cache').insertMany([{cacheKey: 'foo'}]);
