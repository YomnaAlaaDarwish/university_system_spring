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
  
  
   <form onsubmit="event.preventDefault(); deleteStudent();">
      <label for="studentIdInput">Student ID:</label>
      <input type="text" id="studentIdInput" name="studentId">
      <button type="submit">Delete Student</button>
  </form>

    

  <h1>Result: <span id="result"></span></h1> 

  

</body>
</html>

    