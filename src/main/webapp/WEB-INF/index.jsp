<!---<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<form action="regiter" method="post">
    <body>
        <label>firstname: </label>
        <input type="text" name="fname"> <br><br>
        <label>lastname: </label>
        <input type="text" name="lname"> <br><br>
        <label>Gender: </label>
        <input type="text" name="gender"> <br><br>
        <label>GPA: </label>
        <input type="text" name="gpa"> <br><br>
    </body>
    <button type="submit"> Submit </button>
</form>
</html>
--->
<!DOCTYPE html>
<html>
  <head>
    <title>Student Management System</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script>
      function deleteStudent() {
          var studentId = document.getElementById("studentIdInput").value;
  
          $.ajax({
              type: "POST",
              url: "/deleteStudent",
              data: { studentId: studentId },
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
    <h1>Student Management System</h1>
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

      <input type="submit" value="Add Student" />
    </form>

    <form onsubmit="event.preventDefault(); deleteStudent();">
      <label for="studentIdInput">Student ID:</label>
      <input type="text" id="studentIdInput" name="studentId">
      <button type="submit">Delete Student</button>
  </form>

    

<h1>Result: <span id="result"></span></h1>
    


  </body>
</html>
