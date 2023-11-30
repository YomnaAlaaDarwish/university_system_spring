<!DOCTYPE html>
<html>
  <head>
    <title>Student Management System</title>
    <style>
      body {
        font-family: "Arial", sans-serif;
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
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
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

      input[type="text"],
      input[type="submit"],
      button {
        padding: 15px; /* زيادة حشو العناصر الداخلية */
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
        width: 80%; /* جعل العناصر أوسع داخل النموذج */
      }

      input[type="submit"],
      button {
        background-color: #007bff;
        color: white;
        border: none;
        cursor: pointer;
        transition: background-color 0.3s;
      }

      input[type="submit"]:hover,
      button:hover {
        background-color: #0056b3;
      }

      #result {
        color: green;
      }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
      var maxStudents = 0;
      var addedStudents = 0;

      function setMaxStudents() {
        maxStudents = document.getElementById("New").value;
      }

      function clickCounter() {
        if (maxStudents > 0) {
          addedStudents++;
          if (addedStudents - 1 >= maxStudents) {
            document.getElementById("myButton").disabled = true;
            window.location.href = "/Home";
          }
        }
      }

      //validation
      function validateForm() {
        // Get form inputs
        var firstName = document.getElementById("firstName").value;
        var lastName = document.getElementById("lastName").value;
        var address = document.getElementById("address").value;
        var studentId = document.getElementById("studentId").value;
        var gpa = document.getElementById("gpa").value;
        var gender = document.getElementById("gender").value;
        var level = document.getElementById("level").value;

        // Check if name and address contain only characters (a-z)
        if (!isAlpha(firstName) || !isAlpha(lastName) || !isAlpha(address)) {
          alert(
            "Student  First and last name and address should contain only characters (a-z)."
          );
          return false;
        }
        // Check if GPA is in the valid range
        if (gpa < 0 || gpa > 4) {
          alert("GPA must be between 0 and 4.");
          return false;
        }
        // Check if any field is empty
        if (
          !firstName ||
          !lastName ||
          !address ||
          !studentId ||
          !level ||
          !gender ||
          isNaN(gpa)
        ) {
          alert(
            "All attributes must not be null/empty. Please fill in all fields."
          );
          return false;
        }
        clickCounter();
        return true; // Form is valid
      }

      // Function to check if a string contains only alphabetic characters
      function isAlpha(str) {
        return /^[a-zA-Z]+$/.test(str);
      }

      $(document).ready(function () {
        $("#addStudentForm").submit(function (event) {
          event.preventDefault(); // Prevent form submission

          // Collect form data
          var formData = {
            studentId: $("#studentId").val(),
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            gender: $("#gender").val(),
            gpa: parseFloat($("#gpa").val()),
            level: parseInt($("#level").val()),
            address: $("#address").val(),
          };

          // Send AJAX request
          $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/addStudent",
            data: JSON.stringify(formData),
            dataType: "json",
            success: function (response) {
              // Handle success response
              console.log("Student added successfully");
              // Redirect to the index page
              window.location.href = "/index";
            },
            error: function (xhr, status, error) {
              // Handle error response
              console.log("Error: " + xhr.responseText);
              alert(xhr.responseText);
              if (
                xhr.responseText ==
                "Duplicate student ID. Please use a different ID."
              )
                addedStudents--;
            },
          });
        });
      });
    </script>
  </head>
  <body>
    <h1>Student Management System</h1>
    <div>
      <label>Enter number of students:</label>
      <input id="New" type="number" />
      <button onclick="setMaxStudents()" id="myButton">Click me</button>
    </div>

    <form method="POST" action="/addStudent" id="addStudentForm">
      <label for="studentId">Student ID:</label>
      <input type="text" id="studentId" name="studentId" required /><br /><br />

      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" required /><br /><br />

      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" required /><br /><br />

      <label for="gender">Gender:</label>
      <input type="text" id="gender" name="gender" required /><br /><br />

      <label for="gpa">GPA:</label>
      <input
        type="number"
        id="gpa"
        name="gpa"
        required
        min="0"
        max="4"
        step="0.01"
      /><br /><br />

      <label for="level">Level:</label>
      <input type="text" id="level" name="level" required /><br /><br />

      <label for="address">Address:</label>
      <input type="text" id="address" name="address" required /><br /><br />

      <input
        id="myButton"
        type="submit"
        value="Add Student"
        onclick="validateForm()"
      />
    </form>
  </body>
</html>
