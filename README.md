# food_delivery-SpringBoot
Building a modern food delivery api in Springboot , with AWS S3 bucket , payment service integrated with Razorpay 
Generates a randon UUID to prefix to the filename 

creating a putobjectrequest  to request to upload file to the S3 as an object 

- **bucket**: the target bucket
- **key**: the S3 path + file name
- **acl**: gives **public read access**
- **contentType**: MIME type (`image/jpeg`, `application/pdf`, etc.)

put object response actually upload the file to the bucket 

returning the public key as https://bucket-name.s3.amazonaws.com/file-extension 

so the frontend can esasiy access the required image without  further calling the backend server
