## User Manual
Login
Default Users
1. Admin - Rem & Tahar
2. Picker - Iresh & Panduka
3. Packer - Ramith
4. Shipper - Piyumal

## Manual Order Process
### -Create Order
1. Login with Admin user.
2. Go to Store interface and create an order

### -Order Assign
1. Go to Order Assign and allocate the created order to a worker.
2. Go to Worker Progress and inspect the worker who has been allocated the order.

### Picker State Initialization
-Select assigned orders
1. Login with a Picker user who has been allocated the order.
2. Go to Ordres For Picker interface and select Assigned Orders.
3. Select Resume Order
4. Get The Route to see that the picker has done to get to the packing station.

    ### - Picker Action
        1. Go to picker action 
        2. Select Worker
        3. User can add the notification URI to worker JSON.
        eg: http://localhost:8081/workers/Iresh
        4. Go to Orders For Picker instance

5. Tick off the item lists
6. Confirm Order Complete

### Packer State Initialization
-Select assigned orders
1. Go to Orders For Packer
2. Select Available Orders
3. Select Take Order
4. Tick off the item lists
5. Confirm Order Complete

### Shipper State Initialization
-Select assigned orders
1. Go to Orders For Shipper
2. Select Available Orders
3. Select Take Order
4. Tick off the item lists
5. Confirm Order Complete

### Check Order Complete
1. Login with Admin User
2. Go to Daily Orders and check if order is complete
3. Go to worker progress and check the worker's progress of a worker.

## Automatic Order Create
### Start Clock
1. Login as Admin user
2. Click the play button to start the clock
3. Then orders will be automatically generated and assigned to users.

## Add/Delete Products and Orders
## Add/Delete Products
1. Go to Products interface
2. You can manually add/delete new Products
## Add/Delete Items
1. Got to Employees interface
2. You can manually add/delete new Employees