# bookingapp
App that provides a couple of endpoints to book a campsite.  JPA, Spring Boot, Maven, Sprinf IoC

Things to take into account:

1) This Rest API has two endpoints.  These are locked-booking and booking.  Locked-booking is useful to do a prebooking.  You need to use this endpoint to find if the campsite is available for a time period.  If so, it creates a record in the related table (locked-booking) and gives a code to finish the process and load all the information from this one to booking endpoint.  In the last endpoint the user needs to provide user name and user email to complete it.  This API use a timeout to block the campsite for a specific time period.  The user has 5 mins to finish the process. 

2) To update or cancel the booking, the API uses the patch verb and the user needs to provide the following json with one of the entries (code to update and active to cancel).   The code in the json string is the new one that the user obtains with the new period time.  Active parameter must be always false if the user wants to cancel the booking.
```json
{  
   "code": "GJH575VBN",
   "active": "false"
}
```

3) You can see all the endpoints and its definition using swagger.  Use the following url:

http://localhost:8080/swagger-ui.html#/

4) With the booking endpoint along with the get verb and two date parameters, the user can see all the bookings for a period time that can not be greater that 30 days (1 month). With this endpoint the user can determine what days are available for booking.
