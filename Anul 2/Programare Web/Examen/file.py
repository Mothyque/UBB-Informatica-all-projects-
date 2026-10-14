Customers= {
"John" : "address1" ,
"Mary": "address2",
"Andrew": "address3"}
 
Parcels={
"parcel1" :
{"status" : "shipping",
"recipient" : "John"},
"parcel2" :
{"status" : "delivered",
"recipient" : "Mary"},
"parcel3" :
{"status" : "preparing",
"recipient" : "Mary"},
"parcel4" :
{"status" : "shipping",
"recipient" : "John"},
"parcel5" :
{"status" : "preparing",
"recipient" : "Mary"},
"parcel6" :
{"status" : "delivered",
"recipient" : "Mary"}
}

# {
# "John" : [parcel1,parcel4 ],
# "Mary" : [parcel2, parcel3, parcel5, parcel6],
# "Anndrew: []
# }

result = {}
for c in Customers.keys():
    result[c] = []
    for p, v in Parcels.items():
        if v["recipient"] == c:
            result[c].append(p)

print(result)
