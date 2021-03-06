# tradepro

1. This is a app of RESTful API backend services (Spring Boot framework) to generate sales receipt for purchased products.

2. The service endpoint is http://[Domain]/tradepro/receipt

3. It accepts request messages in format:
      
   ## Sample 1
      
      Method: POST
      
      Header: 
            Content-type, application/json
            
      Payload:
```json            
          {
            "location":"CA",
            "purchasedItem":[
                {"item":"book","price":17.99,"qty":1},
                {"item":"potato chips","price":3.99,"qty":1}
              ]
          }
```

      Response: a plain text of sales receipt:
```json
      item                               price                 qty

      book                              $17.99                   1
      potato chips                       $3.99                   1
      subtotal:                                             $21.98
      tax:                                                   $1.80
      total:                                                $23.78
```
   
   ## Sample 2
      
      Method: POST
      
      Header: 
            Content-type, application/json
            
      Payload:
```json            
      {
            "location":"NY",
            "purchasedItem":[
                        {"item":"book","price":17.99,"qty":1},
                        {"item":"pencil","price":2.99,"qty":3}
                  ]
      }
```

      Response: a plain text of sales receipt:
```json
      item                               price                 qty

      book                              $17.99                   1
      pencil                             $2.99                   3
      subtotal:                                             $26.96
      tax:                                                   $2.40
      total:                                                $29.36
```
      
      
5. The sales tax rates are calculated by this formula:

      Sales tax = roundup(price * quantity * sales tax rate).
      
6. Certain product categories are exempted from sales tax (i.e, tax rate is 0), and sales tax amount must be rounded up to a nearest decimal number 0.05 (e.g. 1.13->1.15, 1.16->1.20, 1.151->1.20)

7. The following product categories are exempted from tax rate:

      For California (CA), sales tax rate is 9.75%, food products are exempted.
      
      For New York (NY), sales tax rate is 8.875%, food and apparel products are exempted.

8. The product categories and the exemption list of tax rates are stored in a configurable application properties file, in yaml format:
```json            
      app:
        salestax:
          - location: "CA"
            exempt: food
            rate: 0.0975
          - location: "NY"
            exempt: food,clothing
            rate: 0.08875
        productcat:
          "food": potato chips
          "clothing": shirt
```
