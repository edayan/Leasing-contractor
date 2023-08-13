# Getting Started

##### Rest api details
Start with first Root api
```
GET:http://localhost:8080/api
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

#### 1.3.2 Customer Details
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


#### Update Create Vehicle (1.3.3)

To create vehicle details, we need 
1. Brand
    - To retrieve brand we can hit the Root Api, from the response choose `vehicleBrands`
      - hit `vehicleBrands.href`
        ```
        GET:http://localhost:8080/api/vehicle-brands
        //response sample is
        {
          "_embedded": {
          "vehicleBrandResourceList": [
              {
              "brandName": "Toyota",
              "_links": {
                  "brandDetails": {
                    "href": "http://localhost:8080/api/vehicle-brands/1"
                  },
                  "models": {
                    "href": "http://localhost:8080/api/vehicle-brands/1/models"
                }
              }
          }]
        },
        ```
    - React application can use a model or select list for the above, once user choose an item, use the `models.href` of that item.

2. Model
    - From the last response above, hit   
    ```
   GET:http://localhost:8080/api/vehicle-brands/1/models
    // sample response
    {
        "_embedded": {
            "vehicleModelResourceList": [
                {
                    "id": 1,
                    "modelName": "Corolla",
                    "brand": "Toyota",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/api/vehicle-brands/1/models"
                        }
                    }
                },
            ]
        }
    }
   ```  

Once the above is chosen, Use Root Api in React Js, and use the objects from the response.
use `vehicles.href` and make a Post request 

```
POST: http://localhost:8080/api/vehicles
{
    "vehicleModelId": 1,
    "year": 2023,
    "vin": "X1",
    "price": 500.0
}
```

To update Vehicle; use `vehicles.href` from the Root Api, make a GET request
```
GET:http://localhost:8080/api/vehicles
// response sample
{
  "_embedded": {
    "vehicleResourceList": [
      {
        "id": 1,
        "vehicleModelId": 1,
        "year": 2024,
        "vin": "Cls5",
        "price": 50.0,
        "_links": {
          "update": {
            "href": "http://localhost:8080/api/vehicles/1"
          }
        }
      }
    ]
  }
}
```
Choose the `_embedded.vehicleResourceList[n]._links.update.href`

```
PUT:http://localhost:8080/api/vehicles/2
{
    "vehicleModelId": 1,
    "year": 2024,
    "vin": "X1",
    "price": 500.0
}
```



#### Leasing Contract (1.3.4)

To create a Leasing contractor, we need to select one customer, and a vehicle

1. Selecting customer
   From response of Root Api, choose `_links.customers`, and make a GET call
```
GET:http://localhost:8080/api/customers
{
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
Use the above response for populating `Customer` in modal.

2. Selecting vehicles from list
   - Use Root Api response and choose `_links.vehicles.href` and make a Get call

```
GET:http://localhost:8080/api/vehicles
// sample response
{
  "_embedded": {
    "vehicleDetailResourceList": [
        "id": 1,
        "modelId": 0,
        "modelName": "Corolla",
        "brandName": "Toyota",
        "year": 2024,
        "vin": "Cls5",
        "price": 50.0,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/vehicles/1"
          },
          "update": {
            "href": "http://localhost:8080/api/vehicles/1"
          }
        }
      },
    ]
  }
}
```
Use the above response to populate Vehicle id.
Use Get call to `_links.self.href` for details of the vehicle


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
