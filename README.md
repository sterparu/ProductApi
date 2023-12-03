# ProductApi
Create an API that acts as a store management tool

Project description:
 the project will create an Api that acts as a store management tool for a product that has an UUID id String name and Double price.For the product i used hibernate and a mySql database.
 
 
Features
The API supports various operations for managing products:

Add Product

Endpoint: POST /api/products
Description: Adds a new product to the store.
Request Body: JSON object containing name and price of the product.
Functionality: The product is saved in the MySQL database.
Get Product by ID

Endpoint: GET /api/products/{UUID}
Description: Retrieves a product by its UUID.
Response: Returns the product details if found; otherwise, an error message is returned.
Change Product Price

Endpoint: PUT /api/products/{UUID}/price
Parameters: newPrice as a query parameter.
Description: Updates the price of the specified product.
Functionality: Modifies the product’s price in the database and returns the updated product details.
Delete Product

Endpoint: DELETE /api/products/{UUID}
Description: Removes the product from the database.
Security
The API is secured with basic authentication. Currently, it uses an in-memory user for authentication purposes. All endpoints are accessible only to users with the ADMIN role.

Testing
Comprehensive unit tests have been written for the ProductController class. These tests cover all the API endpoints, ensuring that each operation behaves as expected.
