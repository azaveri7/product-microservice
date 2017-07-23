# product-microservice

This is spring boot application and can be accessed using below url
http://localhost:8080/swagger-ui.html

MicroserviceApplication.java is the class that needs to be run to start this microservice.

Get request to get all products:
http://localhost:8080/products

[
    {
        "id": 1,
        "type": "electronic"
    },
    {
        "id": 2,
        "type": "cloth"
    },
    {
        "id": 3,
        "type": "toy"
    },
    {
        "id": 4,
        "type": "electronic"
    },
    {
        "id": 5,
        "type": "electronic"
    }
]

Get request to get all products based on type of product:
http://localhost:8080/products/types?type=toy

[
    {
        "id": 3,
        "type": "toy"
    }
]

Post request to create/save a new product:
http://localhost:8080/products

Body 
{
        "id": 5,
        "type": "cos"
}

output:

{
        "id": 5,
        "type": "cos"
}

Delete request to delete a product based on id:
http://localhost:8080/products/1
