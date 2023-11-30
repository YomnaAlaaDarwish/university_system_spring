<!DOCTYPE html>
<html>
  <head>
    <style>
    body {
      font-family: 'Arial', sans-serif;
      background-color: #f4f4f4;
      padding: 20px;
      text-align: center;
    }

    h1 {
        color: #333;
    }

    form {
        background-color: #fff;
        padding: 40px; /* زيادة الحشو */
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 30px;
        display: inline-block;
        text-align: left;
        width: 500px; /* تعيين عرض ثابت للنموذج */
    }

    label {
        margin-right: 10px;
        display: inline-block;
        width: 120px; /* زيادة عرض النص */
    }

    input[type="text"], input[type="submit"], button {
        padding: 15px; /* زيادة حشو العناصر الداخلية */
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        width: 80%; /* جعل العناصر أوسع داخل النموذج */
    }

    input[type="submit"], button {
      background-color: #007bff;
      color: white;
      border: none;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    input[type="submit"]:hover, button:hover {
        background-color: #0056b3;
    }

    #result {
        color: green;
    }

    </style>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Student Information</title>

    <script>
      function update() {
          var studentId = document.getElementById("studentId").value;
          var newFirstName = document.getElementById("newFirstName").value;
          var newLastName = document.getElementById("newLastName").value;
          var newGender = document.getElementById("newGender").value;
          var newGPA = document.getElementById("newGPA").value;
          var newLevel = document.getElementById("newLevel").value;
          var newAddress = document.getElementById("newAddress").value;
  
          $.ajax({
              type: "POST",
              url: "/updateStudent",
              data: {
                  studentId: studentId,
                  newFirstName: newFirstName,
                  newLastName: newLastName,
                  newGender: newGender,
                  newGPA: newGPA,
                  newLevel: newLevel,
                  newAddress: newAddress
              },
              success: function(result) {
                  document.getElementById("result").innerText = result;
              },
              error: function(error) {
                  console.error("Error:", error);
              }
          });
      }
  </script>
  
  </head>
  <body>

    <h2>Update Student Information</h2>

    <form onsubmit="event.preventDefault(); update();">
        <label for="studentId">Student ID:</label>
        <input type="text" id="studentId" name="studentId" required>
        <br>

        <label for="newFirstName">New First Name:</label>
        <input type="text" id="newFirstName" name="newFirstName">
        <br>

        <label for="newLastName">New Last Name:</label>
        <input type="text" id="newLastName" name="newLastName">
        <br>

        <label for="newGender">New Gender:</label>
        <input type="text" id="newGender" name="newGender">
        <br>

        <label for="newGPA">New GPA:</label>
        <input type="text" id="newGPA" name="newGPA">
        <br>

        <label for="newLevel">New Level:</label>
        <input type="text" id="newLevel" name="newLevel">
        <br>

        <label for="newAddress">New Address:</label>
        <input type="text" id="newAddress" name="newAddress">
        <br>

        <button type="submit">Update Student</button>
    </form>

    <h1>Result: <span id="result"></span></h1> 
</body>
</html>