# shop-mart

A simple eCommerce android application.


## Technologies
- MVVM
- Firebase
- Hilt
- Glide
- Material Design

## How to use
1. Create your own `google-services.json` by creating [Firebase account](https://firebase.google.com)
2. In Firebase console navigate to `Database` then `Firestore`
3. In Firestore create a collection named `product` then add document with the following field:
   - **name**: String
   - **price**: Number
   - **image**: String [path + filename of the image]
4. Navigate to `Storage` then add the picture.

## Screenshots
![Login](https://firebasestorage.googleapis.com/v0/b/business-10225.appspot.com/o/shopmart%2Flogin.png?alt=media&token=bd9efc72-f1f5-4235-9cb5-394ccf9d339d)  ![Home](https://firebasestorage.googleapis.com/v0/b/business-10225.appspot.com/o/shopmart%2Fhome.png?alt=media&token=6d66ac18-660c-457d-a019-9ee9b1e596dc)  ![Cart](https://firebasestorage.googleapis.com/v0/b/business-10225.appspot.com/o/shopmart%2Fcart.png?alt=media&token=604333d2-352c-4d52-8581-25f2e7e0b0c4)
