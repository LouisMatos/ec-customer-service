db.createUser({
  user: 'customer_service_user',
  pwd: 'customer_service_pass',
  roles: [
    {
      role: 'readWrite',
      db: 'customer-service'
    }
  ]
});

db = db.getSiblingDB('customer-service');

db.createCollection('customers'); 