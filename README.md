# tradepro

1. This is a app of RESTful API backend services to product sales receipt for a list of purchased products.

2. The service endpoint is http://[Domain]/tradepro/receipt

3. It accepts request message in format:

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

4. The response is a plain text of sales receipt:
      ```json
      item                               price                 qty

      book                              $17.99                   1
      potato chips                       $3.99                   1
      subtotal:                                             $21.98
      tax:                                                   $1.80
      total:                                                $23.78
      ```
      
5. The sales tax rates are defined as:

      In California (CA), sales tax rate is 9.75%, food is exempted.
      
      In New York (NY), sales tax rate is 8.875%, food and clothing are exempted.
