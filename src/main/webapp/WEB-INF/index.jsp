<!DOCTYPE html>
<html>
  <head>
    <title>Student Management System</title>
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

      <script>
         var maxStudents = 0;
        var addedStudents = 0;

        function setMaxStudents() {
            maxStudents = document.getElementById("New").value;
        }

        function clickCounter() {
            if (maxStudents > 0) {
                addedStudents++;
                if (addedStudents >= maxStudents) {
                    document.getElementById("myButton").disabled = true;
                }
            }
        }
    
        
     </script>
  
  </head>
  <body>
    <h1>Student Management System</h1>
    <label>Enter number of students:</label>
    <input id="New"  type="number">
    <button onclick="setMaxStudents()">Click me</button>
    <form method="POST" action="/addStudent">
      <label for="studentId">Student ID:</label>
      <input type="text" id="studentId" name="studentId" required /><br /><br />

      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" required /><br /><br />

      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" required /><br /><br />

      <label for="gender">Gender:</label>
      <input type="text" id="gender" name="gender" required /><br /><br />

      <label for="gpa">GPA:</label>
      <input type="text" id="gpa" name="gpa" required /><br /><br />

      <label for="level">Level:</label>
      <input type="text" id="level" name="level" required /><br /><br />

      <label for="address">Address:</label>
      <input type="text" id="address" name="address" required /><br /><br />

      <input id="myButton" type="submit" value="Add Student" onclick="clickCounter()"/>
    </form>

  


  </body>
</html>
