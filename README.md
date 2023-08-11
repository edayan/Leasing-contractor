# Leasing-contractor
Project Leasing contractor


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
    }
  }
}
```
From this we can navigate to all sub uris, So that React application doesn't need to create uris at client level, but use the response object to navigate to every deep level resources. In other words it is using HATEOS.


###### Update Create Vehicle (1.3.3)
Use Root Api in React Api, and use the objects from the response.

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

