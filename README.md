# API Documentation (Bank Simulator)

## **ENDPOINTS**

## Bank related Endpoints:
To add a bank

```
POST   /addbank/
```

### Sample Request:

```
    {
        "bankName": "Bank of Baroda",
        "bankId":"1003";
    }
```

### Sample Response:

```
Added Bank Successfully!!
```
---
To fetch the details of all the banks.

```
GET   /fetchbankdetails/
```

### Sample Response:

```
[
    {
        "bankId": "1001",
        "bankName": "State Bank Of India"
    },
    {
        "bankId": "1002",
        "bankName": "Central Bank Of India"
    }
]
```
___

To fetch the details of a particular bank


### Sample Request:

```
GET /fetchbankdetail/1002/
```

### Sample Response:

```
[
    {
        "bankId": "1002",
        "bankName": "Central Bank Of India"
    }
]
```
To fetch banks based on location(city)
```
GET    /fetchBanksByCity/{city}
```
### Sample Request:
```
GET    /fetchBanksByCity/Delhi
```
### Sample Response:
```
{
   {
        "bankId": "1002",
        "branchCode":"10021"
        "branchName" : "Dwarka",
        "ifscCode": "CBIN0283583",
        "micrCode": "110016123",
        "swiftCode": "CBININBB",
        "city": "Delhi",
        "address": "M SS SCHOOL SEC-19 DWARKAM"
    },
    {
        "bankId": "1005",
        "branchCode":"10051"
        "branchName" : "Bazar road",
        "ifscCode": "CBIN0283446",
        "micrCode": "110009864",
        "swiftCode": "CBINIPHH",
        "city": "Delhi",
        "address": "Opposite to Big Bazar, Bazar road"
    }, 
    {
        "bankId": "1002",
        "branchCode":"10027"
        "branchName" : "Saket",
        "ifscCode": "CBIN0282583",
        "micrCode": "110016123",
        "swiftCode": "CBININFB",
        "city": "Delhi",
        "address": "Middle town, Saket"
    },
    {
        "bankId": "1008",
        "branchCode":"10086"
        "branchName" : "Rajiv gandhi Road",
        "ifscCode": "CBIN0973583",
        "micrCode": "110216123",
        "swiftCode": "CBININRJ",
        "city": "Delhi",
        "address": "Near ganesha temple, Rajiv gandhi Road"
    }
}
```
---
## Branch related Endpoints:
To add bank branch

```
POST   /addbranch/
```

### Sample Request:

```
{
    "bankId": "1003",
    "branchCode":"10031"
    "branchName" : "A R M B Delhi",
    "ifscCode": "BARB0VJADEL",
    "micrCode": "110014126",
    "swiftCode": "BARBINBBXXX",
    "city": "Delhi",
    "address": "A R M B Delhi"
}
```

### Sample Response:

```
 Added  Branch Successfully!!
```
___

To fetch  the branch details of all the branches of a particular bank

### Sample Request:

```
GET /fetchbranchdetails/bank/1002/
```

### Sample Response:

```
[
    {
        "bankId": "1002",
        "branchCode":"10021"
        "branchName" : "Dwarka",
        "ifscCode": "CBIN0283583",
        "micrCode": "110016123",
        "swiftCode": "CBININBB",
        "city": "Delhi",
        "address": "M SS SCHOOL SEC-19 DWARKAM"
    },
    {
        "bankId": "1002",
        "branchCode":"10022"
        "branchName" : "Madipur",
        "ifscCode": "	CBIN0281029",
        "micrCode": "110016038",
        "swiftCode": "CBININBB",
        "city": "Delhi",
        "address": "W.Z. 223, MADIPUR, NEW DELHIM"   
    }
]
```
---

To fetch the branch details of a particular branch of a particular bank

### Sample Request:

```
GET fetchbranchdetail/bank/branch/1002/10021/
```

### Sample Response:

```
[
    {
        "bankId": "1002",
        "branchCode":"100021"
        "branchName" : "Dwarka",
        "ifscCode": "CBIN0283583",
        "micrCode": "110016123",
        "swiftCode": "CBININBB",
        "city": "Delhi",
        "address": "M SS SCHOOL SEC-19 DWARKAM"
    }
]
```
---
## Asset related Endpoints:
To add asset to a particular branch of a particular bank

```
POST  /addasset/
```

### Sample Request:

```
{   
    "bankId": "1003",
    "branchCode": "10031",
    "assetCode": "125",
    "assetType": "USD"
}
```

### Sample Response:

```
 Asset added successfully
 ```
---
To update assetType of a particular account
```
PUT /updateAssetType/accountNumber/12345
```
### Sample Request:
```
{
    "accountNumber": "12345",
    "bankId":"1004",
    "branchCode":"10042",
    "assetCode":125,
    "assetType":"USD"
}
```
In this case, the asset type is updated from INR to USD.
### Sample Response:
```
    Asset type updated successfully.
```
---
## Account related Endpoints:
To add account to a particular asset

```
POST  /addaccount/
```

### Sample Request:

 ```   
{
    "bankId": "1002",
    "branchCode":"10022",
    "assetCode" : "124",
    "accountNumber": "1212",
    "accountHolder Name": "Raj",
    "balance": 70000.00,
    "accountType" : "Deposit"
}
```

### Sample Response:

```
Account Added Successfully!!
```

---
To fetch the details of all the accounts present in all the branches of a particular bank


### Sample Request:

```
GET fetchaccountdetails/bank/1002/
```

### Sample Response:

```
[
    {
        "bankId": "1002",
        "branchCode":"10021",
        "assetCode" : "123",
        "accountNumber": "1234",
        "accountHolder Name": "Ajay",
        "balance": 1000,
        "accountType" : "Saving"
    },
    {
        "bankId": "1002",
        "branchCode":"10021",
        "assetCode" : "125",
        "accountNumber": "1236",
        "accountHolder Name": "Vijay",
        "balance": 105000,
        "accountType" : "Saving"
    },
    {
        "bankId": "1002",
        "branchCode":"10022",
        "assetCode" : "124",
        "accountNumber": "1212",
        "accountHolder Name": "Raj",
        "balance": 50000,
        "accountType" : "Deposit"
    }
]    
```
___

To fetch the details of all the accounts present in a particular branch of a particular bank


### Sample Request:

```
GET /fetchaccountdetails/bank/branch/1002/10022/
```

### Sample Response:

```
[
    {
        "bankId": "1002",
        "branchCode":"10022",
        "assetCode" : "124",
        "accountNumber": "1234",
        "accountHolder Name": "Ajay",
        "balance": 1000,
        "accountType" : "Saving"
    },
    {
        "bankId": "1002",
        "branchCode":"10022",
        "assetCode" : "124",
        "accountNumber": "1234",
        "accountHolderName": "Seema",
        "balance": 5000,
        "accountType" : "Saving"
    }
]

```
___
To fetch the account details of a particular asset of a particular branch of a particular bank


### Sample Request:

```
GET /fetchaccountdetails/bank/branch/asset/1002/10022/124/
```

### Sample Response:

```
[
    {
        "bankId": "1002",
        "branchCode":"10022",
        "assetCode" : "124",
        "accountNumber": "1234",
        "accountHolderName": "Ajay",
        "balance": 1000,
        "accountType" : "Saving"
    }
]
```
___

To fetch the account details of a particular account in a particular branch of a particular bank


### Sample Request:

```
GET /fetchaccountdetails/bank/branch/account/1002/10022/1234/
```

### Sample Response:

```
[
    {
        "bankId": "1002",
        "branchCode":"10022",
        "assetCode" : "124",
        "accountNumber": "1234",
        "accountHolderName": "Ajay",
        "balance": 1000,
        "accountType" : "Saving"
    }
]
```
___
To update the account details of the particular account.

```
PUT /updateaccount/
```
#### Sample Request:

 ```   
{
    "bankId": "1002",
    "branchCode":"10022",
    "assetCode" : "124",
    "accountNumber": "1212",
    "accountHolder Name": "Raj",
    "balance": 70000,
    "accountType" : "Deposit"
}
```

### Sample Response:

```
Account Updated Successfully!!
```
---
To  delete the particular account.

```
DELETE /deleteAccount/{accountNumber}/
```

### Sample Request:

```
/deleteaccount/1212/
```

### Sample Response:

```
"Your account has been deleted successfully. 
Thanks for banking with us."
```
---
