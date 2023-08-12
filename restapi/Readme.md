# Getting Started

##### Rest api details
Start with first Root api
```
http://localhost:8080/api
```

The response sample has
```
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/api"
    },
    "vehicleBrands": {
      "href": "http://localhost:8080/api/vehicle-brands"
    },
    "vehicles": {
      "href": "http://localhost:8080/api/vehicles"
    },
    "customers": {
      "href": "http://localhost:8080/api/customers"
    },
    "leasingContracts": {
      "href": "http://localhost:8080/api/leasing-contracts"
    }
  }
}
```
From this we can navigate to all sub uris, So that React application doesn't need to create uris at client level, but use the response object to navigate to every deep level resources. In other words it is using HATEOS.

###### 1.3.2 Customer Details
Choose _links.customers.href from Root Api result and make POST call

```
POST:http://localhost:8080/api/customers
{
  "firstName": "Test",
  "lastName": "Test",
  "birthDate": "2000-02-23"
}
```
To make an update call
First retrieve all users by _links.customers.href from Root Api
That will give all customers
```
GET:http://localhost:8080/api/customers
result:
  "_embedded": {
    "customerResourceList": [
      {
        "id": 1,
        "firstName": "Aksa",
        "lastName": "Peter",
        "birthDate": "2023-08-11",
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/customers/1"
          },
          "update": {
            "href": "http://localhost:8080/api/customers/1"
          }
        }
      },
    ]
  }
}
```
Select the customer you use, and use _embedded.customerResourceList._links.update.href
and make a PUT call
```
PUT:http://localhost:8080/api/customers/1
{
  "firstName": "Check",
  "lastName": "Teswt",
  "birthDate": "2000-03-23"
}

```


###### Update Create Vehicle (1.3.3)
Use Root Api in React Js, and use the objects from the response.

```
Root ->vehicleBrands -> (response) -> models

In other words:
1. Hit GET:http://localhost:8080/api/vehicle-brands
2. From the Brand, hit GET:http://localhost:8080/api/vehicle-brands/1/models
3. Use the 'id' from step 2 response and use it as vehicleModelId
4. Use POST: http://localhost:8080/api/vehicles
 
    {
        "vehicleModelId": 1,
        "year": 2023,
        "vin": "X1",
        "price": 500.0
    }

To create Vehicle

To update Vehicle; use
PUT:http://localhost:8080/api/vehicles/2
    {
        "vehicleModelId": 1,
        "year": 2024,
        "vin": "X1",
        "price": 500.0
    }

```

###### Leasing Contract (1.3.4)
Use RootApi and choose leasingContracts to create a post call
```
eg. 
POST:http://localhost:8080/api/leasing-contracts
{
  "monthlyRate" : 400.55,
  "customerId": 2,
  "vehicleId": 2
}
```

To update an item, Use Root api and choose leasingContracts to create a get call
```
eg.
GET:http://localhost:8080/api/leasing-contracts
{
  "_embedded": {
    "leasingContractResourceList": [
      {
        "contractNumber": 1,
        "monthlyRate": 455.46,
        "customerId": 1,
        "vehicleId": 2,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/leasing-contracts"
          },
          "update": {
            "href": "http://localhost:8080/api/leasing-contracts/1"
          }
        }
      },
    ]
  }
}
```
choose `update` url from response to create a PUT call
```
eg.
PUT:http://localhost:8080/api/leasing-contracts/1
{
  "monthlyRate" : 45.46,
  "customerId": 1,
  "vehicleId": 2
}
```
