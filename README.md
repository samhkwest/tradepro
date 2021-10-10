# tradepro

1. This is a app of RESTful API backend services to product sales receipt for a list of purchased products.

2. For a http request message as below: 
      {
        "location":"CA",
        "purchasedItem":[
            {"item":"book","price":17.99,"qty":1},
            {"item":"potato chips","price":3.99,"qty":1}
          ]
      }
    
    Then the http response is:
      item                               price                 qty

      book                              $17.99                   1
      potato chips                       $3.99                   1
      subtotal:                                             $21.98
      tax:                                                   $1.80
      total:                                                $23.78
